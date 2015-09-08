package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Check;

@Entity
@Table(name="train_journey_monitor")
@Check(constraints="current_station_flag IN ('Y','N')")
public class TrainJourneyMonitorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int monitorId;
	private TrainJourneyDTO trainJourney;
	private MetroStationDTO station;
	private String currentStationFlag = "N";
    private Date scheduledArrivalTime;
    private Date scheduledDepartureTime;
    private Date actualArrivalTime;
    private Date actualDepartureTime;
    
    /** default constructor*/
    public TrainJourneyMonitorDTO(){}
    
    public TrainJourneyMonitorDTO(TrainJourneyDTO trainJourney, MetroStationDTO station, Date scheduledArrivalTime, Date scheduledDepartureTime){
    	this.trainJourney= trainJourney;
    	this.station=station;
    	this.scheduledArrivalTime=scheduledArrivalTime;
    	this.scheduledDepartureTime=scheduledDepartureTime;
    }
    
    
	/**
	 * @return the monitorId
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="monitor_id")
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
	@ManyToOne
	@JoinColumn(name="train_journey_id",nullable=false)
	public TrainJourneyDTO getTrainJourney() {
		return trainJourney;
	}
	/**
	 * @param trainJourney the trainJourney to set
	 */
	public void setTrainJourney(TrainJourneyDTO trainJourney) {
		this.trainJourney = trainJourney;
	}
	
	/**
	 * @return the station
	 */
	@ManyToOne
	@JoinColumn(name="station_id",nullable=false)
	public MetroStationDTO getStation() {
		return station;
	}
	/**
	 * @param station the station to set
	 */
	public void setStation(MetroStationDTO station) {
		this.station = station;
	}
	/**
	 * @return the currentStationFlag
	 */
	@Column(name="current_station_flag",nullable=false)
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
	@Column(name="scheduled_arrival_time")
	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name="scheduled_departure_time")
	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name="actual_arrival_time")
	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name="actual_departure_time")
	@Temporal(TemporalType.TIMESTAMP)
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
		
		if(!(obj instanceof TrainJourneyMonitorDTO)){
			return false;
		}
		
		TrainJourneyMonitorDTO otherMonitor = (TrainJourneyMonitorDTO)obj;
		
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
