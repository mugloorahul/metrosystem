package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IMetroTrainService;
import com.metrosystem.service.beans.MetroTrainBO;
import com.metrosystem.service.beans.RouteBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class MetroTrainServiceTest {

	private static IMetroTrainService trainService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			trainService = (IMetroTrainService) context.getBean("trainService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void createTrainWithoutRoute(){
		
		try{
			Integer trainNumber = trainService.createTrain(1, "Godavari Express");
			assertTrue(trainNumber != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	@Test
	public void createTrainWithRoute(){
		
		try{
			Integer trainNumber = trainService.createTrain(2, "Jehlum Express","Jammu to Pune");
			assertTrue(trainNumber != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	@Test
	public void createTrainWithDuplicateNumber(){

		try{
			Integer trainNumber = trainService.createTrain(1, "Swaraj Express");
			assertTrue(trainNumber != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createTrainWithDuplicateName(){

		try{
			Integer trainNumber = trainService.createTrain(3, "Godavari Express");
			assertTrue(trainNumber != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void assignRouteToTrainWithNumber(){
	
		try{
			trainService.assignRouteToTrain("Delhi to Pune", 2);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	@Test
	public void assignRouteToTrainWithName(){
		
		try{
			trainService.assignRouteToTrain("Pune to Jammu", "Godavari Express");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
		
	}
	
	@Test
	public void findTrainByName(){
		try{
			MetroTrainBO bo = trainService.findTrainByName("Godavari Express");
			assertTrue(bo != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findTrainByNumber(){
		try{
			MetroTrainBO bo = trainService.findTrainByNumber(2);
			assertTrue(bo != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createMultipleTrains(){
		
		try{
			List<MetroTrainBO> trains = new ArrayList<MetroTrainBO>();
			int startTrainNumber = 4;
			RouteBO route= new RouteBO("Pune to Jammu");
			route.setRouteId(6);
			for(int i =1; i <=100; i++){
				
				MetroTrainBO bo = new MetroTrainBO(startTrainNumber++, "Train-"+i, route);
				trains.add(bo);
			}
			trainService.createMultipleTrains(trains);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void getTrainsForRoute(){
		try{
			List<MetroTrainBO> trains = trainService.getTrainsForRoute("Pune to Jammu");
			assertTrue(trains != null && trains.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
