package com.metrosystem.dao;

import java.util.Date;

import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IUserJourneyDao extends IMetroSystemDao<Integer, UserJourneyDTO> {

	public UserJourneyDTO queryJourneyByScheduleTime(String userIdentifier, Date scheduleTime) throws MetroSystemDaoException;

	public UserJourneyDTO queryLatestScheduledJourney(String userIdentifier) throws MetroSystemDaoException;
	
	public UserJourneyDTO queryLatestJourneyInProgress(String userIdentifier) throws MetroSystemDaoException;
	
	public UserJourneyDTO queryLatestJourneyToBeScheduled(String userIdentifier) throws MetroSystemDaoException;
}
