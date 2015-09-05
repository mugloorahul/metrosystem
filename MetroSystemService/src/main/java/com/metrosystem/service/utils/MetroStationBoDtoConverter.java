package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.service.beans.MetroStationBO;

@Component("stationBoDtoConverter")
public class MetroStationBoDtoConverter {

	
	public MetroStationDTO boToDto(Integer stationId,String name,String latitude, String longitude)
	{
		MetroStationDTO station = new MetroStationDTO(name,latitude,longitude);
		station.setStationId(stationId==null ? 0 : stationId);
		
		return station;
	}
	
	public MetroStationBO dtoToBo(Integer stationId,String name,String latitude, String longitude)
	{
		
		MetroStationBO station =  new MetroStationBO(name, latitude, longitude);
		station.setStationId(stationId==null ? 0 : stationId);
		
		return station;
	}	

}
