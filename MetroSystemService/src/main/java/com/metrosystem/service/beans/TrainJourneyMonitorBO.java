package com.metrosystem.service.beans;

import java.util.Date;

public class TrainJourneyMonitorBO {

	private int monitorId;
	private TrainJourneyBO trainJourney;
	private MetroStationBO station;
	private String currentStationFlag = "N";
    private Date scheduledArrivalTime;
    private Date scheduledDepartureTime;
    private Date actualArrivalTime;
    private Date actualDepartureTime;
    
    /** default constructor*/
    public TrainJourneyMonitorBO(){}

	public TrainJourneyMonitorBO(int monitorId, TrainJourneyBO trainJourney,
			MetroStationBO station, String currentStationFlag,
			Date scheduledArrivalTime, Date scheduledDepartureTime,
			Date actualArrivalTime, Date actualDepartureTime) {
		super();
		this.monitorId = monitorId;
		this.trainJourney = trainJourney;
		this.station = station;
		this.currentStationFlag = currentStationFlag;
		this.scheduledArrivalTime = scheduledArrivalTime;
		this.scheduledDepartureTime = scheduledDepartureTime;
		this.actualArrivalTime = actualArrivalTime;
		this.actualDepartureTime = actualDepartureTime;
	}

	/**
	 * @return the monitorId
	 */
	public int getMonitorId() {
		return monitorId;
	}

	/**
	 * @param monitorId the monitorId to set
	 */
	public void setMonitorId(int monitorId) {
		this.monitorId = monitorId;
	}

	/**
	 * @return the trainJourney
	 */
	public TrainJourneyBO getTrainJourney() {
		return trainJourney;
	}

	/**
	 * @param trainJourney the trainJourney to set
	 */
	public void setTrainJourney(TrainJourneyBO trainJourney) {
		this.trainJourney = trainJourney;
	}

	/**
	 * @return the station
	 */
	public MetroStationBO getStation() {
		return station;
	}

	/**
	 * @param station the station to set
	 */
	public void setStation(MetroStationBO station) {
		this.station = station;
	}

	/**
	 * @return the currentStationFlag
	 */
	public String getCurrentStationFlag() {
		return currentStationFlag;
	}

	/**
	 * @param currentStationFlag the currentStationFlag to set
	 */
	public void setCurrentStationFlag(String currentStationFlag) {
		this.currentStationFlag = currentStationFlag;
	}

	/**
	 * @return the scheduledArrivalTime
	 */
	public Date getScheduledArrivalTime() {
		return scheduledArrivalTime;
	}

	/**
	 * @param scheduledArrivalTime the scheduledArrivalTime to set
	 */
	public void setScheduledArrivalTime(Date scheduledArrivalTime) {
		this.scheduledArrivalTime = scheduledArrivalTime;
	}

	/**
	 * @return the scheduledDepartureTime
	 */
	public Date getScheduledDepartureTime() {
		return scheduledDepartureTime;
	}

	/**
	 * @param scheduledDepartureTime the scheduledDepartureTime to set
	 */
	public void setScheduledDepartureTime(Date scheduledDepartureTime) {
		this.scheduledDepartureTime = scheduledDepartureTime;
	}

	/**
	 * @return the actualArrivalTime
	 */
	public Date getActualArrivalTime() {
		return actualArrivalTime;
	}

	/**
	 * @param actualArrivalTime the actualArrivalTime to set
	 */
	public void setActualArrivalTime(Date actualArrivalTime) {
		this.actualArrivalTime = actualArrivalTime;
	}

	/**
	 * @return the actualDepartureTime
	 */
	public Date getActualDepartureTime() {
		return actualDepartureTime;
	}

	/**
	 * @param actualDepartureTime the actualDepartureTime to set
	 */
	public void setActualDepartureTime(Date actualDepartureTime) {
		this.actualDepartureTime = actualDepartureTime;
	}
    
	@Override
	public boolean equals(Object obj){
		
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof TrainJourneyMonitorBO)){
			return false;
		}
		
		TrainJourneyMonitorBO otherMonitor = (TrainJourneyMonitorBO)obj;
		
		if(this.monitorId != otherMonitor.monitorId){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode(){
		return this.monitorId * 37;
	}
}
