package com.metrosystem.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.metrosystem.dao.IUserJourneyDao;
import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("userJourneyDao")
public class UserJourneyDaoImpl extends MetroSystemDaoImpl<Integer, UserJourneyDTO> implements IUserJourneyDao{

	public UserJourneyDaoImpl() {
		super(UserJourneyDTO.class);
	}

	@Override
	public UserJourneyDTO queryJourneyByScheduleTime(String userIdentifier,Date scheduleTime) throws MetroSystemDaoException {
		
		try{
			String query = " FROM UserJourneyDTO journey " +
	                   " WHERE journey.user.uniqueIdentifier = ? AND scheduledStartTime=?";
		
		List<UserJourneyDTO> journeys = this.queryListOfEntities(query, userIdentifier,scheduleTime);
		if(journeys == null || journeys.size() == 0){
			return null;
		}
		
		return journeys.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public UserJourneyDTO queryLatestScheduledJourney(String userIdentifier) throws MetroSystemDaoException {
		
		try{
			String query = "FROM UserJourneyDTO"+
		                   " WHERE user.uniqueIdentifier = ?" +
					       "   AND scheduledStartTime = (" +
		                   "                            SELECT max(scheduledStartTime)" +
					       "                            FROM UserJourneyDTO " +
		                   "                            WHERE user.uniqueIdentifier = ?" +
					       "                              AND actualStartTime IS  NULL" +
					       "                            GROUP BY user" +
		                   "                            )";
			
			List<UserJourneyDTO> results = this.queryListOfEntities(query, userIdentifier,userIdentifier);
			
			if(results == null || results.size() ==0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public UserJourneyDTO queryLatestJourneyInProgress(String userIdentifier) throws MetroSystemDaoException {
		
		try{
			String query = "FROM UserJourneyDTO"+
		                   " WHERE user.uniqueIdentifier = ?" +
					       "   AND actualStartTime   = (" +
		                   "                            SELECT max(actualStartTime)" +
					       "                            FROM UserJourneyDTO " +
		                   "                            WHERE user.uniqueIdentifier = ?" +
					       "                              AND actualStartTime IS NOT NULL" +
					       "                              AND endTime IS NULL" +
					       "                            GROUP BY user" +
		                   "                            )";
			
			List<UserJourneyDTO> results = this.queryListOfEntities(query, userIdentifier,userIdentifier);
			
			if(results == null || results.size() ==0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}		
	}

	
}
