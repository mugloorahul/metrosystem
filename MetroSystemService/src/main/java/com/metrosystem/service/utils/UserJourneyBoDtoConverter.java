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

	public UserJourneyBO dtoToBo(Integer journeyId, MetroUserBO user, Date swipeInTime,
			                     MetroStationBO source, MetroStationBO destination)
	{
		
		UserJourneyBO journey = new UserJourneyBO(user, swipeInTime, source, destination);
		journey.setJourneyId(journeyId!= null?journeyId:0);
		
		return journey;
	}
	
	public UserJourneyBO dtoToBo(Integer journeyId, MetroUserBO user, Date swipeInTime,
                                 MetroStationBO source, MetroStationBO destination,
                                 TrainJourneyBO trainJourney,Date actualStartTime, Date endTime,
                                 Date scheduleTime, Date swipeOutTime)
    {
		
		UserJourneyBO journey = dtoToBo(journeyId, user, swipeInTime, source, destination);
		journey.setActualStartTime(actualStartTime);
		journey.setEndTime(endTime);
		journey.setScheduledStartTime(scheduleTime);
	    journey.setSwipeOutTime(swipeOutTime);
		
		return journey;
    }
	
	public UserJourneyDTO boToDto(Integer journeyId, MetroUserDTO user, Date swipeInTime,
                                 MetroStationDTO source, MetroStationDTO destination)
    {

		UserJourneyDTO journey = new UserJourneyDTO(user, swipeInTime, source, destination);
        journey.setJourneyId(journeyId!= null?journeyId:0);

        return journey;
    }
	
	
	
	public UserJourneyDTO boToDto(Integer journeyId, MetroUserDTO user, Date swipeInTime,
                                  MetroStationDTO source, MetroStationDTO destination,
                                  TrainJourneyDTO trainJourney,Date actualStartTime, Date endTime,
                                  Date scheduleTime, Date swipeOutTime)
    {

        UserJourneyDTO journey = boToDto(journeyId, user, swipeInTime, source, destination);
        journey.setActualStartTime(actualStartTime);
		journey.setEndTime(endTime);
		journey.setScheduledStartTime(scheduleTime);
	    journey.setSwipeOutTime(swipeOutTime);

        return journey;
    }

}
