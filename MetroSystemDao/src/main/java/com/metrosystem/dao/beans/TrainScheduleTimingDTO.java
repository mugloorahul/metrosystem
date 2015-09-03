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
@Table(name="train_schedule_timings")
public class TrainScheduleTimingDTO implements Serializable {

	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;

	private int timingId;
	private TrainScheduleDTO trainSchedule;
	private MetroStationDTO station;
	private Date arrivalTime;
	private Date departureTime;
	
	//Default constructor
	public TrainScheduleTimingDTO(){}

	public TrainScheduleTimingDTO(TrainScheduleDTO trainSchedule,
			MetroStationDTO station, Date arrivalTime, Date departureTime) {
		this.trainSchedule = trainSchedule;
		this.station = station;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
	}

	/**
	 * @return the timingId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="timing_id")
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
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="schedule_id")
	public TrainScheduleDTO getTrainSchedule() {
		return trainSchedule;
	}

	/**
	 * @param trainSchedule the trainSchedule to set
	 */
	public void setTrainSchedule(TrainScheduleDTO trainSchedule) {
		this.trainSchedule = trainSchedule;
	}

	/**
	 * @return the station
	 */
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="station_id")
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
	 * @return the arrivalTime
	 */
	@Column(name="arrival_time")
	@Temporal(TemporalType.TIMESTAMP)
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
	@Column(name="departure_time")
	@Temporal(TemporalType.TIMESTAMP)
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
		
		if(!(object instanceof TrainScheduleTimingDTO)){
			return false;
		}
		
		TrainScheduleTimingDTO otherTiming = (TrainScheduleTimingDTO)object;
		
		return trainSchedule.equals(otherTiming.getTrainSchedule())
		    &&	this.station.equals(otherTiming.getStation());	 
	}
	
	@Override
	public int hashCode(){
		
		int hashCode = 37 *(trainSchedule.hashCode() + station.hashCode());
		
		return hashCode;
	}
}
