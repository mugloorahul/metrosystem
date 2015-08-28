package com.metrosystem.service.beans;

import java.util.HashSet;
import java.util.Set;


public class MetroStationBO {

	
	private int stationId;
	private String name;
	private Set<RouteBO> routes;
	private GeoLocationBO location;
	private Set<UserJourneyBO> boardedTravellers;
	private Set<UserJourneyBO> alightedTravellers;
	private Set<TrainJourneyMonitorBO> monitors;
	
	/**
	 * Default constructor
	 */
	public MetroStationBO(){
		
	}

	public MetroStationBO(String name, String latitude, String longitude){
		this.name = name;
		this.location = new GeoLocationBO(latitude, longitude);
	}
	
	public MetroStationBO(String name, Set<RouteBO> routes,
			              GeoLocationBO location, Set<UserJourneyBO> boardedTravellers,
			              Set<UserJourneyBO> alightedTravellers,
			              Set<TrainJourneyMonitorBO> monitors) 
	{
		this.name = name;
		this.routes = routes;
		this.location = location;
		this.boardedTravellers = boardedTravellers;
		this.alightedTravellers = alightedTravellers;
		this.monitors = monitors;
	}

	/**
	 * @return the stationId
	 */
	public int getStationId() {
		return stationId;
	}

	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(int stationId) {
		this.stationId = stationId;
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
	 * @return the routes
	 */
	public Set<RouteBO> getRoutes() {
		return routes;
	}

	/**
	 * @param routes the routes to set
	 */
	public void setRoutes(Set<RouteBO> routes) {
		this.routes = routes;
	}

	/**
	 * @return the location
	 */
	public GeoLocationBO getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(GeoLocationBO location) {
		this.location = location;
	}

	/**
	 * @return the boardedTravellers
	 */
	public Set<UserJourneyBO> getBoardedTravellers() {
		return boardedTravellers;
	}

	/**
	 * @param boardedTravellers the boardedTravellers to set
	 */
	public void setBoardedTravellers(Set<UserJourneyBO> boardedTravellers) {
		this.boardedTravellers = boardedTravellers;
	}

	/**
	 * @return the alightedTravellers
	 */
	public Set<UserJourneyBO> getAlightedTravellers() {
		return alightedTravellers;
	}

	/**
	 * @param alightedTravellers the alightedTravellers to set
	 */
	public void setAlightedTravellers(Set<UserJourneyBO> alightedTravellers) {
		this.alightedTravellers = alightedTravellers;
	}

	/**
	 * @return the monitors
	 */
	public Set<TrainJourneyMonitorBO> getMonitors() {
		return monitors;
	}

	/**
	 * @param monitors the monitors to set
	 */
	public void setMonitors(Set<TrainJourneyMonitorBO> monitors) {
		this.monitors = monitors;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof MetroStationBO)){
			return false;
		}
		
		MetroStationBO otherStation = (MetroStationBO)obj;
		
		return this.stationId == otherStation.stationId;
	}
	
	@Override
	public int hashCode(){
		return (this.stationId * 37);
	}

	public void addRoute(RouteBO route){
		if(this.getRoutes() == null){
			routes = new HashSet<RouteBO>();
		}
		routes.add(route);
		route.addStation(this);
	}
}
