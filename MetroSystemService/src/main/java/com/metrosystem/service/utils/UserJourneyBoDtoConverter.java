package com.metrosystem.service.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.dao.beans.UserJourneyDTO;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.beans.UserJourneyBO;

@Component("userJourneyBoDtoConverter")
public class UserJourneyBoDtoConverter {

	public UserJourneyDTO boToDto(Integer journeyId,MetroStationDTO source,MetroStationDTO destination,
			                      MetroUserDTO user,Date scheduleStartTime, TrainJourneyDTO trainJourney){
		
		UserJourneyDTO journey = new UserJourneyDTO(user, scheduleStartTime, source, destination);
		journey.setTrainJourney(trainJourney);
		journey.setJourneyId(journeyId!=null?journeyId:0);
		
		return journey;
	}
	
	public UserJourneyBO dtoToBO(Integer journeyId,MetroStationBO source,MetroStationBO destination,
			                     MetroUserBO user, Date scheduleStartTime, TrainJourneyBO trainJourney){
		
		UserJourneyBO journey = new UserJourneyBO(user, scheduleStartTime, source, destination);
		journey.setTrainJourney(trainJourney);
		journey.setJourneyId(journeyId!=null?journeyId:0);
		
		return journey;
	}
	
}
