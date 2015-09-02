package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.ITrainJourneyService;
import com.metrosystem.service.exception.MetroSystemServiceException;


public class TrainJourneyServiceTest {

	private static ITrainJourneyService journeyService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			journeyService = (ITrainJourneyService) context.getBean("trainJourneyService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void scheduleJourney(){
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 2);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			Integer journeyId = journeyService.scheduleTrainJourney(2, calendar.getTime());
			assertTrue(journeyId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void scheduleDuplicateJourney(){
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 2);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			Integer journeyId = journeyService.scheduleTrainJourney(2, calendar.getTime());
			assertTrue(journeyId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void scheduleJourneyForTrainWithoutRoute(){
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 2);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			Integer journeyId = journeyService.scheduleTrainJourney(3, calendar.getTime());
			assertTrue(journeyId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void startJourney(){
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 2);
			calendar.set(Calendar.MINUTE, 30);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			journeyService.startTrainJourney(2, calendar.getTime());
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void startJourneyBeforeScheduleTime(){
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 1);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			journeyService.startTrainJourney(2, calendar.getTime());
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void endJourney(){
		
		try{
			 Calendar calendar= Calendar.getInstance();
			 calendar.set(Calendar.HOUR, 3);
			 calendar.set(Calendar.MINUTE, 0);
			 calendar.set(Calendar.SECOND, 0);
		   	 calendar.set(Calendar.MILLISECOND,0);
			journeyService.finishTrainJourney(2, calendar.getTime());
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void endJourneyBeforeStartTime(){
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, 2);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			journeyService.finishTrainJourney(2, calendar.getTime());
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
