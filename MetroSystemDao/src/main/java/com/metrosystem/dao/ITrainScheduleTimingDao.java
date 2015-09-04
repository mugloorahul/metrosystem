package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.TrainScheduleTimingDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface ITrainScheduleTimingDao extends IMetroSystemDao<Integer, TrainScheduleTimingDTO> {

	public List<TrainScheduleTimingDTO> queryTimingsForTrain(int trainNumber) throws MetroSystemDaoException;

    public TrainScheduleTimingDTO queryForStation(int trainNumber, String stationName) throws MetroSystemDaoException;
}
