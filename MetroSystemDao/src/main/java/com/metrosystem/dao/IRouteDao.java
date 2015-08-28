package com.metrosystem.dao;

import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IRouteDao extends  IMetroSystemDao<Integer, RouteDTO>{

	public RouteDTO queryRouteByName(String name) throws MetroSystemDaoException;
}
