package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;
import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.DebitCardDTO;
import com.metrosystem.service.beans.DebitCardBO;

@Component("debitCardBoDtoConverter")
public class DebitCardBoDtoConverter {

	public DebitCardDTO boToDto(Integer id,String debitCardNumber,int cvvNum,String expiryMonth,
			                    String expiryYear,BankAccountDTO account){
		
		DebitCardDTO card = new DebitCardDTO(debitCardNumber, cvvNum, expiryMonth, 
				                             expiryYear, account);
		card.setId(id!= null?id:0);
		
		return card;
	}
	
    public DebitCardBO dtoToBo(Integer id,String debitCardNumber,int cvvNum,String expiryMonth,
            String expiryYear,BankAccountBO account){
    	
    	DebitCardBO card = new DebitCardBO(debitCardNumber, cvvNum, expiryMonth, 
                                            expiryYear, account);
        card.setId(id!= null?id:0);

        return card;
    }
}
