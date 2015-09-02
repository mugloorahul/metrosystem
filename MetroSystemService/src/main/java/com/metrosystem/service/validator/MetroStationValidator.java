package com.metrosystem.service.validator;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.StationRouteDTO;

@Component("stationValidator")
public class MetroStationValidator {

	public StationRouteDTO validateStationExistsForRoute(String newStation,Collection<StationRouteDTO> stationsRoutes){
		
		for(StationRouteDTO sr: stationsRoutes){
			if(sr.getStation().getName().equals(newStation)){
				return sr;
			}
		}
			
		return null;	
	}
	
	public String validateNewStationSequenceForRoute(int newSequence,Collection<StationRouteDTO> stationsRoutes){
		
	    for(StationRouteDTO sr: stationsRoutes){
	    	if(sr.getSequence()==newSequence){
	    		return sr.getStation().getName();
	    	}		
		}
	    
	    return null;
	}
}
