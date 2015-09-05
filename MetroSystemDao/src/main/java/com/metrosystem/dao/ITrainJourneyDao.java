package com.metrosystem.dao;

import java.util.Date;

import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface ITrainJourneyDao extends IMetroSystemDao<Integer, TrainJourneyDTO>{

	public TrainJourneyDTO queryJourneyByScheduleTime(int trainNumber, Date scheduleTime) throws MetroSystemDaoException;
	
	public TrainJourneyDTO queryLatestScheduledJourney(int trainNumber) throws MetroSystemDaoException;

    public TrainJourneyDTO queryLatestJourneyInProgress(int trainNumber) throws MetroSystemDaoException;

}
