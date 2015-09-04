package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroStationDao;
import com.metrosystem.dao.IMetroTrainDao;
import com.metrosystem.dao.IRouteDao;
import com.metrosystem.dao.ITrainScheduleDao;
import com.metrosystem.dao.ITrainScheduleTimingDao;
import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.beans.TrainScheduleDTO;
import com.metrosystem.dao.beans.TrainScheduleTimingDTO;
import com.metrosystem.service.ITrainScheduleService;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.TrainScheduleBO;
import com.metrosystem.service.beans.TrainScheduleTimingBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.exception.ServiceValidationException;
import com.metrosystem.service.utils.MetroStationBoDtoConverter;
import com.metrosystem.service.utils.MetroTrainBoDtoConverter;
import com.metrosystem.service.utils.RouteBoDtoConverter;
import com.metrosystem.service.utils.TrainScheduleBoDtoConverter;
import com.metrosystem.service.utils.TrainScheduleTimingBoDtoConverter;

@Component("trainScheduleService")
@Transactional(readOnly=false,rollbackFor={Exception.class})
public class TrainScheduleServiceImpl implements ITrainScheduleService {

	@Autowired
	@Qualifier("trainScheduleDao")
	private ITrainScheduleDao trainScheduleDao;
	
	@Autowired
	@Qualifier("trainScheduleTimingDao")
	private ITrainScheduleTimingDao scheduleTimingDao;
	
	@Autowired
	@Qualifier("trainDao")
	private IMetroTrainDao trainDao;
	
	@Autowired
	@Qualifier("stationDao")
	private IMetroStationDao stationDao;
	
	@Autowired
	@Qualifier("routeDao")
	private IRouteDao routeDao;
	
	@Autowired
	@Qualifier("trainScheduleBoDtoConverter")
	private TrainScheduleBoDtoConverter scheduleConverter;
	
	@Autowired
	@Qualifier("trainScheduleTimingBoDtoConverter")
	private TrainScheduleTimingBoDtoConverter scheduleTimingConverter;
	
	@Autowired
	@Qualifier("trainBoDtoConverter")
	private MetroTrainBoDtoConverter trainBoDtoConverter;
	
	@Autowired
	@Qualifier("routeBoDtoConverter")
	private RouteBoDtoConverter routeBoDtoConverter;
	
	@Autowired
	@Qualifier("stationBoDtoConverter")
	private MetroStationBoDtoConverter stationBoDtoConverter;
	
	@Override
	public TrainScheduleBO getTrainSchedule(int trainNumber) throws MetroSystemServiceException {
		
		try{
			//Validate the train
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				throw new ServiceValidationException("Invalid train number.No train exists with number:" + trainNumber);
			}
			
			TrainScheduleDTO scheduleDTO = trainScheduleDao.queryByTrainNumber(trainNumber);
			if(scheduleDTO == null){
				return null;
			}
			MetroTrainBO trainBO = trainBoDtoConverter.
					                dtoToBo(trainDTO.getTrainNumber(), 
					                		trainDTO.getName(), 
					                		routeBoDtoConverter.
					                		  dtoToBo(trainDTO.getRoute().getRouteId(), 
					                				  trainDTO.getRoute().getName()));
			
			
			//Get the schedule timings
			List<TrainScheduleTimingDTO> timingDTOs = scheduleTimingDao.queryTimingsForTrain(trainNumber);
		    List<TrainScheduleTimingBO> timingBOs = new ArrayList<TrainScheduleTimingBO>();
			TrainScheduleBO scheduleBO = scheduleConverter.
					                        dtoToBo(scheduleDTO.getScheduleId(), 
					                        		trainBO, timingBOs);
		    
		    for(TrainScheduleTimingDTO timingDTO:timingDTOs){
		    	MetroStationDTO stationDTO = timingDTO.getStation();
		    	MetroStationBO stationBO = stationBoDtoConverter.
		    			                     dtoToBo(stationDTO.getStationId(), 
		    			                    		 stationDTO.getName(), 
		    			                    		 stationDTO.getLocation().getLatitude(), 
		    			                    		 stationDTO.getLocation().getLongitude());
		    	
		    	TrainScheduleTimingBO timingBO = scheduleTimingConverter.
		    			                           dtoToBo(timingDTO.getTimingId(), 
		    			                        		   scheduleBO, 
		    			                        		   stationBO, 
		    			                        		   timingDTO.getArrivalTime(), 
		    			                        		   timingDTO.getDepartureTime());
		    	
		    	timingBOs.add(timingBO);
		    }
		    
		    return scheduleBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void createOrUpdateTrainSchedule(int trainNumber,List<TrainScheduleTimingBO> timings)   
	throws MetroSystemServiceException {
		
		try{
			//Validate the train number first
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				throw new ServiceValidationException("Invalid train number.No train exists with number:" + trainNumber);
			}
			
			//Validate if route is defined for train
			RouteDTO routeDTO = trainDTO.getRoute();
			if(routeDTO == null){
				throw new ServiceValidationException("No route defined for train: " + trainNumber);
			}
			//Validate the list
			for(int i =0; i < timings.size();i++){
				int position = i+1;
				TrainScheduleTimingBO timingBO = timings.get(i);
				
				
				//Validate if station has been defined
				MetroStationBO stationBO = timingBO.getStation();
				if(stationBO == null){
					throw new ServiceValidationException("No station defined for schedule timing at position:" + position);
				}
				//Validate if station is valid or not
				MetroStationDTO stationDTO = stationDao.queryStationByName(stationBO.getName());
				if(stationDTO==null){
					throw new ServiceValidationException("Invalid station:" + stationBO.getName()+".No such station exists.");
				}
				//Validate if station exists for the route
				if(stationDao.queryStationForRoute(
				     stationBO.getName(), routeDTO.getName()) == null){
					throw new ServiceValidationException("Station " + stationBO.getName() + 
							                             " does not exist for route" + routeDTO.getName());
				}
			}
			
			//Insert or update the data
			TrainScheduleDTO scheduleDTO = trainScheduleDao.queryByTrainNumber(trainNumber);
			Integer scheduleId = null;
			if(scheduleDTO == null){
				scheduleId =trainScheduleDao.save(scheduleDTO);
				scheduleDTO = scheduleConverter.boToDto(scheduleId, trainDTO, null);
			}
			
			for(TrainScheduleTimingBO timingBO: timings){
				//Check if timing already exists for a station
				TrainScheduleTimingDTO timingDTO = scheduleTimingDao.
						                      queryForStation(trainNumber, timingBO.getStation().getName());
			
			    if(timingDTO != null){
			    	timingDTO.setArrivalTime(timingBO.getArrivalTime());
			    	timingDTO.setDepartureTime(timingBO.getDepartureTime());
			    	scheduleTimingDao.update(timingDTO);
			    }
			    else{
			    	MetroStationBO stationBO = timingBO.getStation();
			    	MetroStationDTO stationDTO = stationBoDtoConverter.boToDto(stationBO.getStationId(), stationBO.getName(), 
			    			                                                   stationBO.getLocation().getLatitude(), 
			    			                                                   stationBO.getLocation().getLongitude());
			    	
			    	timingDTO = scheduleTimingConverter.boToDto(null, scheduleDTO, stationDTO, timingBO.getArrivalTime(), timingBO.getDepartureTime());
			    	scheduleTimingDao.save(timingDTO);
			    }
			}
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

}
