package com.metrosystem.dao;

import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IUserJourneyDao extends IMetroSystemDao<Integer, UserJourneyDTO>{

	public UserJourneyDTO queryLatestUserJourney(String userIdentifier) throws MetroSystemDaoException; 
}
