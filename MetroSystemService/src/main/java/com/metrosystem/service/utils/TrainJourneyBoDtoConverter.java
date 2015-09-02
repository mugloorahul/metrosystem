package com.metrosystem.service.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.TrainJourneyBO;

@Component("trainJourneyBoDtoConverter")
public class TrainJourneyBoDtoConverter {

	public TrainJourneyDTO boToDto(Integer id,Date scheduleStartTime,MetroTrainDTO train,Date actualStartTime,Date actualEndTime){
		
		TrainJourneyDTO journey = new TrainJourneyDTO(train, scheduleStartTime);
		journey.setJourneyId(id!= null?id:0);
		journey.setActualStartTime(actualStartTime);
		journey.setActualEndTime(actualEndTime);
		
		return journey;
	}
	
    public TrainJourneyBO dtoToBo(Integer id,Date scheduleStartTime,MetroTrainBO train,Date actualStartTime,Date actualEndTime){
		
    	TrainJourneyBO journey = new TrainJourneyBO(train, scheduleStartTime);
		journey.setJourneyId(id!= null?id:0);
		journey.setActualStartTime(actualStartTime);
		journey.setActualEndTime(actualEndTime);
		
		return journey;
	}
}
