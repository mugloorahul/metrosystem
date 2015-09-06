package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.TrainScheduleBO;
import com.metrosystem.service.beans.TrainScheduleTimingBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface ITrainScheduleService {

	public TrainScheduleBO getTrainSchedule(int trainNumber) throws MetroSystemServiceException;; 
	
	public void createOrUpdateTrainSchedule(int trainNumber, List<TrainScheduleTimingBO> timings)
	throws MetroSystemServiceException;
	
	public List<TrainScheduleTimingBO> findNextStationsTimings(int trainNumber, String stationName) throws MetroSystemServiceException;
}
