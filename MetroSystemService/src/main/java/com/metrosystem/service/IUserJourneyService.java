package com.metrosystem.service;

import java.util.Date;

import com.metrosystem.service.exception.MetroSystemServiceException;


public interface IUserJourneyService {

	public Integer swipeIn(String userIdentifier,String swipeInStation,Date swipeInTime) throws MetroSystemServiceException;
}
