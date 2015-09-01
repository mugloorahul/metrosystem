package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroStationDao;
import com.metrosystem.dao.IRouteDao;
import com.metrosystem.dao.beans.MetroStationDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.beans.StationRouteDTO;
import com.metrosystem.service.IMetroStationService;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.utils.MetroStationBoDtoConverter;

@Component("stationService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroStationServiceImpl implements IMetroStationService{

	@Autowired
	@Qualifier("routeDao")
	private IRouteDao routeDao;
	
	@Autowired
	@Qualifier("stationDao")
	private IMetroStationDao stationDao;
	
	@Autowired
	@Qualifier("stationBoDtoConverter")
	private MetroStationBoDtoConverter stationBoDtoConverter;
	
	
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void addStationToRoute(String stationName,String routeName,int sequence) 
	throws MetroSystemServiceException 
	{
		try{
			
			//Get the station
			MetroStationDTO stationDTO = stationDao.queryStationByName(stationName);
			if(stationDTO == null){
				throw new IllegalArgumentException("No station found with given name: " + stationName);
			}
			
			//Get the route
			RouteDTO routeDTO = routeDao.queryRouteByName(routeName);
			if(routeDTO == null){
        		throw new IllegalArgumentException("No route found with given name: " + routeName);
        	}
			
			//Check if station with given sequence already exists
			Set<StationRouteDTO> stationsRoutes = routeDTO.getStationRoutes();
			if(stationsRoutes != null && stationsRoutes.size() > 0){
				for(StationRouteDTO sr: stationsRoutes){
					if(sr.getSequence() == sequence){
						throw new IllegalArgumentException("Station " + sr.getStation().getName() + "already exists for route " + routeName + " at sequence " + sequence);
					}
				}
			}
			
			StationRouteDTO stationRoute= new StationRouteDTO(sequence, stationDTO, routeDTO);
			routeDTO.addStation(stationRoute);
			stationDao.update(stationDTO);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}


    

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createStation(String name, String latitude, String longitude)throws MetroSystemServiceException 
	{
	   try{
		   
		   //Check if station already exists
		   MetroStationDTO existingStation = stationDao.queryStationByName(name);
		   if(existingStation != null){
			   throw new IllegalArgumentException("Station with name " + name  + " already exists.");
		   }
		   
		   MetroStationDTO stationDTO = stationBoDtoConverter.
				                        boToDto(null, name, latitude, longitude);
		   return (Integer)stationDao.save(stationDTO); 
	   }
	   catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}




	@Override
	public MetroStationBO findStationByName(String name) throws MetroSystemServiceException {
        try{
        	MetroStationDTO stationDTO = stationDao.queryStationByName(name);
        	
        	if(stationDTO == null){
        		return null;
        	}
        	
        	return stationBoDtoConverter.
        			dtoToBo(stationDTO.getStationId(), 
        					stationDTO.getName(), 
        					stationDTO.getLocation().getLatitude(), 
        					stationDTO.getLocation().getLongitude());
        	
        }
        catch(Throwable e){
        	throw new MetroSystemServiceException(e);
        }
		
	}




	@Override
	public List<MetroStationBO> getStationsForRoute(String routeName) throws MetroSystemServiceException {
		
		try{
			RouteDTO routeDTO = routeDao.queryRouteByName(routeName);
			if(routeDTO == null){
				throw new IllegalArgumentException("No route exists with given name: " + routeName);
			}
			
			Set<StationRouteDTO> stationsRoutes = routeDTO.getStationRoutes();
			List<MetroStationBO> stations = new ArrayList<MetroStationBO>();
			
			for(StationRouteDTO srDTO: stationsRoutes){
				MetroStationDTO stationDTO = srDTO.getStation();
				MetroStationBO stationBO = stationBoDtoConverter.
						dtoToBo(stationDTO.getStationId(), stationDTO.getName(), 
								stationDTO.getLocation().getLatitude(), 
								stationDTO.getLocation().getLongitude());
				
				stations.add(stationBO);
			}
			
			return stations;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}

	}

}
