package com.metrosystem.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.metrosystem.dao.IUserJourneyDao;
import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
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
			String query = "SELECT userJourney" + 
		                   "      ,sourceStation" +
					       "      ,destinationStation"+
		                   "      ,trainJourney"+
					       "      ,train"+
		                   "      ,route"+
		                   " FROM UserJourneyDTO userJourney " +
		                   "    INNER JOIN userJourney.sourceStation sourceStation" +
		                   "    INNER JOIN userJourney.destinationStation destinationStation" +
		                   "    INNER JOIN userJourney.trainJourney trainJourney" +
		                   "    INNER JOIN trainJourney.train train" +
		                   "    INNER JOIN train.route route" +
	                       " WHERE userJourney.user.uniqueIdentifier = ? AND userJourney.scheduledStartTime=?";
		
		List<?> results = this.queryListOfEntities(query, userIdentifier,scheduleTime);
		if(results == null || results.size() == 0){
			return null;
		}
		
		UserJourneyDTO userJourney = null;
		for(int i=0; i < results.size();i++){
			Object[] entities = (Object[]) results.get(i);
			userJourney = (UserJourneyDTO) entities[0];
			userJourney.setSourceStation((MetroStationDTO)entities[1]);
			userJourney.setDestinationStation((MetroStationDTO)entities[2]);
			userJourney.setTrainJourney((TrainJourneyDTO)entities[3]);
			userJourney.getTrainJourney().setTrain((MetroTrainDTO)entities[4]);
			userJourney.getTrainJourney().getTrain().setRoute((RouteDTO)entities[5]);
		}
		
		return userJourney;
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
		                   "                              AND swipeInTime IS NOT NULL" +
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

	@Override
	public UserJourneyDTO queryLatestJourneyToBeScheduled(String userIdentifier) throws MetroSystemDaoException {
		
		try{
			String query = "FROM UserJourneyDTO"+
		                   " WHERE user.uniqueIdentifier = ?" +
					       "   AND swipeInTime = (" +
		                   "                            SELECT max(swipeInTime)" +
					       "                            FROM UserJourneyDTO " +
		                   "                            WHERE user.uniqueIdentifier = ?" +
					       "                              AND actualStartTime IS  NULL" +
		                   "                              AND scheduledStartTime IS NULL" +
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
