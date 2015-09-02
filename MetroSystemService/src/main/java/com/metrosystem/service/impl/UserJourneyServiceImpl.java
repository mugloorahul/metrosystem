package com.metrosystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainJourneyDao;
import com.metrosystem.dao.IUserJourneyDao;
import com.metrosystem.service.IUserJourneyService;
import com.metrosystem.service.beans.UserJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

@Component("userJourneyService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class UserJourneyServiceImpl implements IUserJourneyService {

	@Autowired
	@Qualifier("userJourneyDao")
	private IUserJourneyDao userJourneyDao;
	
	@Autowired
	@Qualifier("trainJourneyDao")
	private ITrainJourneyDao trainJourneyDao;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer scheduleUserJourney(String userIdentifier, int trainNumber,Date scheduleStartTime) throws MetroSystemServiceException {
		
		return null;
	}

	@Override
	public UserJourneyBO findUserJourneyByScheduleTime(String userIdentifier,Date scheduleTime) throws MetroSystemServiceException {
		
		return null;
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void startUserJourney(String userIdentifier, int trainNumber,Date startTime) throws MetroSystemServiceException {

	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void finishUserJourney(String userIdentifier, int trainNumber,Date endTime) throws MetroSystemServiceException {

	}

}
