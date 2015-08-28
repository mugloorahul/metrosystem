package com.metrosystem.service.beans;

import java.util.Set;

public class MetroTrainBO {

	private int trainNumber;
	private String name;
	private RouteBO route;
	private Set<TrainJourneyBO> trainJourneys;
	
	/**
	 * Default constructor
	 */
	public MetroTrainBO(){
		
	}

	public MetroTrainBO(int trainNumber, String name, RouteBO route){
		this.trainNumber = trainNumber;
		this.name=name;
		this.route=route;
	}
	
	public MetroTrainBO(int trainNumber, String name, RouteBO route,
			            Set<TrainJourneyBO> trainJourneys) 
	{
		this.trainNumber = trainNumber;
		this.name = name;
		this.route = route;
		this.trainJourneys = trainJourneys;
	}

	/**
	 * @return the trainNumber
	 */
	public int getTrainNumber() {
		return trainNumber;
	}

	/**
	 * @param trainNumber the trainNumber to set
	 */
	public void setTrainNumber(int trainNumber) {
		this.trainNumber = trainNumber;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the route
	 */
	public RouteBO getRoute() {
		return route;
	}

	/**
	 * @param route the route to set
	 */
	public void setRoute(RouteBO route) {
		this.route = route;
	}

	/**
	 * @return the trainJourneys
	 */
	public Set<TrainJourneyBO> getTrainJourneys() {
		return trainJourneys;
	}

	/**
	 * @param trainJourneys the trainJourneys to set
	 */
	public void setTrainJourneys(Set<TrainJourneyBO> trainJourneys) {
		this.trainJourneys = trainJourneys;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroTrainBO)){
			return false;
		}
		
		MetroTrainBO otherUser = (MetroTrainBO)object;
		
		return this.trainNumber==otherUser.trainNumber;
	}
	
	@Override
	public int hashCode(){
		return (this.trainNumber*37);
	}
}
