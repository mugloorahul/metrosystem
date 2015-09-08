package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IMetroStationDao extends  IMetroSystemDao<Integer, MetroStationDTO>{

	public MetroStationDTO queryStationByName(String name) throws MetroSystemDaoException;
	
	public void insertMultipleStations(List<MetroStationDTO> stations) throws MetroSystemDaoException;
	
	public List<MetroStationDTO> queryStationsForRouteOrderedBySequence(String routeName) throws MetroSystemDaoException;

    public MetroStationDTO queryStationForRoute(String stationName,String routeName) throws MetroSystemDaoException;
    
    public MetroStationDTO queryStationForRouteAtSequence(String routeName,int sequence) throws MetroSystemDaoException;
    
    public MetroStationDTO queryLastStationForRoute(String routeName) throws MetroSystemDaoException;
}
