package com.metrosystem.service;

import java.util.Date;

import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface ITrainJourneyService {

    public Integer scheduleTrainJourney(int trainNumber, Date scheduleStartTime) throws MetroSystemServiceException;

    public TrainJourneyBO findTrainJourneyByScheduleTime(int trainNumber, Date scheduleTime) throws MetroSystemServiceException;

    public void startTrainJourney(int trainNumber,Date startTime) throws MetroSystemServiceException;
    
    public void finishTrainJourney(int trainNumber,Date endTime) throws MetroSystemServiceException;
    
    public void arriveAtStation(int trainNumber, String arrivalStation, Date arrivalTime) throws MetroSystemServiceException;
    
    public void departFromStation(int trainNumber, String departureStation, Date departureTime) throws MetroSystemServiceException;
}
