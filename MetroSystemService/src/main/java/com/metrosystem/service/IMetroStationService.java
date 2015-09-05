package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IMetroStationService {

	public void addStationToRoute(String stationName,String routeName,int sequence) 
	throws MetroSystemServiceException;
	
	public Integer createStation(String name, String latitude, String longitude) throws MetroSystemServiceException;
	
	public Integer createStationForRoute(String stationName, String latitude,String longitude,String routeName,int sequence)
	throws MetroSystemServiceException;
	
	public MetroStationBO findStationByName(String stationName) throws MetroSystemServiceException;
	
	public List<MetroStationBO> getStationsForRoute(String routeName) throws MetroSystemServiceException;
	
	public void changeStationSequenceForRoute(String stationName, String routeName, int newSequence)
	throws MetroSystemServiceException;
}
