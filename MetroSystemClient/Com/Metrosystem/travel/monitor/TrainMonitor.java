package com.metrosystem.travel.monitor;

import java.io.Serializable;
import java.util.Date;

import com.metrosystem.beans.MetroStation;
import com.metrosystem.beans.MetroTrain;
import com.metrosystem.beans.Route;
import com.metrosystem.exception.MetroSystemException;

public class TrainMonitor implements Serializable{

	/**
	 *Default serializable id 
	 */
	private static final long serialVersionUID = 1L;

	/* Train to be monitored*/
	private MetroTrain train;
	
	/* Current station where the train is*/
	private MetroStation currentStation;
	
	/* Train's next station*/
	private MetroStation destStation;
	
	/* Train's arrival time at the current station*/
	private Date actualArrivalTime;
	
	/* Next station expected arrival time*/
	private Date scheduleArrivalTime;
	
	/* Train's departure time from current station*/
	private Date scheduleDepartureTime;
	
	private Date actualDepartureTime;
	
	
	/*Last station from where train departed*/
	private MetroStation lastDepartedStation;
	
	private Object currentStationLock = new Object();
	
	
	public TrainMonitor(MetroTrain train){
		this.train = train;
	}

	public MetroTrain getTrain(){
		return this.train;
	}
	
	public boolean isLastStation(){
		MetroStation lastStation = train.getRoute().getEndStation();
		if(lastStation != null && lastStation.equals(currentStation)){
			return true;
		}
		
		return false;
	}
	
	public boolean isFirstStation(){
		MetroStation firstStation = train.getRoute().getStartStation();
		if(firstStation != null && firstStation.equals(currentStation)){
			return true;
		}
		
		return false;
	}
	
	private MetroStation getCurrentStation() {
	  
		synchronized (currentStationLock) {
			while (currentStation == null) {
				try {
					currentStationLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace(train.getUserLog());
					// e.printStackTrace();
				}
			}
			
			return this.currentStation;
		}
		

	}
	
	public boolean matchToCurrentStation(MetroStation queryStation){
		//int noOfTries = 30;
		synchronized (currentStationLock) {
			MetroStation currentStaion = getCurrentStation();
			while(true && !currentStaion.equals(queryStation)){
				try {
					currentStationLock.wait();
					currentStaion = getCurrentStation();
					if(currentStaion.equals(queryStation)){
						return true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace(train.getUserLog());
				}
			}
			
		}
		return false;
	}
	
	public MetroStation getLastDepartedStation() {
		  
		return this.lastDepartedStation;
	}
	

	public Date getExpectedDepartureTime(MetroStation queryStation){
		if(queryStation == null){
			return null;
		}
		
		if(currentStation == null){
			return null;
		}
		
		if(!queryStation.equals(currentStation)){
			return null;
		}
		
		return this.scheduleDepartureTime;
		
	}
	
	public Date getActualDepartureTime(MetroStation queryStation){
		
		if(queryStation == null){
			return null;
		}
		
		if(currentStation == null){
			return null;
		}
		
		if(!queryStation.equals(currentStation)){
			return null;
		}
		
		return this.actualDepartureTime;
	}
	
	
	public Date getExpectedArrivalTime(MetroStation queryStation){
		if(queryStation == null){
			return null;
		}
		
		if(destStation == null){
			return null;
		}
		
		if(!queryStation.equals(destStation)){
			return null;
		}
		
		return this.scheduleArrivalTime;
		
	}
	
	public Date getActualArrivalTime(MetroStation queryStation){
		
		if(queryStation == null){
			return null;
		}
		
		if(currentStation == null){
			return null;
		}
		
		if(!queryStation.equals(currentStation)){
			return null;
		}
		
		return this.actualArrivalTime;
	}
	
	public void setTrainArrival(MetroStation arrivalStation,Date arrivalDate,
			                    MetroStation nextStation,Date departureTime) 
	throws MetroSystemException{
		
		train.writeLog("Inside method setTrainArrival");
		if(arrivalStation == null){
			throw new MetroSystemException("Please provide the arrival train station");
		}
		
		
		if(arrivalDate == null){
			throw new MetroSystemException("Please provide the arrival time.");
		}
		

		
		synchronized (currentStationLock) {
			this.currentStation = arrivalStation;
			currentStationLock.notifyAll();
		}

		this.actualArrivalTime = arrivalDate;
		this.destStation = nextStation;
		this.scheduleDepartureTime = departureTime;
		
		arrivalStation.addTrainMonitor(this);
		if(nextStation != null){
			nextStation.addTrainMonitor(this);
		}
		train.writeLog("Exiting method setTrainArrival");
	}
	
	public void setNextStationExpectedArrivalTime(Date expectedTime) throws MetroSystemException{
		
		train.writeLog("Inside method setNextStationExpectedArrivalTime");
		if(destStation == null){
			throw new MetroSystemException("The destination station does not exist.");
		}
		
		this.scheduleArrivalTime = expectedTime;
		train.writeLog("Exiting method setNextStationExpectedArrivalTime");
	}
	
	public void setTrainDeparture(Date departureTime) throws MetroSystemException{
		
		train.writeLog("Inside method setTrainDeparture");
		if(currentStation == null){
			throw new MetroSystemException("No departure station defined.");
		}
		
		if(destStation == null){
			throw new MetroSystemException("No destination station defined.");
		}
		
		if(departureTime == null){
			throw new MetroSystemException("Please provide the departure time.");
		}
		
		/**if(departureTime.compareTo(scheduleDepartureTime) < 0){
			throw new MetroSystemException("Train cannot leave before the scheduled departure time.");
		}*/
		
		
		this.actualDepartureTime = departureTime;
		this.lastDepartedStation = this.currentStation;
		this.currentStation.removeTrainMonitor(this);
		this.currentStation = null;
		this.actualArrivalTime = null;
		train.writeLog("Exiting method setTrainDeparture");
	}
	
	public Route getTrainRoute(){
		return this.train.getRoute();
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof TrainMonitor)){
			return false;
		}
		
		TrainMonitor otherTrainMonitor = (TrainMonitor)object;
		
		return this.train.equals(otherTrainMonitor.train);
	}
	
	@Override
	public int hashCode(){
		
		return this.train.hashCode();
	}

}
