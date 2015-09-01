package com.metrosystem.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroTrainDao;
import com.metrosystem.dao.ITrainJourneyDao;
import com.metrosystem.dao.beans.MetroTrainDTO;
import com.metrosystem.dao.beans.RouteDTO;
import com.metrosystem.dao.beans.TrainJourneyDTO;
import com.metrosystem.service.ITrainJourneyService;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.RouteBO;
import com.metrosystem.service.beans.TrainJourneyBO;
import com.metrosystem.service.exception.MetroSystemServiceException;
import com.metrosystem.service.utils.MetroTrainBoDtoConverter;
import com.metrosystem.service.utils.RouteBoDtoConverter;
import com.metrosystem.service.utils.TrainJourneyBoDtoConverter;

@Component("trainJourneyService")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class TrainJourneyServiceImpl implements ITrainJourneyService {

	@Autowired
	@Qualifier("trainDao")
	private IMetroTrainDao trainDao;
	
	@Autowired
	@Qualifier("trainJourneyDao")
	private ITrainJourneyDao trainJourneyDao;
	
	@Autowired
	@Qualifier("trainBoDtoConverter")
	private MetroTrainBoDtoConverter trainBoDtoConverter;
	
	@Autowired
	@Qualifier("trainJourneyBoDtoConverter")
	private TrainJourneyBoDtoConverter trainJourneyBoDtoConverter;
	
	@Autowired
	@Qualifier("routeBoDtoConverter")
	private RouteBoDtoConverter routeBoDtoConverter;
	
	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public Integer scheduleTrainJourney(int trainNumber, Date scheduleStartTime) throws MetroSystemServiceException{
		
		try{
			MetroTrainDTO train = trainDao.queryTrainByNumber(trainNumber);
			if(train == null){
				throw new IllegalArgumentException("No train exists with given train number: " + trainNumber);
			}
			
			//Check if journey has already been scheduled at same time
			TrainJourneyDTO existingJourney = trainJourneyDao.queryJourneyByScheduleTime(trainNumber, scheduleStartTime);
			if(existingJourney != null){
				throw new IllegalArgumentException("Train journey for train " + trainNumber + " is already sceduled at time " + scheduleStartTime);		
			}
			
			TrainJourneyDTO journeyDTO = trainJourneyBoDtoConverter.
					                          boToDto(null, scheduleStartTime, train);
			
			return trainJourneyDao.save(journeyDTO);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}

	}

	@Override
	public TrainJourneyBO findTrainJourneyByScheduleTime(int trainNumber,Date scheduleTime) throws MetroSystemServiceException {
		
		try{
			MetroTrainDTO trainDTO = trainDao.queryTrainByNumber(trainNumber);
			if(trainDTO == null){
				throw new IllegalArgumentException("No train exists with given train number: " + trainNumber);
			}
			RouteDTO routeDTO = trainDTO.getRoute();
			if(routeDTO == null){
				throw new IllegalArgumentException("No route defined for train with number: " + trainNumber);
			}
			
			
			TrainJourneyDTO journeyDTO = trainJourneyDao.queryJourneyByScheduleTime(trainNumber, scheduleTime);
			if(journeyDTO == null){
				return null;
			}
			
			RouteBO routeBO = routeBoDtoConverter.dtoToBo(routeDTO.getRouteId(), routeDTO.getName());
			MetroTrainBO trainBO = trainBoDtoConverter.
					                   dtoToBo(trainDTO.getTrainNumber(), 
					                		   trainDTO.getName(), routeBO);
			
			TrainJourneyBO journeyBO = trainJourneyBoDtoConverter.
					                        dtoToBo(journeyDTO.getJourneyId(), 
					                        		journeyDTO.getScheduledStartTime(), trainBO);
			
			return journeyBO;
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void startJourney(int trainNumber,Date journeyStartTime) throws MetroSystemServiceException {
		
		try{
			TrainJourneyDTO journey = trainJourneyDao.queryLatestTrainJourney(trainNumber);
			if(journey == null){
				throw new IllegalArgumentException("Invalid journey. No journey for train: " + trainNumber);
			}
			
			journey.setActualStartTime(journeyStartTime);
			trainJourneyDao.save(journey);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

	@Override
	@Transactional(readOnly=false,rollbackFor={Exception.class})
	public void endJourney(int trainNumber,Date journeyEndTime) throws MetroSystemServiceException {
		
		try{
			TrainJourneyDTO journey = trainJourneyDao.queryLatestTrainJourney(trainNumber);
			if(journey == null){
				throw new IllegalArgumentException("Invalid journey. No journey for train: " + trainNumber);
			}
			
			journey.setActualEndTime(journeyEndTime);
			trainJourneyDao.save(journey);
		}
		catch(Throwable e){
			throw new MetroSystemServiceException(e);
		}
		
	}

}
