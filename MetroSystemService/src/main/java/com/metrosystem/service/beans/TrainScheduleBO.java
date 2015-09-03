package com.metrosystem.service.beans;

import java.util.Set;

import com.metrosystem.dao.beans.TrainScheduleDTO;
import com.metrosystem.dao.beans.TrainScheduleTimingDTO;

public class TrainScheduleBO {

	private int scheduleId;
	private MetroTrainBO train;
	private Set<TrainScheduleTimingDTO> timings;
	
	//Default constructor
	public TrainScheduleBO(){}
	
	public TrainScheduleBO(MetroTrainBO train){
		this.train = train;
	}

	/**
	 * @return the scheduleId
	 */
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
	 * @return the timings
	 */
	public Set<TrainScheduleTimingBO> getTimings() {
		return timings;
	}

	/**
	 * @param timings the timings to set
	 */
	public void setTimings(Set<TrainScheduleTimingBO> timings) {
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
		
		TrainScheduleBO otherTrainSchedule = (TrainScheduleBO)object;
		
		return train.equals(otherTrainSchedule.train);
	}
	
	@Override
	public int hashCode(){
		return train.hashCode();
	}
}
