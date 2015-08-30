package com.metrosystem.beans;

import java.awt.Point;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.metrosystem.travel.monitor.TrainMonitor;

public class MetroStation implements Serializable{

	/**
	 * Default serializable id
	 */
	private static final long serialVersionUID = 1L;

	private int stationId;
	private String name;
	private Set<Route> routes;
	private Collection<TrainMonitor> monitors;
	//private Semaphore monitorLock = new Semaphore(1);
	private Object monitorsLock = new Object();
	private Point stationLocation;
	
	public MetroStation(int stationId, String name, int stationXCoordinate, int stationYCoordinate){
		this.stationId = stationId;
		this.name = name;
		this.stationLocation = new Point(stationXCoordinate, stationYCoordinate);
	}
	
	public MetroStation(int stationId, String name,int stationXCoordinate, int stationYCoordinate, Set<Route> routes){
		this(stationId, name, stationXCoordinate, stationYCoordinate);
		this.routes = routes;
	}
	
	void addRoute(Route route){
		if(this.routes == null){
			routes = new HashSet<Route>();
		}
		
		routes.add(route);
	}
	
	public void setName(String stationName){
		this.name = stationName;
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof MetroStation)){
			return false;
		}
		
		MetroStation otherStation = (MetroStation)obj;
		
		return this.stationId == otherStation.stationId;
	}
	
	@Override
	public int hashCode(){
		return (this.stationId * 37);
	}
	
	public  void addTrainMonitor(TrainMonitor monitor){
		try{
			synchronized (monitorsLock) {
				//monitorLock.acquire();
				if(this.monitors == null){
					monitors = new HashSet<TrainMonitor>();
				}
				monitors.add(monitor);
				monitorsLock.notifyAll();
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			//monitorLock.release();
		}
	}
	
	public  void removeTrainMonitor(TrainMonitor monitor){
		try{
			//monitorLock.acquire();
			synchronized (monitors) {
				monitors.remove(monitor);		
			}

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			//monitorLock.release();
		}
	}
	
	public Point getLocation(){
		return (Point)this.stationLocation.clone();
	}
	
	public Collection<TrainMonitor> getMonitors(){
		
		synchronized (monitorsLock) {
			while (monitors == null || monitors.size() == 0) {
				try {
					monitorsLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					// e.printStackTrace();
				}
			}
		}
		return this.monitors;
	}
}
