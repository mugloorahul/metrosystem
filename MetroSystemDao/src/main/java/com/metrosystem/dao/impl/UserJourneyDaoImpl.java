package com.metrosystem.dao.impl;

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
			String query = "SELECT userJourney" +
		                   " FROM UserJourneyDTO journey" +
					       " INNER JOIN journey.user" +
		                   " WHERE user.uniqueIdentifier = ?" +
					       " AND journey.swipeOutTime = (" +
		                   "                                SELECT max(swipeOutTime)" +
					       "                                FROM UserJourneyDTO journey_inner" +
		                   "                                WHERE journey_inner.user = journey.user" +
					       "                                  AND swipeOutTime IS NULL" +
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
	
}
