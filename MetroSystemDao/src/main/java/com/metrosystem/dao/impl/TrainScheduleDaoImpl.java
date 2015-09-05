package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ITrainScheduleDao;
import com.metrosystem.dao.beans.TrainScheduleDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("trainScheduleDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class TrainScheduleDaoImpl extends MetroSystemDaoImpl<Integer, TrainScheduleDTO> implements
   ITrainScheduleDao
{

	public TrainScheduleDaoImpl(){
		super(TrainScheduleDTO.class);
	}

	@Override
	public TrainScheduleDTO queryByTrainNumber(int trainNumber) throws MetroSystemDaoException {
		
		try{
			String query = "FROM TrainScheduleDTO" +
		                   " WHERE train.trainNumber=?";
			
			List<TrainScheduleDTO> results = this.queryListOfEntities(query, trainNumber);
			if(results == null || results.size() == 0){
				return null;
			}
			
			return results.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
}
