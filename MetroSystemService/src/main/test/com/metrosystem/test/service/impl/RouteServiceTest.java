package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IRouteService;
import com.metrosystem.service.beans.RouteBO;


public class RouteServiceTest {

	private static IRouteService routeService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			routeService = (IRouteService) context.getBean("routeService");
		}
		catch(Throwable e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createRoute(){
		try{
           Integer routeId = routeService.createRoute("Jammu To Pune");	
           assertTrue(routeId != null);
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createDuplicateRoute(){
		try{
           Integer routeId = routeService.createRoute("Pune to Delhi");	
           assertTrue(routeId != null);
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	
	@Test
	public void findRouteByName(){
		try{
			RouteBO bo = routeService.findRouteByName("Jammu to Pune");
			assertTrue(bo != null);
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void getRoutesForStation(){
		try{
			List<RouteBO> routes = routeService.getRoutesForStation("Jammu");
			assertTrue(routes != null && routes.size() > 0);
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
