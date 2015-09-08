package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.TrainJourneyMonitorDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface ITrainJourneyMonitorDao extends IMetroSystemDao<Integer, TrainJourneyMonitorDTO> {

	public TrainJourneyMonitorDTO queryMonitorForStation(int trainNumber,String stationName) throws MetroSystemDaoException;
	
	public List<TrainJourneyMonitorDTO> queryMonitorsForNextStations(int trainNumber, String currentStation) throws MetroSystemDaoException;
}
