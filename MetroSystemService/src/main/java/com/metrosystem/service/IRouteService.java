package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.RouteBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IRouteService {
	
	public Integer createRoute(String routeName) throws MetroSystemServiceException;
	
	public RouteBO findRouteByName(String routeName) throws MetroSystemServiceException;
	
	public List<RouteBO> getRoutesForStation(String stationName) throws MetroSystemServiceException;

}
