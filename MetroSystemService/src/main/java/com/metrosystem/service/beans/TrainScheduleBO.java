package com.metrosystem.service.beans;

import java.util.List;

import com.metrosystem.dao.beans.TrainScheduleDTO;

public class TrainScheduleBO {

	private int scheduleId;
	private MetroTrainBO train;
	private List<TrainScheduleTimingBO> timings;
	
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
	public List<TrainScheduleTimingBO> getTimings() {
		return timings;
	}

	/**
	 * @param timings the timings to set
	 */
	public void setTimings(List<TrainScheduleTimingBO> timings) {
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
