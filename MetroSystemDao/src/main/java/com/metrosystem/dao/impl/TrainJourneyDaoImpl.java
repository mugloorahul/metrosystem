package com.metrosystem.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainJourneyDao;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("trainJourneyDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class TrainJourneyDaoImpl extends MetroSystemDaoImpl<Integer, TrainJourneyDTO> implements
   ITrainJourneyDao{

	public TrainJourneyDaoImpl(){
		super(TrainJourneyDTO.class);
	}

	@Override
	public TrainJourneyDTO queryJourneyByScheduleTime(int trainNumber,Date scheduleTime) throws MetroSystemDaoException {
		
		try{
			String query = " FROM TrainJourneyDTO journey " +
		                   " WHERE journey.train.trainNumber = ? AND scheduledStartTime=?";
			
			List<TrainJourneyDTO> journeys = this.queryListOfEntities(query, trainNumber,scheduleTime);
			if(journeys == null || journeys.size() == 0){
				return null;
			}
			
			return journeys.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
}
