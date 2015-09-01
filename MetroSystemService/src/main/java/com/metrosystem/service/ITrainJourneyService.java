package com.metrosystem.service;

import java.util.Date;

import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface ITrainJourneyService {

	public Integer scheduleTrainJourney(int trainNumber, Date scheduleStartTime) throws MetroSystemServiceException;

    public TrainJourneyBO findTrainJourneyByScheduleTime(int trainNumber, Date scheduleTime) throws MetroSystemServiceException;
}
