package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IPaymentMethodService;
import com.metrosystem.service.beans.CreditCardBO;
import com.metrosystem.service.beans.DebitCardBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class PaymentMethodServiceTest {

	private static IPaymentMethodService paymentService; 
	private final static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			paymentService = (IPaymentMethodService) context.getBean("paymentMethodService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void createCreditCard(){
		
		try{
			CreditCardBO card = new CreditCardBO("1889 87656 123 456", 
					223, "12", "2014", 10000, null);
			
			Integer id = paymentService.createCreditCard(card, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findCreditCardByNumber(){
		
		try{
			CreditCardBO card = paymentService.findCreditCardByNumber("1889 87656 123 456");
			assertTrue(card != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findCreditCardsByAccountNumber(){
		
		try{
			List<CreditCardBO> cards = paymentService.findCreditCardsByAccountNumber("12323232332323");
			assertTrue(cards != null && cards.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findCreditCardsByUser(){
		
		try{
			List<CreditCardBO> cards = paymentService.findCreditCardsByUser("mugloorahul");
			assertTrue(cards != null && cards.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void deleteCreditCard(){
		
		try{
			paymentService.deleteCreditCard("1889 87656 123 456");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createDebitCard(){
		
		try{
			DebitCardBO card = new DebitCardBO("1456-789-0123-6574", 223, "12", "2014", null);
			Integer id = paymentService.createDebitCard(card, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findDebitCardByNumber(){
		
		try{
			DebitCardBO card = paymentService.findDebitCardByNumber("1456-789-0123-6574");
			assertTrue(card != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findDebitCardsByUser(){
		
		try{
			List<DebitCardBO> cards = paymentService.findDebitCardsByUser("mugloorahul");
			assertTrue(cards != null && cards.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findDebitCardsByAccountNumber(){
		
		try{
			List<DebitCardBO> cards = paymentService.findDebitCardsByAccountNumber("12323232332323");
			assertTrue(cards != null && cards.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void deleteDebitCard(){
		
		try{
			paymentService.deleteDebitCard("1456-789-0123-6574");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
