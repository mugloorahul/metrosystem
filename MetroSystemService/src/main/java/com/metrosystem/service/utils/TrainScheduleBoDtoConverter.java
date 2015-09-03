package com.metrosystem.service.utils;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.TrainScheduleDTO;
import com.metrosystem.dao.beans.TrainScheduleTimingDTO;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.TrainScheduleBO;
import com.metrosystem.service.beans.TrainScheduleTimingBO;

@Component("trainScheduleBoDtoConverter")
public class TrainScheduleBoDtoConverter {

	public TrainScheduleDTO boToDto(Integer scheduleId, MetroTrainDTO train, Set<TrainScheduleTimingDTO> timings){
		
		TrainScheduleDTO schedule = new TrainScheduleDTO(train);
		schedule.setScheduleId(scheduleId!=null?scheduleId:0);
		schedule.setTimings(timings);
		
		return schedule;
	}
	
    public TrainScheduleBO dtoToBo(Integer scheduleId, MetroTrainBO train, Set<TrainScheduleTimingBO> timings){
		
    	TrainScheduleBO schedule = new TrainScheduleBO(train);
		schedule.setScheduleId(scheduleId!=null?scheduleId:0);
		schedule.setTimings(timings);
		
		return schedule;
	}
}
