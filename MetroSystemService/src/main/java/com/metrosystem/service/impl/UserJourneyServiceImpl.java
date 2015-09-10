package com.metrosystem.service.impl;

import java.rmi.server.UnicastRemoteObject;
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
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.service.IUserJourneyService;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.exception.ServiceValidationException;
import com.metrosystem.service.utils.UserJourneyBoDtoConverter;

@Component("userJourneyService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class UserJourneyServiceImpl implements IUserJourneyService {

	@Autowired
	@Qualifier("userDao")
	private IMetroUserDao userDao;
	
	@Autowired
	@Qualifier("stationDao")
	private IMetroStationDao stationDao;
	
	@Autowired
	@Qualifier("userJourneyDao")
	private IUserJourneyDao userJourneyDao;
	
	@Autowired
	@Qualifier("userJourneyBoDtoConverter")
	private UserJourneyBoDtoConverter userJourneyConverter;
	
	@Autowired
	@Qualifier("trainDao")
	private IMetroTrainDao trainDao;
	
	@Autowired
	@Qualifier("trainJourneyDao")
	private ITrainJourneyDao trainJourneyDao;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer swipeIn(String userIdentifier, String swipeInStation,Date swipeInTime) throws MetroSystemServiceException {
		
		try{
			//Validate valid user
			MetroUserDTO user = userDao.queryUserByIdentifier(userIdentifier);
			if(user == null){
				throw new ServiceValidationException("Invalid user id. No user exists with identifier: " + userIdentifier);
			}
			//Validate station
			MetroStationDTO station = stationDao.queryStationByName(swipeInStation);
			if(station == null){
				throw new ServiceValidationException("Invalid swipe in station. No station exists with name: " + swipeInStation);
			}
			//Check if any user journey is there without swipe out time
			UserJourneyDTO incompleteJourney = userJourneyDao.queryLatestJourneyWithoutSwipeOut(userIdentifier);
			if(incompleteJourney != null){
				throw new ServiceValidationException("Swipe in failed. There is a previous incomplete journey with swipe in time as: " + incompleteJourney.getSwipeInTime());
			}
			
			UserJourneyDTO journey = userJourneyConverter.boToDto(null, user, swipeInTime, station);
			
			return userJourneyDao.save(journey);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void boardTrain(String userIdentifier, int trainNumber,Date boardTime) throws MetroSystemServiceException {
		
		try{
		    MetroUserDTO user = userDao.queryUserByIdentifier(userIdentifier);
		    if(user == null){
				throw new ServiceValidationException("Invalid user id. No user exists with identifier: " + userIdentifier);
			}
		    //Validate train
		    MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
		    if(train == null){
		    	throw new ServiceValidationException("Invalid train number. No train exists with number: " + trainNumber);
		    }
		    
		    //Get the latest swipe in journey
		    UserJourneyDTO swipeInJourney = userJourneyDao.queryLatestSwippedInjourney(userIdentifier);
		    if(swipeInJourney == null){
		    	throw new ServiceValidationException("User " + userIdentifier + " cannot board train without swiping at station.");
		    }
		    Date swipeInTime = swipeInJourney.getSwipeInTime();
		    if(boardTime.compareTo(swipeInTime) < 0){
		    	throw new ServiceValidationException("User " + userIdentifier + " cannot board before swipe in time: " + swipeInTime);
		    }
		    
		    //Check if any other journey is in progress
		    UserJourneyDTO journeyInProgress = userJourneyDao.queryLatestJourneyInProgress(userIdentifier);
		    if(journeyInProgress != null){
		    	throw new ServiceValidationException("Cannot board train.A journey is already in progress for the user " + userIdentifier);
		    }
		    //Check if there any active journey for the specified train
		    TrainJourneyDTO trainJourney = trainJourneyDao.queryLatestJourneyInProgress(trainNumber);
		    if(trainJourney == null){
		    	throw new ServiceValidationException("User " + userIdentifier + " cannot board train. No active journey found for train: " + trainNumber);
		    }
		    
		    swipeInJourney.setBoardedTime(boardTime);
		    userJourneyDao.update(swipeInJourney);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void alightTrain(String userIdentifier, int trainNumber,Date alightTime) throws MetroSystemServiceException {
		
		try{
			MetroUserDTO user = userDao.queryUserByIdentifier(userIdentifier);
			if(user == null){
				throw new ServiceValidationException("Invalid user id. No user exists with identifier: " + userIdentifier);
			}
		    //Validate train
		    MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
		    if(train == null){
		    	throw new ServiceValidationException("Invalid train number. No train exists with number: " + trainNumber);
		    }
		    
		    //Check if any  journey is in progress
		    UserJourneyDTO journeyInProgress = userJourneyDao.queryLatestJourneyInProgress(userIdentifier);
		    if(journeyInProgress == null){
		    	throw new ServiceValidationException("Cannot alight train.No journey found in progress for the user " + userIdentifier);
		    }
		    MetroTrainDTO boardedTrain = journeyInProgress.getTrainJourney().getTrain();
		    if(!boardedTrain.equals(train)){
		    	throw new ServiceValidationException("Incorrect train number specified: " + trainNumber + ". User had boarded train: " + boardedTrain.getTrainNumber());
		    }
		    Date boardTime = journeyInProgress.getBoardedTime();
		    if(alightTime.compareTo(boardTime) < 0){
		    	throw new ServiceValidationException("User " + userIdentifier + " cannot alight from train before board time: " + boardTime);
		    }
		    journeyInProgress.setAlightedTime(alightTime);
		    userJourneyDao.save(journeyInProgress);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void swipeOut(String userIdentifier, String swipeOutStation,Date swipeOutTime) throws MetroSystemServiceException {
		
		try{
			//Validate valid user
			MetroUserDTO user = userDao.queryUserByIdentifier(userIdentifier);
			if(user == null){
				throw new ServiceValidationException("Invalid user id. No user exists with identifier: " + userIdentifier);
			}
			//Validate station
			MetroStationDTO station = stationDao.queryStationByName(swipeOutStation);
			if(station == null){
				throw new ServiceValidationException("Invalid swipe out station. No station exists with name: " + swipeOutStation);
			}
			//Check if any journey is in progress
			UserJourneyDTO journeyInProgress = userJourneyDao.queryLatestJourneyInProgress(userIdentifier);
		    if(journeyInProgress != null){
		    	throw new ServiceValidationException("Cannot swipe out.A journey is already in progress for the user " + userIdentifier);
		    }
		    //Get the latest swipe in journey
		    UserJourneyDTO swipeInJourney = userJourneyDao.queryLatestSwippedInjourney(userIdentifier);
		    if(swipeInJourney == null){
		    	throw new ServiceValidationException("User " + userIdentifier + " cannot swipe out without swiping int at any station.");
		    }
		    Date swipeInTime = swipeInJourney.getSwipeInTime();
		    if(swipeOutTime.compareTo(swipeInTime) < 0){
		    	throw new ServiceValidationException("User " + userIdentifier + " cannot swipe out before swipe in time: " + swipeInTime);
		    
		    }
		    Date alightTime = swipeInJourney.getAlightedTime();
		    if(alightTime != null && alightTime.compareTo(swipeOutTime) > 0){
		    	throw new ServiceValidationException("User " + userIdentifier + " cannot swipe out before alight time: " + alightTime);
		    }
		    swipeInJourney.setSwipeOutTime(swipeOutTime);
		    userJourneyDao.update(swipeInJourney);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	
}
