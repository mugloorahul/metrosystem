package com.metrosystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainScheduleDao;
import com.metrosystem.service.ITrainScheduleService;
import com.metrosystem.service.beans.TrainScheduleBO;
import com.metrosystem.service.beans.TrainScheduleTimingBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

@Component("trainScheduleService")
@Transactional(readOnly=false,rollbackFor={Exception.class})
public class TrainScheduleServiceImpl implements ITrainScheduleService {

	@Autowired
	@Qualifier("trainScheduleDao")
	private ITrainScheduleDao trainScheduleDao;
	
	@Override
	public TrainScheduleBO getTrainSchedule(int trainNumber)
			throws MetroSystemServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer createTrainSchedule(int trainNumber,
			List<TrainScheduleTimingBO> timings)
			throws MetroSystemServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
