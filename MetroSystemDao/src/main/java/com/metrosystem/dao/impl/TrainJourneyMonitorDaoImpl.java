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
		                   " WHERE journey.train.train_number =?" +
					       "  AND monitor.station.name = ?" +
		                   "  AND journey.scheduledStartTime = (" +
					       "                                    SELECT max(scheduledStartTime)" +
		                   "                                    FROM TrainJourneyDTO journey_inner" +
					       "                                    WHERE journey_inner.train.trainNumber = journey.train.train_number" +
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
					       "     ,TrainJourneyDTO journey" +
		                   "     ,StationRouteDTO stationRoute" +
					       "     ,StationRouteDTO stationRoute1" +
		                   " WHERE journey = monitor.trainJourney" +
					       "   AND journey.train.trainNumber=?" +
		                   "   AND stationRoute.route = journey.train.trainNumber" +
		                   "   AND stationRoute.route = stationRoute1.route" +
		                   "   AND stationRoute1.station.name=?" +
					       "   AND stationRoute.sequence > stationRoute1.sequence" +
		                   "   AND stationRoute.station.name = monitor.station.name" +
		                   "   AND stationRoute1.station.name = monitor.station.name" +
		                   "   AND journey.scheduledStartTime = (" +
					       "                                    SELECT max(scheduledStartTime)" +
		                   "                                    FROM TrainJourneyDTO journey_inner" +
					       "                                    WHERE journey_inner.train.trainNumber = journey.train.train_number" +
		                   "                                    GROUP BY journey_inner.train" +
					       "                                    )" +
		                   " ORDER BY stationRoute.sequence";
			
			List<TrainJourneyMonitorDTO> monitors = this.queryListOfEntities(query, trainNumber,currentStation);
			
			
			return monitors;
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}


}
