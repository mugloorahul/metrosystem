package com.metrosystem.service.beans;

import java.util.HashSet;
import java.util.Set;

public class RouteBO {

	private int routeId;
	private String name;
    private Set<MetroStationBO> stations;
    private Set<MetroTrainBO> trains;
    
    /**
     * Default constructor
     */
    public RouteBO(){
    	
    }
    
    public RouteBO(String name){
    	this.name = name;
    }

	public RouteBO(String name, Set<MetroStationBO> stations,
			       Set<MetroTrainBO> trains) 
	{
		this.name = name;
		this.stations = stations;
		this.trains = trains;
	}

	/**
	 * @return the routeId
	 */
	public int getRouteId() {
		return routeId;
	}

	/**
	 * @param routeId the routeId to set
	 */
	public void setRouteId(int routeId) {
		this.routeId = routeId;
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
	 * @return the stations
	 */
	public Set<MetroStationBO> getStations() {
		return stations;
	}

	/**
	 * @param stations the stations to set
	 */
	public void setStations(Set<MetroStationBO> stations) {
		this.stations = stations;
	}

	/**
	 * @return the trains
	 */
	public Set<MetroTrainBO> getTrains() {
		return trains;
	}

	/**
	 * @param trains the trains to set
	 */
	public void setTrains(Set<MetroTrainBO> trains) {
		this.trains = trains;
	}
    
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof RouteBO)){
			return false;
		}
		
		RouteBO otherRoute = (RouteBO)obj;
		
		return otherRoute.routeId == this.routeId;
	}
	
	@Override
	public int hashCode(){
		return (this.routeId * 37);
	}
	
	public void addStation(MetroStationBO station){
		if(this.getStations() == null){
			stations = new HashSet<MetroStationBO>();
		}
		stations.add(station);
	}
    
}
