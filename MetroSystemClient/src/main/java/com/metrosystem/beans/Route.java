package com.metrosystem.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Route implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int routeId;
	private String routeName;
	
	private List<MetroStation> stations;
	private List<MetroTrain> trains;
	
	public Route(int routeId, String routeName,List<MetroStation> stations){
		this.routeId = routeId;
		this.routeName = routeName;
		this.stations = stations;
	}

	public Route(int routeId, String routeName, MetroStation startingStation){
		
		this(routeId,routeName,new LinkedList<MetroStation>());
		//this.stations.add(startingStation);

	}
	
	public Route(int routeId, String routeName){
		this.routeId = routeId;
		this.routeName = routeName;
	}
	
	public void setRouteName(String name){
		this.routeName = name;
	}
	
	public String getRouteName(){
		return this.routeName;
	}
	
	public List<MetroStation> getStations(){
		return new ArrayList<MetroStation>(this.stations);
	}
	
	public boolean addStation(MetroStation station){
		if(checkStationExists(station)){
		    return false;	
		}
		
		if(this.stations == null){
			this.stations = new LinkedList<MetroStation>();			
		}
		this.stations.add(station);
		station.addRoute(this);
		
		return true;
	}
	
	private boolean checkStationExists(MetroStation inputStation){
		if(stations != null && stations.size() > 0){
			for(MetroStation station : stations){
				if(station.equals(inputStation)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean checkTrainExists(MetroTrain inputTrain){
		if(trains != null && trains.size() > 0){
			for(MetroTrain train : trains){
				if(train.equals(inputTrain)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public MetroStation getStartStation(){
		if(this.stations == null || this.stations.size() == 0){
			return null;
		}
		
		return this.stations.get(0);
	}
	
	public MetroStation getEndStation(){
		if(this.stations == null || this.stations.size() <= 1){
			return null;
		}
		
		return this.stations.get(this.stations.size()-1);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof Route)){
			return false;
		}
		
		Route otherRoute = (Route)obj;
		
		return otherRoute.routeId == this.routeId;
	}
	
	@Override
	public int hashCode(){
		return (this.routeId * 37);
	}
	
	public boolean addTrainToRoute(MetroTrain train){
		
		if(checkTrainExists(train)){
			return false;
		}
		
		if(this.trains == null){
			trains = new ArrayList<>();
		}
		trains.add(train);
		
		return true;
	}
	
	public MetroStation getNextStation(MetroStation currentStation){
		if(currentStation == null){
			return null;
		}
		
	    int stationIndex = stations.indexOf(currentStation);
	    if(stationIndex < 0 || stationIndex >= stations.size()-1){
	    	return null;
	    }
	    
	    return stations.get(++stationIndex);
	}
	
	public boolean isCorrectRoute(MetroStation source, MetroStation destination){
		if(checkStationExists(source) && checkStationExists(destination)){
			int sourceIndex = this.stations.indexOf(source);
			int destIndex = this.stations.indexOf(destination);
			
			if(destIndex > sourceIndex){
				return true;
			}
		}
		
		return false;
	}
}
