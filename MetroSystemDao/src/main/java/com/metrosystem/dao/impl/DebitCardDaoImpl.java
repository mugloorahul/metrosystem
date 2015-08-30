package com.metrosystem.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.metrosystem.dao.IDebitCardDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.DebitCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public class DebitCardDaoImpl extends MetroSystemDaoImpl<Integer, DebitCardDTO> implements 
     IDebitCardDao {

	public DebitCardDaoImpl(){
		super(DebitCardDTO.class);
	}
	
    public DebitCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException{
    	
    	try{
    		String query = "FROM DebitCardDTO WHERE paymentId =? ";
    		
    		List<DebitCardDTO> cards = this.queryListOfEntities(query, cardNumber);
    		if(cards == null || cards.size() == 0){
    			return null;
    		}
    		
    		return cards.get(0);
    	}
    	catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
    }
	
	public List<DebitCardDTO> queryCardsByUser(String userIdentifier) throws MetroSystemDaoException{
		
		try{
			String query = "SELECT pay,account " +
		                   " FROM DebitCardDTO pay" +
					       " INNER JOIN pay.account account"+
		                   " INNER JOIN account.user user " +
					       " WHERE user.uniqueIdentifier = ?";
			
			 List<?> result= this.queryListOfEntities(query, userIdentifier);
			 
			 List<DebitCardDTO> cards = new ArrayList<DebitCardDTO>();
			 for(int i =0; i < result.size(); i++){
				 Object[] entities = (Object[])result.get(i);
				 DebitCardDTO card = (DebitCardDTO) entities[0];
				 card.setAccount((BankAccountDTO)entities[1]);
				 cards.add(card);
			 }
			 
			 return cards;
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
	
	public List<DebitCardDTO> queryCardByAccount(String accountNumber) throws MetroSystemDaoException{
		
		try{
			String query = "FROM DebitCardDTO pay" +
	                       " WHERE pay.account.accountNumber= ? " ;
		
		    return this.queryListOfEntities(query, accountNumber);
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
	
}
