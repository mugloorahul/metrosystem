package com.metrosystem.test.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.metrosystem.service.IBankAccountService;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public class BankAccountServiceTest {

	private static IBankAccountService accountService; 
	private static String cfg_location = "com/metrosystem/service/spring/service_layer.xml";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		try{
			//Load spring file
			ApplicationContext context = new ClassPathXmlApplicationContext(cfg_location);
			//stationDao = new MetroSystemDaoImpl<Integer, MetroStationDTO>();
			accountService = (IBankAccountService) context.getBean("accountService");
		}
		catch(Exception e){
			e.printStackTrace();
			assertTrue(false);
		}

	}
	
	@Test
	public void openBankAccount(){
		try{
			Integer num = accountService.openBankAccount("14445568776543", 200, "mugloorahul");
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
	        	 Integer accountNum = accountService.openBankAccount(num, 1000, "mugloorahul");
	        	 assertTrue(accountNum != null);
	         }
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findAccountsForUser(){
		try{
			List<BankAccountBO> accounts = accountService.getBankAccountsForUser("mugloorahul");
			assertTrue(accounts != null && accounts.size() > 0);
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void findAccountByNumber(){
		try{
			BankAccountBO account = accountService.findAccountByNumber("12323232332323");
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
			accountService.closeBankAccount("677789654223");
		}
		catch(MetroSystemServiceException e){
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
