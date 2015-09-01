package com.metrosystem.service.utils;

import java.util.Date;

@Component("trainJourneyBoDtoConverter")
public class TrainJourneyBoDtoConverter {

	public TrainJourneyDTO boToDto(Integer id,Date scheduleStartTime,MetroTrainDTO train){
		
		TrainJourneyDTO journey = new TrainJourneyDTO(train, scheduleStartTime);
		journey.setJourneyId(id!= null?id:0);
		
		return journey;
	}
	
    public TrainJourneyBO dtoToBo(Integer id,Date scheduleStartTime,MetroTrainBO train){
		
    	TrainJourneyBO journey = new TrainJourneyBO(train, scheduleStartTime);
		journey.setJourneyId(id!= null?id:0);
		
		return journey;
	}
}
