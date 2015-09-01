package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IMetroStationService;
import com.metrosystem.service.beans.MetroStationBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class MetroStationServiceTest {

	private static IMetroStationService stationService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			stationService = (IMetroStationService) context.getBean("stationService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void createStation(){
		try{
			Integer id = stationService.createStation("Jammu", "33.6", "40.1");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createDuplicateStation(){
		try{
			Integer id = stationService.createStation("Jammu", "33.6", "40.1");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findStationByName(){
		try{
			MetroStationBO station = stationService.findStationByName("Jammu");
			assertTrue(station != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void addStationToRoute(){
		try{
			//MetroStationBO station = stationService.findStationByName("Jammu");
			stationService.addStationToRoute("Jammu", "Jammu to Pune", 1);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void addStationToRouteWithDuplicateSequence(){
		try{
			//MetroStationBO station = stationService.findStationByName("Jammu");
			stationService.addStationToRoute("Jammu", "Jammu to Pune", 1);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void getStationsForRoute(){
		try{
			List<MetroStationBO> stations = stationService.getStationsForRoute("Jammu to Pune");
			assertTrue(stations != null && stations.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
