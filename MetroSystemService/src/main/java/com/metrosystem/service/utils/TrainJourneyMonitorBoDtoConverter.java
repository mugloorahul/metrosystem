package com.metrosystem.service.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.dao.beans.TrainJourneyMonitorDTO;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.beans.TrainJourneyMonitorBO;

@Component("trainJourneyMonitorBoDtoConverter")
public class TrainJourneyMonitorBoDtoConverter {

	public TrainJourneyMonitorDTO boToDto(Integer monitorId,TrainJourneyDTO journey, MetroStationDTO station,Date scheduledArrivalTime,
			                              Date scheduledDepartureTime, Date actualArrivalTime,Date actualDepartureTime)
	{
		TrainJourneyMonitorDTO monitor = new TrainJourneyMonitorDTO(journey, station, scheduledArrivalTime, scheduledDepartureTime);
		monitor.setActualArrivalTime(actualArrivalTime);
		monitor.setActualDepartureTime(actualDepartureTime);
		monitor.setMonitorId(monitorId!=null?monitorId:0);
		
		return monitor;
	}
	
	public TrainJourneyMonitorBO dtoToBo(Integer monitorId,TrainJourneyBO journey, MetroStationBO station,Date scheduledArrivalTime,
                                          Date scheduledDepartureTime, Date actualArrivalTime,Date actualDepartureTime)
    {
		TrainJourneyMonitorBO monitor = new TrainJourneyMonitorBO(journey, station, scheduledArrivalTime, scheduledDepartureTime);
        monitor.setActualArrivalTime(actualArrivalTime);
        monitor.setActualDepartureTime(actualDepartureTime);
        monitor.setMonitorId(monitorId!=null?monitorId:0);

        return monitor;
    }
	
}
