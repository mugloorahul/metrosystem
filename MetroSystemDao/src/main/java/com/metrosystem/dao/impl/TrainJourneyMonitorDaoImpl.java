package com.metrosystem.dao.impl;

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
					       "                       AND sr.station.name = ?" +
			               "                       AND sr.station=sr_inner.station" +
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


}
