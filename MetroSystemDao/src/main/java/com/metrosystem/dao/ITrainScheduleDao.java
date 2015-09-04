package com.metrosystem.dao;

import com.metrosystem.dao.beans.TrainScheduleDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface ITrainScheduleDao extends IMetroSystemDao<Integer, TrainScheduleDTO>{

	public TrainScheduleDTO queryByTrainNumber(int trainNumber) throws MetroSystemDaoException;
}
