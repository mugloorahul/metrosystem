package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IUserJourneyDao;
import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("userJourneyDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class UserJourneyDaoImpl extends MetroSystemDaoImpl<Integer, UserJourneyDTO> implements
  IUserJourneyDao{

	
	public UserJourneyDaoImpl(){
		super(UserJourneyDTO.class);
	}
	
	@Override
	public UserJourneyDTO queryLatestUserJourney(String userIdentifier) throws MetroSystemDaoException{
		
		try{
			String query = "FROM UserJourneyDTO WHERE " +
		                   " WHERE scheduledStartTime = ("+ 
					                           "SELECT max(scheduledStartTime) "+ 
		                                       " FROM UserJourneyDTO " +
		                                       " WHERE user.uniqueIdentifier = ?" +
					                           "   GROUP BY user"+
		                                       ")" +
					       "   AND user.uniqueIdentifier = ?";
			
			List<UserJourneyDTO> results = this.queryListOfEntities(query, userIdentifier,userIdentifier);
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
