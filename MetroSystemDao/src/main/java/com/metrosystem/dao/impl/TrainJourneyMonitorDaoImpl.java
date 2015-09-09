package com.metrosystem.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainJourneyMonitorDao;
import com.metrosystem.dao.beans.TrainJourneyMonitorDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("trainJourneyMonitorDao")
@Transactional(readOnly = false,rollbackFor={Exception.class})
public class TrainJourneyMonitorDaoImpl extends MetroSystemDaoImpl<Integer, TrainJourneyMonitorDTO> 
implements ITrainJourneyMonitorDao {

	public TrainJourneyMonitorDaoImpl(){
		super(TrainJourneyMonitorDTO.class);
	}

	@Override
	public TrainJourneyMonitorDTO queryMonitorForStation(int trainNumber,String stationName) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
					       " INNER JOIN monitor.trainJourney journey" +
		                   " WHERE journey.train.trainNumber =?" +
					       "  AND monitor.station.name = ?" +
		                   "  AND journey.scheduledStartTime = (" +
					       "                                    SELECT max(scheduledStartTime)" +
		                   "                                    FROM TrainJourneyDTO journey_inner" +
					       "                                    WHERE journey_inner.train = journey.train" +
		                   "                                    GROUP BY journey_inner.train" +
					       "                                   )";
			
			List<TrainJourneyMonitorDTO> monitors = this.queryListOfEntities(query, trainNumber,stationName);
			if(monitors == null || monitors.size() == 0){
				return null;
			}
			
			return monitors.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public List<TrainJourneyMonitorDTO> queryMonitorsForNextStations(int trainNumber, String currentStation) throws MetroSystemDaoException {
		
		try{
	
			String query = "SELECT monitor" +
			               " FROM TrainJourneyMonitorDTO monitor" +
					       " INNER JOIN monitor.trainJourney journey" +
			               " INNER JOIN journey.train train" +
					       " INNER JOIN train.route route " +
			               " INNER JOIN route.stationRoutes sr " +
					       " WHERE train.trainNumber=?" + 
			               "   AND sr.station=monitor.station" +
			               "   AND sr.sequence > (" +
			               "                     SELECT sequence" +
					       "                     FROM StationRouteDTO sr_inner" +
			               "                     WHERE sr_inner.route = route" +
					       "                       AND sr_inner.station.name = ?" +
			               "                     )" +
					       "  AND journey.scheduledStartTime=("+
					       "                                  SELECT max(scheduledStartTime)" +
					       "                                  FROM TrainJourneyDTO journey_inner" +
		                   "                                  WHERE journey_inner.train = journey.train" +
					       "                                  GROUP BY journey_inner.train" +
		                   "                                 )"+
					       " ORDER BY sr.sequence";
					                        
			
			List<TrainJourneyMonitorDTO> monitors = this.queryListOfEntities(query, trainNumber,currentStation);
			
			
			return monitors;
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public TrainJourneyMonitorDTO queryPreviousStationMonitor(int trainNumber,String currentStation) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
				           " INNER JOIN monitor.trainJourney journey" +
		                   " INNER JOIN journey.train train" +
				           " INNER JOIN train.route route " +
		                   " INNER JOIN route.stationRoutes sr " +
				           " WHERE train.trainNumber=?" + 
		                   "   AND sr.station=monitor.station" +
		                   "   AND sr.sequence = (" +
		                   "                     SELECT sequence-1" +
				           "                     FROM StationRouteDTO sr_inner" +
		                   "                     WHERE sr_inner.route = route" +
				           "                       AND sr_inner.station.name = ?" +
		                   "                     )" +
				           "  AND journey.scheduledStartTime=("+
				           "                                  SELECT max(scheduledStartTime)" +
				           "                                  FROM TrainJourneyDTO journey_inner" +
	                       "                                  WHERE journey_inner.train = journey.train" +
				           "                                  GROUP BY journey_inner.train" +
	                       "                                 )";
			
			List<TrainJourneyMonitorDTO> results = this.queryListOfEntities(query, trainNumber,currentStation);
			if(results == null || results.size() ==0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public TrainJourneyMonitorDTO queryNextStationMonitor(int trainNumber,String currentStation) throws MetroSystemDaoException {

		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
				           " INNER JOIN monitor.trainJourney journey" +
		                   " INNER JOIN journey.train train" +
				           " INNER JOIN train.route route " +
		                   " INNER JOIN route.stationRoutes sr " +
				           " WHERE train.trainNumber=?" + 
		                   "   AND sr.station=monitor.station" +
		                   "   AND sr.sequence = (" +
		                   "                     SELECT sequence+1" +
				           "                     FROM StationRouteDTO sr_inner" +
		                   "                     WHERE sr_inner.route = route" +
				           "                       AND sr_inner.station.name = ?" +
		                   "                     )" +
				           "  AND journey.scheduledStartTime=("+
				           "                                  SELECT max(scheduledStartTime)" +
				           "                                  FROM TrainJourneyDTO journey_inner" +
	                       "                                  WHERE journey_inner.train = journey.train" +
				           "                                  GROUP BY journey_inner.train" +
	                       "                                 )";
			
			List<TrainJourneyMonitorDTO> results = this.queryListOfEntities(query, trainNumber,currentStation);
			if(results == null || results.size() ==0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}		
	}

	@Override
	public List<TrainJourneyMonitorDTO> queryAllActiveMonitorsForStation(String station) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
					       " WHERE monitor.station.name = ?" +
					       "  AND (monitor.actualArrivalTime IS NULL" +
		                   "       OR monitor.actualDepartureTime IS NULL)";
			
			return this.queryListOfEntities(query, station);
					       
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public List<TrainJourneyMonitorDTO> queryScheduledArrivalMonitorsForStation(String station) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
					       " WHERE monitor.station.name = ?" +
		                   "  AND monitor.scheduledArrivalTime IS NOT NULL" +
					       "  AND monitor.actualArrivalTime IS NULL" +
		                   " ORDER BY monitor.scheduledArrivalTime";
			
			return this.queryListOfEntities(query, station);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public List<TrainJourneyMonitorDTO> queryScheduledDepartureMonitorsForStation(String station) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
					       " WHERE monitor.station.name = ?" +
		                   "  AND monitor.scheduledDepartureTime IS NOT NULL" +
					       "  AND monitor.actualDepartureTime IS NULL" +
		                   " ORDER BY monitor.scheduledDepartureTime";
			
			return this.queryListOfEntities(query, station);
			
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public List<TrainJourneyMonitorDTO> queryScheduledArrivalsForStation(String station, Date fromTime, Date toTime) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
					       " WHERE monitor.station.name = ?" +
		                   "  AND monitor.scheduledArrivalTime IS NOT NULL" +
					       "  AND monitor.actualArrivalTime IS NULL" +
		                   "  AND monitor.scheduledArrivalTime >= ?" +
					       " AND monitor.scheduledArrivalTime <= ?" +
		                   " ORDER BY monitor.scheduledArrivalTime";
			
			return this.queryListOfEntities(query, station,fromTime,toTime);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}		
	}

	@Override
	public List<TrainJourneyMonitorDTO> queryScheduledDepartureMonitorsForStation(String station, Date fromTime, Date toTime) throws MetroSystemDaoException {
		
		try{
			String query = "SELECT monitor" +
		                   " FROM TrainJourneyMonitorDTO monitor" +
					       " WHERE monitor.station.name = ?" +
		                   "  AND monitor.scheduledDepartureTime IS NOT NULL" +
					       "  AND monitor.actualDepartureTime IS NULL" +
		                   "  AND monitor.scheduledDepartureTime >= ?" +
					       "  AND monitor.scheduledDepartureTime <= ?" +
		                   " ORDER BY monitor.scheduledDepartureTime";
			
			return this.queryListOfEntities(query, station,fromTime,toTime);
			
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}


}
