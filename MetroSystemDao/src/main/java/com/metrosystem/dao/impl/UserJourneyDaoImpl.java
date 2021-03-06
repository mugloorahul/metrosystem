package com.metrosystem.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IUserJourneyDao;
import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("userJourneyDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class UserJourneyDaoImpl extends MetroSystemDaoImpl<Integer, UserJourneyDTO> implements IUserJourneyDao{

	public UserJourneyDaoImpl() {
		super(UserJourneyDTO.class);
	}

	@Override
	public UserJourneyDTO queryLatestJourneyWithoutSwipeOut(String userIdentifier) throws MetroSystemDaoException{
		
		try{
			String query = "SELECT journey" +
		                   " FROM UserJourneyDTO journey" +
					       " INNER JOIN journey.user user" +
		                   " WHERE user.uniqueIdentifier = ?" +
					       " AND journey.swipeInTime = (" +
		                   "                                SELECT max(swipeInTime)" +
					       "                                FROM UserJourneyDTO journey_inner" +
		                   "                                WHERE journey_inner.user = user" +
					       "                                  AND journey_inner.swipeOutTime IS NULL" +
		                   "                                GROUP BY journey_inner.user" +
					       "                            )";
			
			List<UserJourneyDTO> results = this.queryListOfEntities(query, userIdentifier);
			
			if(results == null || results.size() == 0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public UserJourneyDTO queryLatestSwippedInjourney(String userIdentifier) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT journey" +
		                   " FROM UserJourneyDTO journey" +
					       " INNER JOIN journey.user user" +
		                   " WHERE user.uniqueIdentifier = ?" +
					       "   AND journey.swipeInTime = (" +
		                   "                             SELECT max(swipeInTime)" +
					       "                             FROM UserJourneyDTO journey_inner" +
		                   "                             WHERE journey_inner.user = user" +
					       "                               AND journey_inner.swipeOutTime IS  NULL" +
		                   "                             GROUP BY journey_inner.user" +
					       "                             )";
			
            List<UserJourneyDTO> results = this.queryListOfEntities(query, userIdentifier);
			
			if(results == null || results.size() == 0){
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
			String query = "SELECT journey"+
		                   " FROM UserJourneyDTO journey" +
					       " INNER JOIN journey.user user" +
		                   " WHERE user.uniqueIdentifier = ?" +
					       "  AND journey.swipeInTime = (" +
		                   "                            SELECT max(swipeInTime)" +
					       "                            FROM UserJourneyDTO journey_inner" +
		                   "                            WHERE user = journey_inner.user" +
					       "                              AND journey_inner.boardedTime IS NOT NULL" +
		                   "                              AND journey_inner.alightedTime IS NULL" +
		                   "                            GROUP BY journey_inner.user" +
					       "                            )";
			
            List<UserJourneyDTO> results = this.queryListOfEntities(query, userIdentifier);
			
			if(results == null || results.size() == 0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public UserJourneyDTO queryJourneyBySwipeInTime(String userIdentifier,Date swipeInTime) throws MetroSystemDaoException {
		
		try{
		    String query = "SELECT journey" +
		    		       " FROM UserJourneyDTO journey" +
				           " INNER JOIN journey.user user" +
	                       " WHERE user.uniqueIdentifier = ?" +
				           " AND journey.swipeInTime = ?";
		    
		    List<UserJourneyDTO> results = this.queryListOfEntities(query, userIdentifier,swipeInTime);
		    
		    if(results == null || results.size() == 0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
	
}
