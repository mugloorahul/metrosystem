package com.metrosystem.service.beans;

import java.util.Date;

public class UserJourneyBO {
	
	private int journeyId;
    private MetroUserBO user;
    private TrainJourneyBO trainJourney;
    private Date swipeInTime;
    private Date swipeOutTime;
    private Date boardedTime;
    private Date alightedTime;
    private MetroStationBO swipeInStation;
    private MetroStationBO swipeOutStation;
    
    
    public UserJourneyBO(MetroUserBO user,Date swipeInTime,MetroStationBO swipeInStation) {
		this.user=user;
		this.swipeInTime=swipeInTime;
		this.swipeInStation=swipeInStation;
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



	/**
	 * @return the boardedTime
	 */
	public Date getBoardedTime() {
		return boardedTime;
	}



	/**
	 * @param boardedTime the boardedTime to set
	 */
	public void setBoardedTime(Date boardedTime) {
		this.boardedTime = boardedTime;
	}



	/**
	 * @return the alightedTime
	 */
	public Date getAlightedTime() {
		return alightedTime;
	}



	/**
	 * @param alightedTime the alightedTime to set
	 */
	public void setAlightedTime(Date alightedTime) {
		this.alightedTime = alightedTime;
	}



	/**
	 * @return the swipeInStation
	 */
	public MetroStationBO getSwipeInStation() {
		return swipeInStation;
	}



	/**
	 * @param swipeInStation the swipeInStation to set
	 */
	public void setSwipeInStation(MetroStationBO swipeInStation) {
		this.swipeInStation = swipeInStation;
	}



	/**
	 * @return the swipeOutStation
	 */
	public MetroStationBO getSwipeOutStation() {
		return swipeOutStation;
	}



	/**
	 * @param swipeOutStation the swipeOutStation to set
	 */
	public void setSwipeOutStation(MetroStationBO swipeOutStation) {
		this.swipeOutStation = swipeOutStation;
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
