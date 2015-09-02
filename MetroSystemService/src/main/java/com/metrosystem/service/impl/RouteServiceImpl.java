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
import com.metrosystem.service.IRouteService;
import com.metrosystem.service.beans.RouteBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.exception.ServiceValidationException;
import com.metrosystem.service.utils.RouteBoDtoConverter;

@Component("routeService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class RouteServiceImpl implements IRouteService {

	@Autowired
	@Qualifier("routeBoDtoConverter")
	private RouteBoDtoConverter routeBoDtoConverter;
	
	@Autowired
	@Qualifier("routeDao")
	private IRouteDao routeDao;
	
	@Autowired
	@Qualifier("stationDao")
	private IMetroStationDao stationDao;
	
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createRoute(String routeName) throws MetroSystemServiceException {
	
		try{
			//Check if route with this name already exists
			RouteDTO existingRoute = routeDao.queryRouteByName(routeName);
			if(existingRoute != null){
				throw new ServiceValidationException("Route with name " + routeName + " already exists.");
			}
			
			return (Integer)routeDao.save(routeBoDtoConverter.boToDto(null, routeName));
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public RouteBO findRouteByName(String routeName) throws MetroSystemServiceException {
		
		try{
        	RouteDTO routeDTO = routeDao.queryRouteByName(routeName);
        	
        	if(routeDTO == null){
        		return null;
        	}
        	
        	return routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
        	
        }
        catch(Throwable e){
        	throw new MetroSystemServiceException(e);
        }
	}

	@Override
	public List<RouteBO> getRoutesForStation(String stationName) throws MetroSystemServiceException {
		
		try{
			MetroStationDTO stationDTO = stationDao.queryStationByName(stationName);
			if(stationDTO == null){
				throw new ServiceValidationException("No station exists with given name: " + stationName);
			}
			
			Set<StationRouteDTO> stationsRoutes = stationDTO.getStationRoutes();
			List<RouteBO> routes = new ArrayList<RouteBO>();
			
			for(StationRouteDTO srDTO: stationsRoutes){
				RouteDTO routeDTO = srDTO.getRoute();
				RouteBO routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
				routes.add(routeBO);
			}
			
			return routes;
		}
		catch(Throwable e){
        	throw new MetroSystemServiceException(e);
        }
	}


}
  
