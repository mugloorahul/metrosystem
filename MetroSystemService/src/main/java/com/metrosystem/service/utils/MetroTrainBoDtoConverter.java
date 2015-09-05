package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.RouteBO;

@Component("trainBoDtoConverter")
public class MetroTrainBoDtoConverter {

	public MetroTrainDTO boToDto(int trainNumber, String name, RouteDTO route){
		
		return new MetroTrainDTO(trainNumber, name, route);
	}
	
	public MetroTrainBO dtoToBo(int trainNumber, String name, RouteBO route){
		
		return new MetroTrainBO(trainNumber, name, route);
	}
}
