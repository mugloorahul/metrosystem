package com.metrosystem.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroStationDao;
import com.metrosystem.dao.IMetroTrainDao;
import com.metrosystem.dao.ITrainJourneyDao;
import com.metrosystem.dao.ITrainJourneyMonitorDao;
import com.metrosystem.dao.ITrainScheduleDao;
import com.metrosystem.dao.ITrainScheduleTimingDao;
import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.dao.beans.TrainJourneyMonitorDTO;
import com.metrosystem.dao.beans.TrainScheduleDTO;
import com.metrosystem.dao.beans.TrainScheduleTimingDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;
import com.metrosystem.service.ITrainJourneyService;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.RouteBO;
import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.exception.ServiceValidationException;
import com.metrosystem.service.utils.MetroTrainBoDtoConverter;
import com.metrosystem.service.utils.RouteBoDtoConverter;
import com.metrosystem.service.utils.TrainJourneyBoDtoConverter;
import com.metrosystem.service.utils.TrainJourneyMonitorBoDtoConverter;

@Component("trainJourneyService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class TrainJourneyServiceImpl implements ITrainJourneyService {

	@Autowired
	@Qualifier("trainDao")
	private IMetroTrainDao trainDao;
	
	@Autowired
	@Qualifier("stationDao")
	private IMetroStationDao stationDao;
	
	@Autowired
	@Qualifier("trainJourneyDao")
	private ITrainJourneyDao trainJourneyDao;
	
	@Autowired
	@Qualifier("trainScheduleDao")
	private ITrainScheduleDao trainScheduleDao;
	
	@Autowired
	@Qualifier("trainScheduleTimingDao")
	private ITrainScheduleTimingDao trainScheduleTimingDao;
	
	@Autowired
	@Qualifier("trainJourneyMonitorDao")
	private ITrainJourneyMonitorDao trainJourneyMonitorDao;
	
	@Autowired
	@Qualifier("trainBoDtoConverter")
	private MetroTrainBoDtoConverter trainBoDtoConverter;
	
	@Autowired
	@Qualifier("trainJourneyBoDtoConverter")
	private TrainJourneyBoDtoConverter trainJourneyBoDtoConverter;
	
	@Autowired
	@Qualifier("routeBoDtoConverter")
	private RouteBoDtoConverter routeBoDtoConverter;
	
	@Autowired
	@Qualifier("trainJourneyMonitorBoDtoConverter")
	private TrainJourneyMonitorBoDtoConverter monitorBoDtoConverter;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer scheduleTrainJourney(int trainNumber, Date scheduleStartTime) throws MetroSystemServiceException{
		
		try{
			MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
			if(train == null){
				throw new ServiceValidationException("No train exists with given train number: " + trainNumber);
			}
			
			RouteDTO routeDTO = train.getRoute();
			if(routeDTO == null){
				throw new ServiceValidationException("No route defined for train with number: " + trainNumber);
			}
			
			//Check if journey is being scheduled before its scheduled time
			List<MetroStationDTO> stations = stationDao.queryStationsForRouteOrderedBySequence(routeDTO.getName());
			if(stations == null || stations.size() == 0){
				throw new ServiceValidationException("No stations exist for route: " + routeDTO.getName());
			}
			
			MetroStationDTO firstStation = stations.get(0);
			
			/*TrainScheduleTimingDTO firstStationTiming = trainScheduleTimingDao.queryForStation(trainNumber, firstStation.getName());
			if(scheduleStartTime.compareTo(firstStationTiming.getDepartureTime()) < 0){
				throw new ServiceValidationException("Cannot schedule the journey before its scheduled departure time: " + firstStationTiming.getDepartureTime());
			}*/
			
			//Check if journey has already been scheduled at same time
			TrainJourneyDTO existingJourney = trainJourneyDao.queryJourneyByScheduleTime(trainNumber, scheduleStartTime);
			if(existingJourney != null){
				throw new ServiceValidationException("Train journey for train " + trainNumber + " is already scheduled at time " + scheduleStartTime);		
			}
			
			TrainJourneyDTO journeyDTO = trainJourneyBoDtoConverter.
					                          boToDto(null, scheduleStartTime, train,null,null);
			
			Integer journeyId =  trainJourneyDao.save(journeyDTO);
			journeyDTO.setJourneyId(journeyId);
			createMonitorsForScheduledJourney(journeyDTO);
			
			
			return journeyId;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}

	}
	
	private void createMonitorsForScheduledJourney(TrainJourneyDTO trainJourney) 
			throws MetroSystemDaoException, ServiceValidationException 
	{
		
		//Get the train schedule
		MetroTrainDTO train = trainJourney.getTrain();
		TrainScheduleDTO trainSchedule = trainScheduleDao.queryByTrainNumber(train.getTrainNumber());
		
		if(trainSchedule == null){
			throw new ServiceValidationException("No schedule has been defined for the train: " + train.getTrainNumber());
		}
		
	    List<TrainScheduleTimingDTO> timings = trainSchedule.getTimings();
	    Date beginningScheduleTime = new Date(trainJourney.getScheduledStartTime().getTime());  
	    if(timings != null && timings.size() > 0){
	    	Date lastTime = timings.get(0).getDepartureTime();
	    	Calendar calendar = Calendar.getInstance();
	    	calendar.setTime(beginningScheduleTime);
	    	for(int i=0;i < timings.size();i++){
	    		TrainScheduleTimingDTO timingDTO = timings.get(i);
	    		Date timingArrivalTime = timingDTO.getArrivalTime();
	    		Date timingDepartureTime = timingDTO.getDepartureTime();
	    		Date scheduledArrivalTime = null;
	    		Date scheduledDepartureTime = null;
	    		if(timingArrivalTime != null){
	    			calendar.add(Calendar.MILLISECOND, (int)getDateDifference(timingArrivalTime, lastTime, TimeUnit.MILLISECONDS));
	    			scheduledArrivalTime = calendar.getTime();
	    			lastTime = timingArrivalTime;
	    		}
	    		
	    		if(timingDepartureTime != null){
	    			calendar.add(Calendar.MILLISECOND, (int)getDateDifference(timingDepartureTime, lastTime, TimeUnit.MILLISECONDS));
	    		    scheduledDepartureTime = calendar.getTime();
	    		    lastTime = timingDepartureTime;
	    		}
	    		
	    		TrainJourneyMonitorDTO monitor = monitorBoDtoConverter.
	    				                           boToDto(null, 
	    				                        		   trainJourney, 
	    				                        		   timingDTO.getStation(), 
	    				                        		   scheduledArrivalTime, scheduledDepartureTime, 
	    				                        		   null, null);
	    		
	    		trainJourneyMonitorDao.save(monitor);
	    	}
	    }
	}

	@Override
	public TrainJourneyBO findTrainJourneyByScheduleTime(int trainNumber,Date scheduleTime) throws MetroSystemServiceException {
		
		try{
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				throw new ServiceValidationException("No train exists with given train number: " + trainNumber);
			}
		 
			
			TrainJourneyDTO journeyDTO = trainJourneyDao.queryJourneyByScheduleTime(trainNumber, scheduleTime);
			if(journeyDTO == null){
				return null;
			}
			
			RouteDTO routeDTO = trainDTO.getRoute();
			RouteBO routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
			MetroTrainBO trainBO = trainBoDtoConverter.
					                   dtoToBo(trainDTO.getTrainNumber(), 
					                		   trainDTO.getName(), routeBO);
			
			TrainJourneyBO journeyBO = trainJourneyBoDtoConverter.
					                        dtoToBo(journeyDTO.getJourneyId(), 
					                        		journeyDTO.getScheduledStartTime(), trainBO,
					                        		journeyDTO.getActualStartTime(),
					                        		journeyDTO.getActualEndTime());
			
			return journeyBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void startTrainJourney(int trainNumber,Date startTime) throws MetroSystemServiceException {
		
		try{
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				throw new ServiceValidationException("No train exists with given train number: " + trainNumber);
			}
			
			//Get the latest train journey
			TrainJourneyDTO journey = trainJourneyDao.queryLatestScheduledJourney(trainNumber);
			if(journey == null){
				throw new ServiceValidationException("No scheduled journey found for train with number: " + trainNumber);				
			}
			
			if(journey.getScheduledStartTime().compareTo(startTime) > 0){
				throw new ServiceValidationException("Train cannot start before schedule time: "+ journey.getScheduledStartTime());
			}
			journey.setActualStartTime(startTime);
			trainJourneyDao.update(journey);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void finishTrainJourney(int trainNumber,Date endTime) throws MetroSystemServiceException {
	
		try{
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				throw new ServiceValidationException("No train exists with given train number: " + trainNumber);
			}
			
			//Get the latest train journey
			TrainJourneyDTO journey = trainJourneyDao.queryLatestJourneyInProgress(trainNumber);
			if(journey == null){
				throw new ServiceValidationException("No journey in progress found for train with number: " + trainNumber);				
			}

			if(journey.getActualStartTime().compareTo(endTime) > 0){
				throw new ServiceValidationException("Train cannot end before start time: "+ journey.getActualStartTime());
			}
			
			journey.setActualEndTime(endTime);
			trainJourneyDao.update(journey);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}	
	}
	
	private long getDateDifference(Date date1, Date date2,TimeUnit timeUinit){
		
		long diff = date1.getTime()-date2.getTime();
		
		return timeUinit.convert(diff, TimeUnit.MILLISECONDS);
	}

}
