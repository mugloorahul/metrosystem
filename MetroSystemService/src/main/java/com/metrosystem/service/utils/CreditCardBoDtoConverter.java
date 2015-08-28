package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.CreditCardDTO;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.CreditCardBO;

@Component("creditCardBoDtoConverter")
public class CreditCardBoDtoConverter {

	public CreditCardDTO boToDto(Integer id, String creditCardNum,int cvvNumber,String expiryMonth,
            String expiryYear,double creditLimit,BankAccountDTO account){
		
		CreditCardDTO card = new CreditCardDTO(creditCardNum, cvvNumber, expiryMonth, 
				                               expiryYear, creditLimit, account);
		card.setId(id != null?id:0);
		
		return card;
	}
	
	public CreditCardBO dtoToBo(Integer id, String creditCardNum,int cvvNumber,String expiryMonth,
            String expiryYear,double creditLimit,BankAccountBO account){
		
		CreditCardBO card = new CreditCardBO(creditCardNum, cvvNumber, expiryMonth, 
				                               expiryYear, creditLimit, account);
		card.setId(id != null?id:0);
		
		return card;
	}
}
