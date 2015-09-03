package com.metrosystem.service.beans;

import java.util.Date;

import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.MetroUserDTO;

public class UserJourneyBO {

	private int journeyId;
	private MetroUserBO user;
	private TrainJourneyBO trainJourney;
	private Date scheduledStartTime;
	private Date actualStartTime;
	private Date endTime;
	private MetroStationBO sourceStation;
	private MetroStationBO destinationStation;
	private Date swipeInTime;
	private Date swipeOutTime;
	
	/**
	 * Default constructor
	 */
	public UserJourneyBO(){
		
	}

	public UserJourneyBO(int journeyId, MetroUserBO user,
			TrainJourneyBO trainJourney, Date scheduledStartTime,
			Date actualStartTime, Date endTime, MetroStationBO sourceStation,
			MetroStationBO destinationStation, Date swipeInTime,
			Date swipeOutTime) {
		super();
		this.journeyId = journeyId;
		this.user = user;
		this.trainJourney = trainJourney;
		this.scheduledStartTime = scheduledStartTime;
		this.actualStartTime = actualStartTime;
		this.endTime = endTime;
		this.sourceStation = sourceStation;
		this.destinationStation = destinationStation;
		this.swipeInTime = swipeInTime;
		this.swipeOutTime = swipeOutTime;
	}
	
	public UserJourneyBO(MetroUserBO user,Date swipeInTime,
			MetroStationBO source,MetroStationBO destination)
	{
	  this.user=user;
	  this.swipeInTime=swipeInTime;
	  this.sourceStation=source;
	  this.destinationStation=destination;
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
	 * @return the user
	 */
	public MetroUserBO getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(MetroUserBO user) {
		this.user = user;
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
	 * @return the endTime
	 */
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
	public MetroStationBO getSourceStation() {
		return sourceStation;
	}

	/**
	 * @param sourceStation the sourceStation to set
	 */
	public void setSourceStation(MetroStationBO sourceStation) {
		this.sourceStation = sourceStation;
	}

	/**
	 * @return the destinationStation
	 */
	public MetroStationBO getDestinationStation() {
		return destinationStation;
	}

	/**
	 * @param destinationStation the destinationStation to set
	 */
	public void setDestinationStation(MetroStationBO destinationStation) {
		this.destinationStation = destinationStation;
	}

	/**
	 * @return the swipeInTime
	 */
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
		
		if(!(obj instanceof UserJourneyBO)){
			return false;
		}
		
		UserJourneyBO otherJourney = (UserJourneyBO)obj;
		
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
