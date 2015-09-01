package com.metrosystem.service;

import java.util.List;

public interface IMetroTrainService {

	public Integer createTrain(int trainNumber,String name, String routeName) throws MetroSystemServiceException;
	
	public Integer createTrain(int trainNumber,String name) throws MetroSystemServiceException;
	
	public MetroTrainBO findTrainByNumber(int trainNumber) throws MetroSystemServiceException;
	
	public MetroTrainBO findTrainByName(String name) throws MetroSystemServiceException;
	
	public void assignRouteToTrain(String routeName, int trainNumber) throws MetroSystemServiceException;

	public void assignRouteToTrain(String routeName, String trainName) throws MetroSystemServiceException;
	
	public void createMultipleTrains(List<MetroTrainBO> trains) throws MetroSystemServiceException;
	
	public List<MetroTrainBO> getTrainsForRoute(String routeName) throws MetroSystemServiceException;
	
	public List<TrainJourneyBO> getAllTrainJourneys(int trainNumber) throws MetroSystemServiceException;
}
