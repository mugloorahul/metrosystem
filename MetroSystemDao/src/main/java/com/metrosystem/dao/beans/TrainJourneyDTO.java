package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="train_journey")
public class TrainJourneyDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int journeyId;
	private MetroTrainDTO train;
	private Date scheduledStartTime;
	private Date actualStartTime;
	private Date actualEndTime;
	private Set<UserJourneyDTO> travellers;
	private List<TrainJourneyMonitorDTO> monitors;
	
	/**
	 * Default constructor
	 */
	public TrainJourneyDTO(){
		
	}
	
	public TrainJourneyDTO(MetroTrainDTO train, Date scheduledStartTime){
		this.train=train;
		this.scheduledStartTime=scheduledStartTime;
	}
	
	/**
	 * @return the journeyId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="journey_id")
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="train_number",nullable=false)
	public MetroTrainDTO getTrain() {
		return train;
	}
	/**
	 * @param train the train to set
	 */
	public void setTrain(MetroTrainDTO train) {
		this.train = train;
	}
	/**
	 * @return the scheduledStartTime
	 */
	@Column(name="scheduled_start_time",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name="actual_start_time")
	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name="end_time")
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
	@OneToMany(mappedBy="trainJourney",cascade=CascadeType.ALL)
	public Set<UserJourneyDTO> getTravellers() {
		return travellers;
	}

	/**
	 * @param travellers the travellers to set
	 */
	public void setTravellers(Set<UserJourneyDTO> travellers) {
		this.travellers = travellers;
	}
	
	/**
	 * @return the monitors
	 */
	@OneToMany(mappedBy="trainJourney",fetch=FetchType.LAZY)
	@OrderBy("scheduledArrivalTime")
	public List<TrainJourneyMonitorDTO> getMonitors() {
		return monitors;
	}

	/**
	 * @param monitors the monitors to set
	 */
	public void setMonitors(List<TrainJourneyMonitorDTO> monitors) {
		this.monitors = monitors;
	}
	
	public void addTraveller(UserJourneyDTO traveller){
		if(this.travellers == null){
			travellers = new HashSet<UserJourneyDTO>();
		}
		
		travellers.add(traveller);
		traveller.setTrainJourney(this);
	}

	@Override
	public boolean equals(Object obj){
		
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof TrainJourneyDTO)){
			return false;
		}
		
		TrainJourneyDTO otherJourney = (TrainJourneyDTO)obj;
		
		if(this.journeyId != otherJourney.journeyId){
			return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode(){
		return this.journeyId * 37;
	}

	public void addMonitors(TrainJourneyMonitorDTO monitor){
		if(this.monitors == null){
			monitors = new ArrayList<TrainJourneyMonitorDTO>();
		}
		
		monitors.add(monitor);
	}
}
