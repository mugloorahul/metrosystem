package com.metrosystem.service.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.TrainScheduleDTO;
import com.metrosystem.dao.beans.TrainScheduleTimingDTO;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.beans.TrainScheduleBO;
import com.metrosystem.service.beans.TrainScheduleTimingBO;

@Component("trainScheduleTimingBoDtoConverter")
public class TrainScheduleTimingBoDtoConverter {

	public TrainScheduleTimingDTO boToDto(Integer timingId,TrainScheduleDTO trainSchedule,
			MetroStationDTO station, Date arrivalTime, Date departureTime){
		
		TrainScheduleTimingDTO dto = new TrainScheduleTimingDTO(trainSchedule, station, 
				                                arrivalTime, departureTime);
		dto.setTimingId(timingId!=null?timingId:0);
		
		return dto;
	}
	
	public TrainScheduleTimingBO dtoToBo(Integer timingId,TrainScheduleBO trainSchedule,
			MetroStationBO station, Date arrivalTime, Date departureTime){
		
		TrainScheduleTimingBO bo = new TrainScheduleTimingBO(trainSchedule, station, 
				                                arrivalTime, departureTime);
		bo.setTimingId(timingId!=null?timingId:0);
		
		return bo;
	}
}
