package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="train_schedule")
public class TrainScheduleDTO implements Serializable{

	/**
	 * Default serial id
	 */
	private static final long serialVersionUID = 1L;

	private int scheduleId;
	private MetroTrainDTO train;
	private List<TrainScheduleTimingDTO> timings;
	
	//Default constructor
	public TrainScheduleDTO(){}
	
	public TrainScheduleDTO(MetroTrainDTO train){
		this.train = train;
	}

	/**
	 * @return the scheduleId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="schedule_id")
	public int getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the train
	 */
	@OneToOne(optional=false)
	@JoinColumn(name="train_number")
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
	 * @return the timings
	 */
	@OneToMany(mappedBy="trainSchedule")
	public List<TrainScheduleTimingDTO> getTimings() {
		return timings;
	}

	/**
	 * @param timings the timings to set
	 */
	public void setTimings(List<TrainScheduleTimingDTO> timings) {
		this.timings = timings;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof TrainScheduleDTO)){
			return false;
		}
		
		TrainScheduleDTO otherTrainSchedule = (TrainScheduleDTO)object;
		
		return train.equals(otherTrainSchedule.train);
	}
	
	@Override
	public int hashCode(){
		return train.hashCode();
	}
}
