package com.metrosystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroStationDao;
import com.metrosystem.dao.IMetroTrainDao;
import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.ITrainJourneyDao;
import com.metrosystem.dao.IUserJourneyDao;
import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.service.IUserJourneyService;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.beans.RouteBO;
import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.beans.UserJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.exception.ServiceValidationException;
import com.metrosystem.service.utils.MetroStationBoDtoConverter;
import com.metrosystem.service.utils.MetroTrainBoDtoConverter;
import com.metrosystem.service.utils.MetroUserBoDtoConverter;
import com.metrosystem.service.utils.RouteBoDtoConverter;
import com.metrosystem.service.utils.TrainJourneyBoDtoConverter;
import com.metrosystem.service.utils.UserJourneyBoDtoConverter;

@Component("userJourneyService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class UserJourneyServiceImpl implements IUserJourneyService {

	@Autowired
	@Qualifier("userDao")
	private IMetroUserDao userDao;
	
	@Autowired
	@Qualifier("userJourneyDao")
	private IUserJourneyDao userJourneyDao;
	
	@Autowired
	@Qualifier("stationDao")
	private IMetroStationDao stationDao;
	
	@Autowired
	@Qualifier("trainDao")
	private IMetroTrainDao trainDao;
	
	@Autowired
	@Qualifier("trainJourneyDao")
	private ITrainJourneyDao trainJourneyDao;
	
	@Autowired
	@Qualifier("userBoDtoConverter")
	private MetroUserBoDtoConverter userBoDtoConverter;
	
	@Autowired
	@Qualifier("userJourneyBoDtoConverter")
	private UserJourneyBoDtoConverter userJourneyBoDtoConverter;
	
	@Autowired
	@Qualifier("stationBoDtoConverter")
	private MetroStationBoDtoConverter stationBoDtoConverter;
	
	@Autowired
	@Qualifier("trainBoDtoConverter")
	private MetroTrainBoDtoConverter trainBoDtoConverter;
	
	@Autowired
	@Qualifier("trainJourneyBoDtoConverter")
	private TrainJourneyBoDtoConverter trainJourneyBoDtoConverter;
	
	@Autowired
	@Qualifier("routeBoDtoConverter")
	private RouteBoDtoConverter routeBoDtoConverter;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer swipeIn(String userIdentifier,Date swipeInTime,String sourceStation, String destinationStation) 
	throws MetroSystemServiceException 
	{
		try{
			//Validate if valid user
			MetroUserDTO user = userDao.queryUserByIdentifier(userIdentifier);
			if(user == null){
				throw new ServiceValidationException("Invalid user.No user exists with identifier: " + userIdentifier);
			}
			
			//Validate if valid source station
			MetroStationDTO source = stationDao.queryStationByName(sourceStation);
			if(source == null){
				throw new ServiceValidationException("Invalid source station. No station exists with name: " + sourceStation);
			}
			//Validate if valid destination station
			MetroStationDTO destination = stationDao.queryStationByName(destinationStation);
			if(destination == null){
				throw new ServiceValidationException("Invalid destination station. No station exists with name: " + destinationStation);
			}
			
			//Validate if source and destination stations are same
			if(source.equals(destination)){
				throw new ServiceValidationException("Source and destination stations cannot be same.");
			}
			
			
			UserJourneyDTO userJourney = userJourneyBoDtoConverter.boToDto(null, user, swipeInTime, source, destination);
			
			return userJourneyDao.save(userJourney);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public UserJourneyBO findUserJourneyByScheduleTime(String userIdentifier,Date scheduleTime) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO userDTO = userDao.queryUserByIdentifier(userIdentifier);
			if(userDTO == null){
				throw new ServiceValidationException("Invalid user.No user exists with identifier: " + userIdentifier);
			}
			
			UserJourneyDTO userJourneyDTO = userJourneyDao.queryJourneyByScheduleTime(userIdentifier, scheduleTime);
			if(userJourneyDTO == null){
				return null;
			}
			
			MetroStationDTO sourceStationDTO = userJourneyDTO.getSourceStation();
			MetroStationDTO destStationDTO = userJourneyDTO.getDestinationStation();
			
			MetroStationBO sourceStationBO = stationBoDtoConverter.
					                            dtoToBo(sourceStationDTO.getStationId(), 
					                            		sourceStationDTO.getName(), 
					                            		sourceStationDTO.getLocation().getLatitude(), 
					                            		sourceStationDTO.getLocation().getLongitude());
			
			MetroStationBO destStationBO = stationBoDtoConverter.
                                                dtoToBo(destStationDTO.getStationId(), 
                                                		destStationDTO.getName(), 
                                                		destStationDTO.getLocation().getLatitude(), 
                                                		destStationDTO.getLocation().getLongitude());
			
			MetroUserBO userBO = userBoDtoConverter.dtoToBo(userDTO.getUserId(), userDTO.getUniqueIdentifier(), userDTO.getName());
			
			TrainJourneyDTO trainJourneyDTO = userJourneyDTO.getTrainJourney();
			TrainJourneyBO trainJourneyBO = null;
			if(trainJourneyDTO != null){
				MetroTrainDTO trainDTO = trainJourneyDTO.getTrain();
				RouteDTO routeDTO = trainDTO.getRoute();
				RouteBO routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
				MetroTrainBO trainBO = trainBoDtoConverter.dtoToBo(trainDTO.getTrainNumber(), trainDTO.getName(), routeBO);
				
				trainJourneyBO = trainJourneyBoDtoConverter.dtoToBo(trainJourneyDTO.getJourneyId(), 
						                                            trainJourneyDTO.getScheduledStartTime(), 
						                                            trainBO, 
						                                            trainJourneyDTO.getActualStartTime(), 
						                                            trainJourneyDTO.getActualEndTime());
			}
			UserJourneyBO userJourneyBO = userJourneyBoDtoConverter.
					                           dtoToBo(userJourneyDTO.getJourneyId(), 
					                        		   userBO, 
					                        		   userJourneyDTO.getSwipeInTime(), 
					                        		   sourceStationBO, 
					                        		   destStationBO, 
					                        		   trainJourneyBO, 
					                        		   userJourneyDTO.getActualStartTime(), 
					                        		   userJourneyDTO.getEndTime(),
					                        		   userJourneyDTO.getScheduledStartTime(),
					                        		   userJourneyDTO.getSwipeOutTime());
			
			return userJourneyBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void startUserJourney(String userIdentifier, int trainNumber,Date startTime) throws MetroSystemServiceException {

		try{
			//Validate user
			MetroUserDTO user = userDao.queryUserByIdentifier(userIdentifier);
			if(user == null){
				throw new ServiceValidationException("Invalid user.No user exists with identifier: " + userIdentifier);
			}
			
			//Validate train number
			MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
			if(train == null){
				throw new ServiceValidationException("Invalid train number.No train exists with number: " + trainNumber);
			}
			
			//Validate if any journey is scheduled for the train
			TrainJourneyDTO trainJourney = trainJourneyDao.queryLatestJourneyInProgress(trainNumber);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void finishUserJourney(String userIdentifier,Date endTime) throws MetroSystemServiceException {

	}

}
