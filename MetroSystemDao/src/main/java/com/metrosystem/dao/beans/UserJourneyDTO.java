package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="user_journey")
public class UserJourneyDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int journeyId;
	private MetroUserDTO user;
	private TrainJourneyDTO trainJourney;
	private Date scheduledStartTime;
	private Date actualStartTime;
	private Date endTime;
	private MetroStationDTO sourceStation;
	private MetroStationDTO destinationStation;
	private Date swipeInTime;
	private Date swipeOutTime;
	
	/**
	 * Default constructor
	 */
	public UserJourneyDTO(){
		
	}
	
	public UserJourneyDTO(MetroUserDTO user,Date swipeInTime,
			MetroStationDTO source,MetroStationDTO destination)
	{
	  this.user=user;
	  this.swipeInTime=swipeInTime;
	  this.sourceStation=source;
	  this.destinationStation=destination;
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
	 * @return the user
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	public MetroUserDTO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(MetroUserDTO user) {
		this.user = user;
	}
	/**
	 * @return the trainJourney
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="train_journey_id")
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
	 * @return the scheduledStartTime
	 */
	@Column(name="scheduled_start_time",nullable=true)
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
	 * @return the endTime
	 */
	@Column(name="end_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the sourceStation
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="source_station_id",nullable=false)
	public MetroStationDTO getSourceStation() {
		return sourceStation;
	}
	/**
	 * @param sourceStation the sourceStation to set
	 */
	public void setSourceStation(MetroStationDTO sourceStation) {
		this.sourceStation = sourceStation;
	}
	/**
	 * @return the destinationStation
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dest_station_id",nullable=false)
	public MetroStationDTO getDestinationStation() {
		return destinationStation;
	}
	/**
	 * @param destinationStation the destinationStation to set
	 */
	public void setDestinationStation(MetroStationDTO destinationStation) {
		this.destinationStation = destinationStation;
	}
	/**
	 * @return the swipeInTime
	 */
	@Column(name="swipe_in_time",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSwipeInTime() {
		return swipeInTime;
	}
	/**
	 * @param swipeInTime the swipeInTime to set
	 */
	public void setSwipeInTime(Date swipeInTime) {
		this.swipeInTime = swipeInTime;
	}
	/**
	 * @return the swipeOutTime
	 */
	@Column(name="swipe_out_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getSwipeOutTime() {
		return swipeOutTime;
	}
	/**
	 * @param swipeOutTime the swipeOutTime to set
	 */
	public void setSwipeOutTime(Date swipeOutTime) {
		this.swipeOutTime = swipeOutTime;
	}
	
	@Override
	public boolean equals(Object obj){
		
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof UserJourneyDTO)){
			return false;
		}
		
		UserJourneyDTO otherJourney = (UserJourneyDTO)obj;
		
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
