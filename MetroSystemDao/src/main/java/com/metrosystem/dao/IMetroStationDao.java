package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IMetroStationDao extends  IMetroSystemDao<Integer, MetroStationDTO>{

	public MetroStationDTO queryStationByName(String name) throws MetroSystemDaoException;
	
	public void insertMultipleStations(List<MetroStationDTO> stations) throws MetroSystemDaoException;
	
}
