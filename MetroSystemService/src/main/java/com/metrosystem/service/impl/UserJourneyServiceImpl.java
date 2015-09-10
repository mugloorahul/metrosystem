package com.metrosystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroStationDao;
import com.metrosystem.dao.IMetroUserDao;
import com.metrosystem.dao.IUserJourneyDao;
import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
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

	
}
