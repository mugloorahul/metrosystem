package com.metrosystem.dao;

import java.util.Date;
import java.util.List;

import com.metrosystem.dao.beans.TrainJourneyMonitorDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface ITrainJourneyMonitorDao extends IMetroSystemDao<Integer, TrainJourneyMonitorDTO> {

	public TrainJourneyMonitorDTO queryMonitorForStation(int trainNumber,String stationName) throws MetroSystemDaoException;
	
	public List<TrainJourneyMonitorDTO> queryMonitorsForNextStations(int trainNumber, String currentStation) throws MetroSystemDaoException;

    public TrainJourneyMonitorDTO queryPreviousStationMonitor(int trainNumber, String currentStation) throws MetroSystemDaoException;
    
    public TrainJourneyMonitorDTO queryNextStationMonitor(int trainNumber, String currentStation) throws MetroSystemDaoException;

    public List<TrainJourneyMonitorDTO> queryAllActiveMonitorsForStation(String station) throws MetroSystemDaoException;
    
    public List<TrainJourneyMonitorDTO> queryScheduledArrivalMonitorsForStation(String station) throws MetroSystemDaoException;
    
    public List<TrainJourneyMonitorDTO> queryScheduledDepartureMonitorsForStation(String station) throws MetroSystemDaoException;
    
    public List<TrainJourneyMonitorDTO> queryScheduledArrivalsForStation(String station, Date fromTime, Date toTime) throws MetroSystemDaoException;
    
    public List<TrainJourneyMonitorDTO> queryScheduledDepartureMonitorsForStation(String station, Date fromTime, Date toTime) throws MetroSystemDaoException;
}
