package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IMetroStationService;
import com.metrosystem.service.ITrainScheduleService;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.beans.TrainScheduleBO;
import com.metrosystem.service.beans.TrainScheduleTimingBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class TrainScheduleServiceTest {

	private static ITrainScheduleService scheduleService; 
	private static IMetroStationService stationService;
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	private static Logger logger = Logger.getLogger(TrainJourneyServiceTest.class);
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			scheduleService = (ITrainScheduleService) context.getBean("trainScheduleService");
			stationService = (IMetroStationService)context.getBean("stationService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void getTrainSchedule(){
		
		try{
		    TrainScheduleBO schedule = scheduleService.getTrainSchedule(2);
		    assertTrue(schedule != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createOrUpdateTrainSchedule(){
		
		try{
			logger.debug("********Rahul Start time***********:" + new Date());
			List<MetroStationBO> stations = stationService.getStationsForRoute("Jammu To Pune");
			logger.debug("********Rahul End time***********:" + new Date());
			List<TrainScheduleTimingBO> timings = new ArrayList<TrainScheduleTimingBO>();
			String arrivalTimes[] = {"","01-01-1970 22:03:00","01-01-1970 22:20:00","01-01-1970 22:36:00",
					                 "01-01-1970 23:05:00" ,"01-01-1970 23:40:00","02-01-1970 00:27:00",
					                 "02-01-1970 00:46:00","02-01-1970 01:40:00","02-01-1970 02:03:00",
					                 "02-01-1970 03:00:00","02-01-1970 04:02:00","02-01-1970 04:20:00",
					                 "02-01-1970 04:49:00","02-01-1970 05:12:00","02-01-1970 05:40:00",
					                 "02-01-1970 06:09:00","02-01-1970 06:34:00","02-01-1970 06:54:00",
					                 "02-01-1970 07:10:00","02-01-1970 07:28:00","02-01-1970 07:47:00",
					                 "02-01-1970 08:02:00","02-01-1970 08:16:00","02-01-1970 08:33:00",
					                 "02-01-1970 08:53:00","02-01-1970 09:28:00","02-01-1970 09:55:00",
					                 "02-01-1970 10:43:00","02-01-1970 12:40:00","02-01-1970 13:24:00",
					                 "02-01-1970 13:50:00","02-01-1970 14:30:00","02-01-1970 14:55:00",
					                 "02-01-1970 15:28:00","02-01-1970 16:06:00","02-01-1970 16:34:00",
					                 "02-01-1970 17:13:00","02-01-1970 17:46:00","02-01-1970 18:30:00",
					                 "02-01-1970 19:08:00","02-01-1970 20:05:00","02-01-1970 20:42:00",
					                 "02-01-1970 21:12:00","02-01-1970 22:15:00","02-01-1970 22:30:00",
					                 "02-01-1970 23:30:00","03-01-1970 00:15:00","03-01-1970 00:44:00",
					                 "03-01-1970 01:17:00","03-01-1970 02:02:00","03-01-1970 03:20:00",
					                 "03-01-1970 04:18:00","03-01-1970 05:05:00","03-01-1970 05:38:00",
					                 "03-01-1970 06:08:00","03-01-1970 06:38:00","03-01-1970 07:08:00",
					                 "03-01-1970 07:40:00","03-01-1970 09:13:00","03-01-1970 09:58:00",
					                 "03-01-1970 11:02:00","03-01-1970 12:55:00","03-01-1970 14:29:00",
					                 "03-01-1970 15:10:00"};
			
			
			String depatureTimes[] = {"01-01-1970 21:45:00","01-01-1970 22:05:00","01-01-1970 22:22:00",
					                  "01-01-1970 22:38:00","01-01-1970 23:07:00","01-01-1970 23:45:00",
					                  "02-01-1970 00:29:00","02-01-1970 00:48:00","02-01-1970 01:45:00",
					                  "02-01-1970 02:05:00","02-01-1970 03:10:00","02-01-1970 04:04:00",
					                  "02-01-1970 04:32:00","02-01-1970 04:51:00","02-01-1970 05:14:00",
					                  "02-01-1970 05:50:00","02-01-1970 06:11:00","02-01-1970 06:36:00",
					                  "02-01-1970 06:56:00","02-01-1970 07:12:00","02-01-1970 07:30:00",
					                  "02-01-1970 07:49:00","02-01-1970 08:04:00","02-01-1970 08:18:00",
					                  "02-01-1970 08:35:00","02-01-1970 08:55:00","02-01-1970 09:30:00",
					                  "02-01-1970 10:15:00","02-01-1970 10:45:00","02-01-1970 12:45:00",
					                  "02-01-1970 13:26:00","02-01-1970 13:55:00","02-01-1970 14:32:00",
					                  "02-01-1970 14:57:00","02-01-1970 15:33:00","02-01-1970 16:08:00",
					                  "02-01-1970 16:36:00","02-01-1970 17:23:00","02-01-1970 17:48:00",
					                  "02-01-1970 18:32:00","02-01-1970 19:10:00","02-01-1970 20:10:00",
					                  "02-01-1970 20:44:00","02-01-1970 21:14:00","02-01-1970 22:20:00",
					                  "02-01-1970 22:32:00","02-01-1970 23:32:00","03-01-1970 00:20:00",
					                  "03-01-1970 00:46:00","03-01-1970 01:19:00","03-01-1970 02:04:00",
					                  "03-01-1970 03:25:00","03-01-1970 04:20:00","03-01-1970 05:15:00",
					                  "03-01-1970 05:40:00","03-01-1970 06:10:00","03-01-1970 06:40:00",
					                  "03-01-1970 07:10:00","03-01-1970 07:50:00","03-01-1970 09:15:00",
					                  "03-01-1970 10:00:00","03-01-1970 11:05:00","03-01-1970 13:10:00",
					                  "03-01-1970 14:30:00",""};
			
			logger.debug("*******Rahul arrival dates array length*********:" + arrivalTimes.length);
			logger.debug("*******Rahul departures dates array length*********:" + depatureTimes.length);
			SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			for(int i =0; i < stations.size();i++){
				MetroStationBO station = stations.get(i);
				String arrivalTimeStr = arrivalTimes[i];
				String departureTimeStr = depatureTimes[i];
				
				Date arrivalTime = null;
				Date departureTime = null;
				if(!arrivalTimeStr.equals("")){
					arrivalTime = df.parse(arrivalTimeStr);
				}
				if(!departureTimeStr.equals("")){
					departureTime = df.parse(departureTimeStr);
				}
				
				TrainScheduleTimingBO timing = new TrainScheduleTimingBO(null, station, arrivalTime, departureTime);
				timings.add(timing);
			}
			scheduleService.createOrUpdateTrainSchedule(2, timings);
		}
		catch(ParseException e){
			e.printStackTrace();
			assertTrue(false);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findNextStationsTimings(){
		
		try{
			List<TrainScheduleTimingBO> timings = scheduleService.findNextStationsTimings(2, "New Delhi");
			assertTrue(timings != null && timings.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
