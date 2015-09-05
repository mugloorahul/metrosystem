package com.metrosystem.service.beans;

import java.util.Date;

public class TrainScheduleTimingBO {


	private int timingId;
	private TrainScheduleBO trainSchedule;
	private MetroStationBO station;
	private Date arrivalTime;
	private Date departureTime;
	
	//Default constructor
	public TrainScheduleTimingBO(){}

	public TrainScheduleTimingBO(TrainScheduleBO trainSchedule,
			                     MetroStationBO station, Date arrivalTime, Date departureTime) 
	{
		this.trainSchedule = trainSchedule;
		this.station = station;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
	}

	/**
	 * @return the timingId
	 */
	public int getTimingId() {
		return timingId;
	}

	/**
	 * @param timingId the timingId to set
	 */
	public void setTimingId(int timingId) {
		this.timingId = timingId;
	}

	/**
	 * @return the trainSchedule
	 */
	public TrainScheduleBO getTrainSchedule() {
		return trainSchedule;
	}

	/**
	 * @param trainSchedule the trainSchedule to set
	 */
	public void setTrainSchedule(TrainScheduleBO trainSchedule) {
		this.trainSchedule = trainSchedule;
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
	 * @return the arrivalTime
	 */
	public Date getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the departureTime
	 */
	public Date getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime the departureTime to set
	 */
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	
	@Override
	public boolean equals(Object object){
		
		if(object == null){
			return false;
		}
		
		if(!(object instanceof TrainScheduleTimingBO)){
			return false;
		}
		
		TrainScheduleTimingBO otherTiming = (TrainScheduleTimingBO)object;
		
		return trainSchedule.equals(otherTiming.getTrainSchedule())
		    &&	this.station.equals(otherTiming.getStation());	 
	}
	
	@Override
	public int hashCode(){
		
		int hashCode = 37 *(trainSchedule.hashCode() + station.hashCode());
		
		return hashCode;
	}
}
