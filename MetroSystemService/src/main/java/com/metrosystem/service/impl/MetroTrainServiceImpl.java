package com.metrosystem.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroTrainDao;
import com.metrosystem.dao.IRouteDao;
import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.service.IMetroTrainService;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.RouteBO;
import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.utils.MetroTrainBoDtoConverter;
import com.metrosystem.service.utils.RouteBoDtoConverter;
import com.metrosystem.service.utils.TrainJourneyBoDtoConverter;

@Component("trainService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroTrainServiceImpl implements IMetroTrainService{

	@Autowired
	@Qualifier("trainDao")
	private IMetroTrainDao trainDao;
	
	@Autowired
	@Qualifier("routeDao")
	private IRouteDao routeDao;
	
	@Autowired
	@Qualifier("trainBoDtoConverter")
	private MetroTrainBoDtoConverter trainBoDtoConverter;
	
	@Autowired
	@Qualifier("routeBoDtoConverter")
	private RouteBoDtoConverter routeBoDtoConverter;
	
	@Autowired
	@Qualifier("trainJourneyBoDtoConverter")
	private TrainJourneyBoDtoConverter trainJourneyBoDtoConverter;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createTrain(int trainNumber, String name, String routeName) 
	throws MetroSystemServiceException {
		
		try{
			RouteDTO routeDTO = routeDao.queryRouteByName(routeName);
			if(routeDTO == null){
				throw new IllegalArgumentException("No route found with given name: " + routeName);
			}
			
			//Check if train with given number exists
			MetroTrainDTO existingTrain = trainDao.queryTrainByNumber(trainNumber);
			if(existingTrain != null){
				throw new IllegalAccessException("Train with number " + trainNumber + " already exists.");
			}
			
			
			MetroTrainDTO trainDTO = trainBoDtoConverter.boToDto(trainNumber, routeName, routeDTO);
			
			return trainDao.save(trainDTO);
			
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
		
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer createTrain(int trainNumber, String name) throws MetroSystemServiceException {
		
		try{
			//Check if train with given number exists
			MetroTrainDTO existingTrain = trainDao.queryTrainByNumber(trainNumber);
			if(existingTrain != null){
				throw new IllegalAccessException("Train with number " + trainNumber + " already exists.");
			}
			
			MetroTrainDTO trainDTO = trainBoDtoConverter.boToDto(trainNumber, name, null);
			
			return trainDao.save(trainDTO);
		}
		catch (Throwable e) {
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public MetroTrainBO findTrainByNumber(int trainNumber) throws MetroSystemServiceException {
		try{
			
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				return null;
			}
			RouteDTO routeDTO = trainDTO.getRoute();
			RouteBO routeBO = null;
			if(routeDTO != null){
				routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
			}

			MetroTrainBO trainBO = trainBoDtoConverter.
					                   dtoToBo(trainDTO.getTrainNumber(), 
					                		   trainDTO.getName(), 
					                		   routeBO);
			
			return trainBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

	@Override
	public MetroTrainBO findTrainByName(String name) throws MetroSystemServiceException {
        
		try{
			
			MetroTrainDTO trainDTO = trainDao.queryTrainByName(name);
			if(trainDTO == null){
				return null;
			}
			RouteDTO routeDTO = trainDTO.getRoute();
			RouteBO routeBO = null;
			if(routeDTO != null){
				routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
			}

			MetroTrainBO trainBO = trainBoDtoConverter.
					                   dtoToBo(trainDTO.getTrainNumber(), 
					                		   trainDTO.getName(), 
					                		   routeBO);
			
			return trainBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void assignRouteToTrain(String routeName, int trainNumber) 
	throws MetroSystemServiceException {
	
		try{
			RouteDTO route = routeDao.queryRouteByName(routeName);
			if(route == null){
				throw new IllegalArgumentException("No route exists with given name: " + routeName);
			}
			MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
			if(train == null){
				throw new IllegalArgumentException("No train exists with given number: " + trainNumber);
			}
			train.setRoute(route);
			trainDao.update(train);
		}
		catch(Throwable e){
        	throw new MetroSystemServiceException(e);
        }
		
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void assignRouteToTrain(String routeName, String trainName) 
	throws MetroSystemServiceException{
		
		try{
			RouteDTO route = routeDao.queryRouteByName(routeName);
			if(route == null){
				throw new IllegalArgumentException("No route exists with given name: " + routeName);
			}
			MetroTrainDTO train = trainDao.queryTrainByName(trainName);
			if(train == null){
				throw new IllegalArgumentException("No train exists with given name: " + trainName);
			}
			train.setRoute(route);
			trainDao.update(train);
		}
		catch(Throwable e){
        	throw new MetroSystemServiceException(e);
        }
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void createMultipleTrains(List<MetroTrainBO> trains)
	throws MetroSystemServiceException {
		
		try{
			List<MetroTrainDTO> trainDTOs = new ArrayList<MetroTrainDTO>();
			for(MetroTrainBO bo : trains){
				RouteBO routeBO = bo.getRoute();
				RouteDTO routeDTO = null;
				if(routeBO != null){
					routeDTO = routeBoDtoConverter.boToDto(routeBO.getRouteId(), routeBO.getName());
				}
				trainDTOs.add(trainBoDtoConverter.boToDto(bo.getTrainNumber(),bo.getName(),routeDTO));
			}
			
			trainDao.batchInsertTrains(trainDTOs);
			
		}
		catch(Throwable e){
        	throw new MetroSystemServiceException(e);
        }
		
	}

	@Override
	public List<MetroTrainBO> getTrainsForRoute(String routeName) throws MetroSystemServiceException {
		
		try{
			RouteDTO routeDTO = routeDao.queryRouteByName(routeName);
			if(routeDTO == null){
				throw new IllegalArgumentException("No route exists with given name: " + routeName);
			}
			
			RouteBO routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
			Set<MetroTrainDTO> trainDTOs = routeDTO.getTrains();
			List<MetroTrainBO> trainBOs = new ArrayList<MetroTrainBO>();
			for(MetroTrainDTO trainDTO: trainDTOs){
				MetroTrainBO trainBO = trainBoDtoConverter.
						              dtoToBo(trainDTO.getTrainNumber(), 
						            		  trainDTO.getName(), routeBO);
				trainBOs.add(trainBO);
			}
			
			return trainBOs;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	public List<TrainJourneyBO> getAllTrainJourneys(int trainNumber) throws MetroSystemServiceException {
	
		try{
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				throw new IllegalArgumentException("No train exists with given train number: "+trainNumber);
			}
			Set<TrainJourneyDTO> journeyDTOs = trainDTO.getTrainJourneys();
			List<TrainJourneyBO> journeyBOs = new ArrayList<TrainJourneyBO>();
			if(journeyDTOs == null || journeyDTOs.size()==0){
				return journeyBOs;
			}
			RouteDTO routeDTO = trainDTO.getRoute();
			RouteBO routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
			MetroTrainBO trainBO = trainBoDtoConverter.dtoToBo(trainDTO.getTrainNumber(), trainDTO.getName(), routeBO);
			
			for(TrainJourneyDTO journeyDTO : journeyDTOs){
				TrainJourneyBO journeyBO = trainJourneyBoDtoConverter.dtoToBo(journeyDTO.getJourneyId(), journeyDTO.getScheduledStartTime(), trainBO);
				journeyBOs.add(journeyBO);
			}
			
			return journeyBOs;
		}
		catch (Throwable e) {
			throw new MetroSystemServiceException(e);
		}
	}
}
