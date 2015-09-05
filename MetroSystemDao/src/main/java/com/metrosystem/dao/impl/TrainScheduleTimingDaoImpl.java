package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainScheduleTimingDao;
import com.metrosystem.dao.beans.TrainScheduleTimingDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("trainScheduleTimingDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class TrainScheduleTimingDaoImpl extends MetroSystemDaoImpl<Integer, TrainScheduleTimingDTO> 
implements ITrainScheduleTimingDao 
{
   public TrainScheduleTimingDaoImpl(){
	  super(TrainScheduleTimingDTO.class);   
   }

   @Override
   public List<TrainScheduleTimingDTO> queryTimingsForTrain(int trainNumber) throws MetroSystemDaoException {
	
	   try{
		   String query = "SELECT timing" +
				          " FROM TrainScheduleTimingDTO timing" +
	                      " INNER JOIN timing.trainSchedule schedule" +
				          " INNER JOIN schedule.train train" +
	                      " INNER JOIN train.route route" +
				          " INNER JOIN route.stationRoutes stationRoute"+
	                      " WHERE stationRoute.station = timing.station"+
				          "   AND train.trainNumber = ?" +
	                      " ORDER BY stationRoute.sequence";
		   
		   return this.queryListOfEntities(query, trainNumber);
	   }
	   catch(Throwable e){
		   throw new MetroSystemDaoException(e);
	   }
	
   }

   @Override
   public TrainScheduleTimingDTO queryForStation(int trainNumber,String stationName) throws MetroSystemDaoException {
	
	   try{
		   String query = "FROM TrainScheduleTimingDTO" +
	                      " WHERE trainSchedule.train.trainNumber=?" +
				          " AND station.name=?";
		   
		   List<TrainScheduleTimingDTO> results = this.queryListOfEntities(query, trainNumber,stationName);
		   
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
   public List<TrainScheduleTimingDTO> queryNextStationsTimings(int trainNumber,String stationName) throws MetroSystemDaoException {
	    
	   try{
		   String query = "SELECT timing" +
			              " FROM TrainScheduleTimingDTO timing" +
                          " INNER JOIN timing.trainSchedule schedule" +
			              " INNER JOIN schedule.train train" +
                          " INNER JOIN train.route route" +
			              " INNER JOIN route.stationRoutes stationRoute"+
                          " WHERE stationRoute.station = timing.station"+
			              "   AND train.trainNumber = ?" +
                          "   AND stationRoute.sequence > (" +
			              "                               SELECT sequence" +
                          "                               FROM StationRouteDTO sr_inner" +
			              "                               WHERE sr_inner.route.routeId = route.routeId" +
                          "                                  AND sr_inner.station.name = ?" +
			              "                               )" +
                          " ORDER BY stationRoute.sequence";
		   
		   return this.queryListOfEntities(query, trainNumber,stationName);
	   }
	   catch(Throwable e){
		   throw new MetroSystemDaoException(e);
	   }
   }
}
