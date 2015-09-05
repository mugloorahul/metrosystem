package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IMetroCardService;
import com.metrosystem.service.beans.MetroCardBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class MetroCardServiceTest {

	private static IMetroCardService cardService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			cardService = (IMetroCardService) context.getBean("cardService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void createCard(){
		try{
			Integer cardId = cardService.createMetroCard("134",200, "mugloorahul");
			assertTrue(cardId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
			
		}
	}
	
	@Test
	public void createDuplicateCard(){
		try{
			Integer cardId = cardService.createMetroCard("134",200, "mugloorahul");
			assertTrue(cardId != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
			
		}
	}
	
	@Test
	public void createMultipleCards(){
		try{
			for(int i =1; i <=3; i++){
				Integer cardId = cardService.createMetroCard(i+"",550, "mugloorahul");
				assertTrue(cardId != null);
			}
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findCardsByUser(){
		try{
			List<MetroCardBO> cards = cardService.findCardsForUser("mugloorahul");
			assertTrue(cards != null && cards.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findCardByNumber(){
		try{
			MetroCardBO card = cardService.findCardByNumber("1");
			assertTrue(card != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void deleteCard(){
		try{
			cardService.deleteCard("134");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
