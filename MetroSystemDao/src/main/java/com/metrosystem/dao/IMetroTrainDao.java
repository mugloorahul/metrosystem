package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IMetroTrainDao extends IMetroSystemDao<Integer, MetroTrainDTO>{

	public Integer createTrain(MetroTrainDTO train) throws MetroSystemDaoException;
	
	public MetroTrainDTO queryTrainByName(String trainName) throws MetroSystemDaoException;
	
	public MetroTrainDTO queryTrainByNumber(Integer trainNumber) throws MetroSystemDaoException;
	
	public void batchInsertTrains(List<MetroTrainDTO> trains) throws MetroSystemDaoException;
}
