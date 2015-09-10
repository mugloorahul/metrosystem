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

	public UserJourneyDTO boToDto(Integer journeyId,MetroUserDTO user,Date swipeInTime,MetroStationDTO swipeInStation){
		UserJourneyDTO journey = new UserJourneyDTO(user, swipeInTime, swipeInStation);
		journey.setJourneyId(journeyId!=null?journeyId:0);
		
		return journey;
	}
	
	public UserJourneyDTO boToDto(Integer journeyId,MetroUserDTO user,Date swipeInTime,MetroStationDTO swipeInStation,
			                     TrainJourneyDTO trainJourney,Date swipeOutTime,MetroStationDTO swipeOutStation,Date boardedTime,Date alightedTime)
	{
		
		UserJourneyDTO journey = new UserJourneyDTO(user, swipeInTime, swipeInStation);
		journey.setJourneyId(journeyId!=null?journeyId:0);
		journey.setTrainJourney(trainJourney);
		journey.setSwipeOutTime(swipeOutTime);
		journey.setSwipeOutStation(swipeOutStation);
		journey.setBoardedTime(boardedTime);
		journey.setAlightedTime(alightedTime);
		
		return journey;
	}
	
	public UserJourneyBO dtoToBo(Integer journeyId,MetroUserBO user,Date swipeInTime,MetroStationBO swipeInStation){
		UserJourneyBO journey = new UserJourneyBO(user, swipeInTime, swipeInStation);
		journey.setJourneyId(journeyId!=null?journeyId:0);
		
		return journey;
	}
	
	public UserJourneyBO dtoToBo(Integer journeyId,MetroUserBO user,Date swipeInTime,MetroStationBO swipeInStation,
                                 TrainJourneyBO trainJourney,Date swipeOutTime,MetroStationBO swipeOutStation,Date boardedTime,Date alightedTime)
	{
	
		UserJourneyBO journey = new UserJourneyBO(user, swipeInTime, swipeInStation);
		journey.setJourneyId(journeyId!=null?journeyId:0);
		journey.setTrainJourney(trainJourney);
		journey.setSwipeOutTime(swipeOutTime);
		journey.setSwipeOutStation(swipeOutStation);
		journey.setBoardedTime(boardedTime);
		journey.setAlightedTime(alightedTime);
		
		return journey;
	}

}
