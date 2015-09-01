package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IBankService;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.CreditCardBO;
import com.metrosystem.service.beans.DebitCardBO;
import com.metrosystem.service.beans.NetBankingBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class BankServiceTest {

	private static IBankService bankService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			bankService = (IBankService) context.getBean("bankService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void openBankAccount(){
		try{
			Integer num = bankService.openBankAccount("14445568776543", 200, "mugloorahul");
			assertTrue(num != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void openDuplicateBankAccount(){
		try{
			Integer num = bankService.openBankAccount("14445568776543", 200, "mugloorahul");
			assertTrue(num != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void openMultipleBankAccountsForUser(){
		try{
	         String nums[] = {"12323232332323","677789654223","8765390876"};
	         for(String num : nums){
	        	 Integer accountNum = bankService.openBankAccount(num, 1000, "mugloorahul");
	        	 assertTrue(accountNum != null);
	         }
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveBankAccountsForUser(){
		try{
			List<BankAccountBO> accounts = bankService.getActiveBankAccountsForUser("mugloorahul");
			assertTrue(accounts != null && accounts.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveBankAccountByNumber(){
		try{
			BankAccountBO account = bankService.findActiveBankAccountByNumber("12323232332323");
			assertTrue(account != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void closeAccount(){
		try{
			bankService.closeBankAccount("677789654223");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createCreditCard(){
		
		try{
			CreditCardBO card = new CreditCardBO("1889 87656 123 456", 
					223, "12", "2014", 10000, null);
			
			Integer id = bankService.createCreditCard(card, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createDuplicateCreditCard(){
		
		try{
			CreditCardBO card = new CreditCardBO("1889 87656 123 456", 
					223, "12", "2014", 10000, null);
			
			Integer id = bankService.createCreditCard(card, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveCreditCardByNumber(){
		
		try{
			CreditCardBO card = bankService.findActiveCreditCardByNumber("1889 87656 123 456");
			assertTrue(card != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveCreditCardsByAccountNumber(){
		
		try{
			List<CreditCardBO> cards = bankService.findActiveCreditCardsByAccountNumber("12323232332323");
			assertTrue(cards != null && cards.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveCreditCardsByUser(){
		
		try{
			List<CreditCardBO> cards = bankService.findActiveCreditCardsByUser("mugloorahul");
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
			bankService.deleteCreditCard("1889 87656 123 456");
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
			Integer id = bankService.createDebitCard(card, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void createDuplicateDebitCard(){
		
		try{
			DebitCardBO card = new DebitCardBO("1456-789-0123-6574", 223, "12", "2014", null);
			Integer id = bankService.createDebitCard(card, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveDebitCardByNumber(){
		
		try{
			DebitCardBO card = bankService.findActiveDebitCardByNumber("1456-789-0123-6574");
			assertTrue(card != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveDebitCardsByUser(){
		
		try{
			List<DebitCardBO> cards = bankService.findActiveDebitCardsByUser("mugloorahul");
			assertTrue(cards != null && cards.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveDebitCardsByAccountNumber(){
		
		try{
			List<DebitCardBO> cards = bankService.findActiveDebitCardsByAccountNumber("12323232332323");
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
			bankService.deleteDebitCard("1456-789-0123-6574");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void activateNetBanking(){
		
		try{
			NetBankingBO bo = new NetBankingBO("mugloorahul", "test123", null);
			Integer id = bankService.activateNetBanking(bo, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void activateDuplicateNetBanking(){
		
		try{
			NetBankingBO bo = new NetBankingBO("mugloorahul", "test123", null);
			Integer id = bankService.activateNetBanking(bo, "12323232332323");
			assertTrue(id != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void deactivateNetBanking(){
		
		try{
			bankService.deactivateNetBanking("mugloorahul");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveNetBankingByCustomerId(){
		
		try{
			NetBankingBO nb = bankService.findActiveNetBankingByCustomerId("mugloorahul");
			assertTrue(nb != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveNetBankingByAccount(){
		
		try{
			NetBankingBO nb = bankService.findActiveNetBankingByAccount("12323232332323");
			assertTrue(nb != null);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findActiveNetBankingByUser(){
		
		try{
			List<NetBankingBO> nbBOs = bankService.findActiveNetBankingByUser("mugloorahul");
			assertTrue(nbBOs != null && nbBOs.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
