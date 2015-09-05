package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.MetroUserBO;

@Component("accountBoDtoConverter")
public class BankAccountBoDtoConverter {

	public BankAccountDTO boToDtoConverter(Integer accountId,String accountNumber,double balance, MetroUserDTO user){
		
		BankAccountDTO account = new BankAccountDTO(accountNumber, balance, user);
		account.setAccountId(accountId==null?0:accountId);
		
		
		return account;
	}
	
    public BankAccountBO dtoToBoConverter(Integer accountId,String accountNumber,double balance, MetroUserBO user){
		
		BankAccountBO account = new BankAccountBO(accountNumber, balance, user);
		account.setAccountId(accountId==null?0:accountId);
		
		return account;
	}
    

}
