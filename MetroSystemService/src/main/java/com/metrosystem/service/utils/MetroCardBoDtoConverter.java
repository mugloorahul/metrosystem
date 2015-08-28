package com.metrosystem.service.utils;

import org.springframework.stereotype.Component;

import com.metrosystem.dao.beans.MetroCardDTO;
import com.metrosystem.dao.beans.MetroUserDTO;
import com.metrosystem.service.beans.MetroCardBO;
import com.metrosystem.service.beans.MetroUserBO;

@Component("cardBoDtoConverter")
public class MetroCardBoDtoConverter {

	public MetroCardDTO boToDto(Integer cardId,String cardNumber,MetroUserDTO user,double balance){
		
		MetroCardDTO card = new MetroCardDTO(cardNumber,balance, user);
		card.setCardId(cardId==null?0:cardId);

		return card;
	}
	
	public MetroCardBO dtoToBo(Integer cardId,String cardNumber, MetroUserBO user, double balance){
		
		MetroCardBO card = new MetroCardBO(cardNumber,balance, user);
        card.setCardId(cardId==null?0:cardId);
        
		return card;
		
	}
}
