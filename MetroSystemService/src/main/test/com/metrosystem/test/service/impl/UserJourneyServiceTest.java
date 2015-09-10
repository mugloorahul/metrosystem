package com.metrosystem.test.service.impl;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IUserJourneyService;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class UserJourneyServiceTest {

	private static IUserJourneyService userJourneyService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			userJourneyService = (IUserJourneyService) context.getBean("userJourneyService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void swipeIn(){
		
		try{
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 15);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND,0);
			Date swipeInTime = calendar.getTime();
			Integer journeyId = userJourneyService.swipeIn("mugloorahul1", "Pune", swipeInTime);
		    assertTrue(journeyId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
