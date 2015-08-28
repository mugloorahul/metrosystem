package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IMetroUserService;
import com.metrosystem.service.beans.MetroUserBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class MetroUserServiceTest {

	private static IMetroUserService userService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			userService = (IMetroUserService) context.getBean("userService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void createUser(){
		try{
			Integer userId = userService.createUser("mugloorahul", "Rahul Mugloo");
			assertTrue(userId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createDuplicateUser(){
		try{
			Integer userId = userService.createUser("mugloorahul", "Rahul Mugloo");
			assertTrue(userId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findUserByIdentifier(){
		try{
		   MetroUserBO bo = userService.findUserByIdentifier("mugloorahul");
		   assertTrue(bo != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findUsersByName(){
		try{
			List<MetroUserBO> bos = userService.findUsersByName("Rahul Mugloo");
			assertTrue(bos != null && bos.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
