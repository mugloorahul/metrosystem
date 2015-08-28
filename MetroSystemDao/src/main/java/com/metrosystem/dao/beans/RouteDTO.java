package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="route")

public class RouteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int routeId;
	private String name;
    private Set<StationRouteDTO> stationRoutes;
    private Set<MetroTrainDTO> trains;
    
    /**
     * Default constructor
     */
    public RouteDTO(){
    	
    }
    
    public RouteDTO(String name){
    	this.name=name;
    }
    
    
    public RouteDTO(String name,Set<StationRouteDTO> stationRoutes){
    	this(name);
    	this.stationRoutes=stationRoutes;
    }
    
    public RouteDTO(String name,Set<StationRouteDTO> stationRoutes,Set<MetroTrainDTO> trains){
    	this(name,stationRoutes);
    	this.trains=trains;
    }
    
	/**
	 * @return the routeId
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="route_id")
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
	@Column(name="name",length=50,nullable=false,unique=true)
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
	 * @return the trains
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="route",cascade=CascadeType.ALL)
	public Set<MetroTrainDTO> getTrains() {
		return trains;
	}
	/**
	 * @param trains the trains to set
	 */
	public void setTrains(Set<MetroTrainDTO> trains) {
		this.trains = trains;
	}
    
	/**
	 * @return the stationRoutes
	 */
	@OneToMany(mappedBy="route",cascade=CascadeType.ALL)
	public Set<StationRouteDTO> getStationRoutes() {
		return stationRoutes;
	}

	/**
	 * @param stationRoutes the stationRoutes to set
	 */
	public void setStationRoutes(Set<StationRouteDTO> stationRoutes) {
		this.stationRoutes = stationRoutes;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof RouteDTO)){
			return false;
		}
		
		RouteDTO otherRoute = (RouteDTO)obj;
		
		return otherRoute.routeId == this.routeId;
	}
	
	@Override
	public int hashCode(){
		return (this.routeId * 37);
	}
	
	public void addStation(StationRouteDTO stationRoute){
		if(this.stationRoutes == null){
			stationRoutes = new HashSet<StationRouteDTO>();
		}
		
		stationRoutes.add(stationRoute);
		stationRoute.getStation().addRoute(stationRoute);
	}
	
	public void attachTrain(MetroTrainDTO train){
		if(this.trains == null){
			trains = new HashSet<MetroTrainDTO>();
		}
		
		trains.add(train);
		train.attachRoute(this);
	}
}
