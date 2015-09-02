package com.metrosystem.service;

import java.util.Date;

import com.metrosystem.service.beans.UserJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IUserJourneyService {

	public Integer scheduleUserJourney(String userIdentifier,int trainNumber, Date scheduleStartTime) throws MetroSystemServiceException;

    public UserJourneyBO findUserJourneyByScheduleTime(String userIdentifier, Date scheduleTime) throws MetroSystemServiceException;

    public void startUserJourney(String userIdentifier,int trainNumber,Date startTime) throws MetroSystemServiceException;
    
    public void finishUserJourney(String userIdentifier,int trainNumber,Date endTime) throws MetroSystemServiceException;
}
