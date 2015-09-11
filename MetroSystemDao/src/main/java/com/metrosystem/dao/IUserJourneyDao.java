package com.metrosystem.dao;

import java.util.Date;

import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IUserJourneyDao extends IMetroSystemDao<Integer, UserJourneyDTO> {

	public UserJourneyDTO queryLatestJourneyWithoutSwipeOut(String userIdentifier) throws MetroSystemDaoException;

    public UserJourneyDTO queryLatestSwippedInjourney(String userIdentifier) throws MetroSystemDaoException;
    
    public UserJourneyDTO queryLatestJourneyInProgress(String userIdentifier) throws MetroSystemDaoException;

    public UserJourneyDTO queryJourneyBySwipeInTime(String userIdentifier,Date swipeInTime) throws MetroSystemDaoException;
}
