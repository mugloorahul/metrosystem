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
			Integer id = stationService.createStation("Bangalore", "33.6", "40.1");
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
			Integer id = stationService.createStation("Bangalore", "33.6", "40.1");
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
			stationService.addStationToRoute("Delhi", "Pune to Delhi", 1);
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
			stationService.addStationToRoute("Delhi", "Pune to Delhi", 2);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void getStationsForRoute(){
		try{
			List<MetroStationBO> stations = stationService.getStationsForRoute("Pune to Delhi");
			assertTrue(stations != null && stations.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void addDuplicateStationToRoute(){
		
		try{
			stationService.addStationToRoute("Jammu", "Pune to Delhi", 2);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void changeStationSequenceForRoute(){
		
		try{
			stationService.changeStationSequenceForRoute("Pune", "Pune to Delhi", 3);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void createStationsForJammuToPuneRoute(){
		
		try{
			String stations[] = {"Jammu Tawi","Vijaypur Jammu","Samba","Hira Nagar","Kathua","Pathankot Cantt",
					             "Mukerian","Dasuya","Jalandhar Cantt. Junction","Phagwara Junction",
					             "Ludhiana Junction","Khanna","Sirhind Junction","Rajpura Junction",
					             "Ambala City","Ambala Cantt. Junction","Shahbad Markanda","Kurukshetra Junction",
					             "Taraori","Karnal","Gharaunda","Panipat Junction","Samalkha","Ganaur","Sonipat",
					             "Narela","Sabzi Mandi","New Delhi","Faridabad","Mathura Junction","Raja Ki Mandi",
					             "Agra Cantt.","Dholpur Junction","Morena","Gwalior Junction","Dabra","Datia",
					             "Jhansi Junction","Babina","Lalitpur","Dhaura","Bina Junction","Ganj Basoda",
					             "Vidisha","Bhopal Junction","Bhopal HabibGanj","Hoshangabad","Itarsi Junction",
					             "Banapura","Harda","Chhanera","Khandwa Junction","Burhanpur","Bhusaval Junction",
					             "Jalgaon Junction","Pachora Junction","Chalisgaon Junction","Nandgaon",
					             "Manmad Junction","Kopargaon","Belapur","Ahmadnagar","Daund Junction","Uruli",
					             "Pune Junction"};
			
			for(int i =0; i < stations.length;i++){
				int sequence = i+1;
				stationService.createStationForRoute(stations[i], "20.3", "30.6", "Jammu To Pune", sequence);
			}
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
