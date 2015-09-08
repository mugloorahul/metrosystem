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
			
			TrainScheduleTimingDTO firstStationTiming = trainScheduleTimingDao.queryForStation(trainNumber, firstStation.getName());
			Calendar actualScheduleCalendar = Calendar.getInstance();
			actualScheduleCalendar.setTime(firstStationTiming.getDepartureTime());
			
			Calendar scheduleCalendar = Calendar.getInstance();
			scheduleCalendar.setTime(scheduleStartTime);
			actualScheduleCalendar.set(Calendar.DAY_OF_MONTH, scheduleCalendar.get(Calendar.DAY_OF_MONTH));
			actualScheduleCalendar.set(Calendar.MONTH, scheduleCalendar.get(Calendar.MONTH));
			actualScheduleCalendar.set(Calendar.YEAR, scheduleCalendar.get(Calendar.YEAR));
			Date actualScheduleTime = actualScheduleCalendar.getTime();
			
			
			if(scheduleStartTime.compareTo(actualScheduleTime) < 0){
				throw new ServiceValidationException("Cannot schedule the journey before its scheduled departure time: " + actualScheduleTime);
			}
			
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
	    	String currentStationFlag = "Y";
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
	    		
	    		monitor.setCurrentStationFlag(currentStationFlag);
	    		trainJourneyMonitorDao.save(monitor);
	    		currentStationFlag= "N";
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
			
			//Get the monitor for the first station and update its actual departure time
			MetroStationDTO firstStation = stationDao.queryStationForRouteAtSequence(trainDTO.getRoute().getName(), 1);
			updateMonitorsForDeparture(trainNumber,firstStation.getName(), startTime);
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	
	private void updateMonitorsForArrival(int trainNumber,String arrivalStation,Date arrivalTime) throws MetroSystemDaoException{
		
		//First update the current station flag for arrival station to Y and also update the arrival time
		TrainJourneyMonitorDTO arrivalStationMonitor = trainJourneyMonitorDao.queryMonitorForStation(trainNumber, arrivalStation);
		arrivalStationMonitor.setCurrentStationFlag("Y");
		arrivalStationMonitor.setActualArrivalTime(arrivalTime);
		if(arrivalTime.compareTo(arrivalStationMonitor.getScheduledDepartureTime()) >= 0){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(arrivalTime);
			calendar.add(Calendar.MILLISECOND, 
					     (int)getDateDifference(arrivalStationMonitor.getScheduledDepartureTime(),arrivalStationMonitor.getScheduledArrivalTime(), TimeUnit.MILLISECONDS));
		}
		trainJourneyMonitorDao.update(arrivalStationMonitor);
		
		long delay = getDateDifference(arrivalTime,arrivalStationMonitor.getScheduledArrivalTime(), TimeUnit.MILLISECONDS);
		if(delay > 0){
			//Get the monitors for stations after departure station and update their scheduled arrival times and departure times accordingly
			List<TrainJourneyMonitorDTO> monitors = trainJourneyMonitorDao.queryMonitorsForNextStations(trainNumber, arrivalStation);
			updateNextStationsMonitors(monitors, delay);
		}		
	}
	
	private void updateMonitorsForDeparture(int trainNumber,String departureStation,Date departureTime) throws MetroSystemDaoException, ServiceValidationException{
		
		//First update the current station flag for departing station to N and also update the departure time
		TrainJourneyMonitorDTO departureStationMonitor = trainJourneyMonitorDao.queryMonitorForStation(trainNumber, departureStation);
		if(departureTime.compareTo(departureStationMonitor.getScheduledDepartureTime()) < 0){
			throw new ServiceValidationException("Train cannot depart from station " + departureStation + " before scheduled departure time: " + 
		                                         departureStationMonitor.getScheduledDepartureTime());
		}
		departureStationMonitor.setCurrentStationFlag("N");
		departureStationMonitor.setActualDepartureTime(departureTime);
		trainJourneyMonitorDao.update(departureStationMonitor);
		
		long delay = getDateDifference(departureTime,departureStationMonitor.getScheduledDepartureTime(), TimeUnit.MILLISECONDS);
		if(delay > 0){
			//Get the monitors for stations after departure station and update their scheduled arrival times and departure times accordingly
			List<TrainJourneyMonitorDTO> monitors = trainJourneyMonitorDao.queryMonitorsForNextStations(trainNumber, departureStation);
			updateNextStationsMonitors(monitors, delay);
		}
		
	}
	
	private void updateNextStationsMonitors(List<TrainJourneyMonitorDTO> monitors,long delay) throws MetroSystemDaoException{
		Calendar arrivalTimeCalendar = Calendar.getInstance();
		Calendar departureTimeCalendar = Calendar.getInstance();
		for(TrainJourneyMonitorDTO monitor : monitors){
			Date scheduledArrivalTime = monitor.getScheduledArrivalTime();
			Date scheduledDepartureTime = monitor.getScheduledDepartureTime();
			arrivalTimeCalendar.setTime(scheduledArrivalTime);
			departureTimeCalendar.setTime(scheduledDepartureTime);
			arrivalTimeCalendar.add(Calendar.MILLISECOND, (int)delay);
			departureTimeCalendar.add(Calendar.MILLISECOND,(int)delay);
			monitor.setScheduledArrivalTime(arrivalTimeCalendar.getTime());
			if(arrivalTimeCalendar.getTime().compareTo(departureTimeCalendar.getTime()) >= 0){
				departureTimeCalendar.setTime(arrivalTimeCalendar.getTime());
				departureTimeCalendar.add(Calendar.MILLISECOND, (int)getDateDifference(scheduledDepartureTime, scheduledArrivalTime, TimeUnit.MILLISECONDS));
			}
			monitor.setScheduledDepartureTime(departureTimeCalendar.getTime());
			trainJourneyMonitorDao.update(monitor);
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
				throw new ServiceValidationException("Train cannot end its journey before start time: "+ journey.getActualStartTime());
			}
			
			journey.setActualEndTime(endTime);
			trainJourneyDao.update(journey);
			
			//Get the last station for the route
			MetroStationDTO lastStation = stationDao.queryLastStationForRoute(trainDTO.getRoute().getName());
			TrainJourneyMonitorDTO lastStationMonitorDTO = trainJourneyMonitorDao.queryMonitorForStation(trainNumber, lastStation.getName());
			lastStationMonitorDTO.setActualArrivalTime(endTime);
			lastStationMonitorDTO.setCurrentStationFlag("Y");
			trainJourneyMonitorDao.update(lastStationMonitorDTO);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}	
	}
	
	private long getDateDifference(Date date1, Date date2,TimeUnit timeUinit){
		
		long diff = date1.getTime()-date2.getTime();
		
		return timeUinit.convert(diff, TimeUnit.MILLISECONDS);
	}

	@Override
	public void arriveAtStation(int trainNumber, String arrivalStation, Date arrivalTime) throws MetroSystemServiceException {
		
		try{
			MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
			if(train == null){
				throw new ServiceValidationException("Invalid train number. No train found with number: " + trainNumber);
			}
			
			RouteDTO route = train.getRoute();
			if(route == null){
				throw new ServiceValidationException("No route defined for train: " + trainNumber);
			}
			
			//Check if valid station
			if(stationDao.queryStationByName(arrivalStation) == null){
				throw new ServiceValidationException("Invalid station name. No station exists with name: " + arrivalStation);
			}
			
			if(stationDao.queryStationForRoute(arrivalStation, route.getName()) == null){
				throw new ServiceValidationException("Station " + arrivalStation + " does not exist for route " + route.getName());
			}
			
			
			//Get the latest train journey
			TrainJourneyDTO journey = trainJourneyDao.queryLatestJourneyInProgress(trainNumber);
			if(journey == null){
				throw new ServiceValidationException("No journey in progress found for train with number: " + trainNumber);				
			}
			
			//Update the monitor for arrival station
			updateMonitorsForArrival(trainNumber, arrivalStation, arrivalTime);
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

	@Override
	public void departFromStation(int trainNumber, String departureStation,Date departureTime) throws MetroSystemServiceException {
		
		try{
			MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
			if(train == null){
				throw new ServiceValidationException("Invalid train number. No train found with number: " + trainNumber);
			}
			
			RouteDTO route = train.getRoute();
			if(route == null){
				throw new ServiceValidationException("No route defined for train: " + trainNumber);
			}
			
			//Check if valid station
			if(stationDao.queryStationByName(departureStation) == null){
				throw new ServiceValidationException("Invalid station name. No station exists with name: " + departureStation);
			}
			
			if(stationDao.queryStationForRoute(departureStation, route.getName()) == null){
				throw new ServiceValidationException("Station " + departureStation + " does not exist for route " + route.getName());
			}
			
			
			//Get the latest train journey
			TrainJourneyDTO journey = trainJourneyDao.queryLatestJourneyInProgress(trainNumber);
			if(journey == null){
				throw new ServiceValidationException("No journey in progress found for train with number: " + trainNumber);				
			}
			
			//Update the monitor for arrival station
			updateMonitorsForDeparture(trainNumber, departureStation, departureTime);
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}		
	}

}
