package com.metrosystem.service;

import java.util.Date;

import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IUserJourneyService {

	public Integer scheduleUserJourney(String userIdentifier,Date scheduleTime,
			String sourceStation,String destinationStation) throws MetroSystemServiceException;
	
	public void startUserJourney(Date startTime) throws MetroSystemServiceException;
	
	public void finishUserJourney(Date endTime) throws MetroSystemServiceException;
}
