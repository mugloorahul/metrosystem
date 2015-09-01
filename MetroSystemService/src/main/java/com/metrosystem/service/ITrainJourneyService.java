package com.metrosystem.service;

import java.util.Date;

import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface ITrainJourneyService {

	public Integer scheduleTrainJourney(int trainNumber, Date scheduleStartTime) throws MetroSystemServiceException;

    public TrainJourneyBO findTrainJourneyByScheduleTime(int trainNumber, Date scheduleTime) throws MetroSystemServiceException;

    public void startJourney(int trainNumber,Date journeyStartTime) throws MetroSystemServiceException;
    
    public void endJourney(int trainNumber,Date journeyEndTime) throws MetroSystemServiceException; 
   
}
