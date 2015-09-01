package com.metrosystem.service.beans;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class TrainJourneyBO {

	private int journeyId;
	private MetroTrainBO train;
	private Date scheduledStartTime;
	private Date actualStartTime;
	private Date actualEndTime;
	private Set<UserJourneyBO> travellers;
	private List<TrainJourneyMonitorBO> monitors;
	
	/**
	 * Default constructor
	 */
	public TrainJourneyBO(){
		
	}

	public TrainJourneyBO(int journeyId, MetroTrainBO train,
			Date scheduledStartTime, Date actualStartTime, Date actualEndTime,
			Set<UserJourneyBO> travellers, List<TrainJourneyMonitorBO> monitors) {
		super();
		this.journeyId = journeyId;
		this.train = train;
		this.scheduledStartTime = scheduledStartTime;
		this.actualStartTime = actualStartTime;
		this.actualEndTime = actualEndTime;
		this.travellers = travellers;
		this.monitors = monitors;
	}

	public TrainJourneyBO(MetroTrainBO train, Date scheduleStartTime){
		this.train = train;
		this.scheduledStartTime = scheduleStartTime;
	}
	
	/**
	 * @return the journeyId
	 */
	public int getJourneyId() {
		return journeyId;
	}

	/**
	 * @param journeyId the journeyId to set
	 */
	public void setJourneyId(int journeyId) {
		this.journeyId = journeyId;
	}

	/**
	 * @return the train
	 */
	public MetroTrainBO getTrain() {
		return train;
	}

	/**
	 * @param train the train to set
	 */
	public void setTrain(MetroTrainBO train) {
		this.train = train;
	}

	/**
	 * @return the scheduledStartTime
	 */
	public Date getScheduledStartTime() {
		return scheduledStartTime;
	}

	/**
	 * @param scheduledStartTime the scheduledStartTime to set
	 */
	public void setScheduledStartTime(Date scheduledStartTime) {
		this.scheduledStartTime = scheduledStartTime;
	}

	/**
	 * @return the actualStartTime
	 */
	public Date getActualStartTime() {
		return actualStartTime;
	}

	/**
	 * @param actualStartTime the actualStartTime to set
	 */
	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	/**
	 * @return the actualEndTime
	 */
	public Date getActualEndTime() {
		return actualEndTime;
	}

	/**
	 * @param actualEndTime the actualEndTime to set
	 */
	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	/**
	 * @return the travellers
	 */
	public Set<UserJourneyBO> getTravellers() {
		return travellers;
	}

	/**
	 * @param travellers the travellers to set
	 */
	public void setTravellers(Set<UserJourneyBO> travellers) {
		this.travellers = travellers;
	}

	/**
	 * @return the monitors
	 */
	public List<TrainJourneyMonitorBO> getMonitors() {
		return monitors;
	}

	/**
	 * @param monitors the monitors to set
	 */
	public void setMonitors(List<TrainJourneyMonitorBO> monitors) {
		this.monitors = monitors;
	}
	
	@Override
	public boolean equals(Object obj){
		
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof TrainJourneyBO)){
			return false;
		}
		
		TrainJourneyBO otherJourney = (TrainJourneyBO)obj;
		
		if(this.journeyId != otherJourney.journeyId){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode(){
		return this.journeyId * 37;
	}
}
