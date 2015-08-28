package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroTrainDao;
import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;


@Repository("trainDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroTrainDaoImpl extends MetroSystemDaoImpl<Integer, MetroTrainDTO>
implements IMetroTrainDao{
	
	public MetroTrainDaoImpl(){
		super(MetroTrainDTO.class);
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createTrain(MetroTrainDTO train) throws MetroSystemDaoException {
		
		try{
			return this.save(train);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public MetroTrainDTO queryTrainByName(String trainName) throws MetroSystemDaoException {
		
		try{
			String query = " SELECT train " +
					       " FROM MetroTrainDTO train " +
		                   " LEFT OUTER JOIN train.route route " +
		                   " WHERE train.name = ?";
			List<MetroTrainDTO> trains = this.queryListOfEntities(query, trainName);
			
			if(trains == null || trains.size() == 0){
				return null;
			}
			
			return trains.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public MetroTrainDTO queryTrainByNumber(Integer trainNumber) throws MetroSystemDaoException {
		
		try{
			String query = " SELECT train " +
				       " FROM MetroTrainDTO train " +
	                   " LEFT OUTER JOIN train.route route " +
	                   " WHERE train.trainNumber = ?";
			List<MetroTrainDTO> trains = this.queryListOfEntities(query, trainNumber);
			if(trains == null || trains.size() == 0){
				return null;
			}
			
			return trains.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void batchInsertTrains(List<MetroTrainDTO> trains) throws MetroSystemDaoException {
		
		this.batchInsertEntities(trains);
		
	}

	
}
