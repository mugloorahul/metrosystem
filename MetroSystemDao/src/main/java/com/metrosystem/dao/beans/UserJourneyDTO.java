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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    private Date swipeInTime;
    private Date swipeOutTime;
    private Date boardedTime;
    private Date alightedTime;
    private MetroStationDTO swipeInStation;
    private MetroStationDTO swipeOutStation;
    
    
    public UserJourneyDTO(MetroUserDTO user,Date swipeInTime,MetroStationDTO swipeInStation) {
		this.user=user;
		this.swipeInTime=swipeInTime;
		this.swipeInStation=swipeInStation;
	}
	
	/**
	 * @return the journeyId
	 */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@Fetch(FetchMode.JOIN)
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
	 * @return the swipeInTime
	 */
	@Column(name="swipe_in_time")
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

	/**
	 * @return the boardedTime
	 */
	@Column(name="boarded_time")
	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name="alighted_time")
	@Temporal(TemporalType.TIMESTAMP)
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
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="swipe_in_station_id")
	public MetroStationDTO getSwipeInStation() {
		return swipeInStation;
	}

	/**
	 * @param swipeInStation the swipeInStation to set
	 */
	public void setSwipeInStation(MetroStationDTO swipeInStation) {
		this.swipeInStation = swipeInStation;
	}

	/**
	 * @return the swipeOutStation
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="swipe_out_station_id")
	public MetroStationDTO getSwipeOutStation() {
		return swipeOutStation;
	}

	/**
	 * @param swipeOutStation the swipeOutStation to set
	 */
	public void setSwipeOutStation(MetroStationDTO swipeOutStation) {
		this.swipeOutStation = swipeOutStation;
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
