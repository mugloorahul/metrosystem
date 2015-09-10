package com.metrosystem.service;

import java.util.Date;

import com.metrosystem.service.exception.MetroSystemServiceException;


public interface IUserJourneyService {

	public Integer swipeIn(String userIdentifier,String swipeInStation,Date swipeInTime) throws MetroSystemServiceException;
	
	public void boardTrain(String userIdentifier,int trainNumber, Date boardTime) throws MetroSystemServiceException;
	
	public void alightTrain(String userIdentifier,int trainNumber, Date alightTime) throws MetroSystemServiceException;
	
	public void swipeOut(String userIdentifier, String swipeOutStation, Date swipeOutTime) throws MetroSystemServiceException;
}
