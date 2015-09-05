package com.metrosystem.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.ICreditCardDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.CreditCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Transactional(readOnly=true,rollbackFor={Exception.class})
@Repository("creditCardDao")
public class CreditCardDaoImpl extends MetroSystemDaoImpl<Integer, CreditCardDTO> implements
		ICreditCardDao {

	public CreditCardDaoImpl(){
		super(CreditCardDTO.class);
	}
	
	@Override
    public CreditCardDTO queryActiveCardByNumber(String cardNumber) throws MetroSystemDaoException{
    	
    	try{
    		String query = "FROM CreditCardDTO WHERE paymentId =? and deleted != ? ";
    		
    		List<CreditCardDTO> cards = this.queryListOfEntities(query, cardNumber,"Y");
    		if(cards == null || cards.size() == 0){
    			return null;
    		}
    		
    		return cards.get(0);
    	}
    	catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
    }
	
    @Override
	public List<CreditCardDTO> queryActiveCardsByUser(String userIdentifier) throws MetroSystemDaoException{
		
		try{
			String query = "SELECT pay,account " +
		                   " FROM CreditCardDTO pay" +
					       " INNER JOIN pay.account account"+
		                   " INNER JOIN account.user user " +
					       " WHERE user.uniqueIdentifier = ?" +
		                   "   AND account.deleted != ?" +
					       "   AND pay.deleted != ?";
			
			 List<?> result= this.queryListOfEntities(query, userIdentifier,"Y","Y");
			 
			 List<CreditCardDTO> cards = new ArrayList<CreditCardDTO>();
			 for(int i =0; i < result.size(); i++){
				 Object[] entities = (Object[])result.get(i);
				 CreditCardDTO card = (CreditCardDTO) entities[0];
				 card.setAccount((BankAccountDTO)entities[1]);
				 cards.add(card);
			 }
			 
			 return cards;
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
	
	@Override
	public List<CreditCardDTO> queryActiveCardsByAccount(String accountNumber) throws MetroSystemDaoException{
		
		try{
			String query = "FROM CreditCardDTO pay" +
	                       " WHERE pay.account.accountNumber= ? and pay.deleted != ?" +
					       "  AND pay.account.deleted != ?";
		
		    return this.queryListOfEntities(query, accountNumber,"Y","Y");
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
	
	@Override
    public CreditCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException{
    	
    	try{
    		String query = "FROM CreditCardDTO WHERE paymentId =?";
    		
    		List<CreditCardDTO> cards = this.queryListOfEntities(query, cardNumber);
    		if(cards == null || cards.size() == 0){
    			return null;
    		}
    		
    		return cards.get(0);
    	}
    	catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
    }
	
    @Override
	public List<CreditCardDTO> queryCardsByUser(String userIdentifier) throws MetroSystemDaoException{
		
		try{
			String query = "SELECT pay,account " +
		                   " FROM CreditCardDTO pay" +
					       " INNER JOIN pay.account account"+
		                   " INNER JOIN account.user user " +
					       " WHERE user.uniqueIdentifier = ?";
			
			 List<?> result= this.queryListOfEntities(query, userIdentifier);
			 
			 List<CreditCardDTO> cards = new ArrayList<CreditCardDTO>();
			 for(int i =0; i < result.size(); i++){
				 Object[] entities = (Object[])result.get(i);
				 CreditCardDTO card = (CreditCardDTO) entities[0];
				 card.setAccount((BankAccountDTO)entities[1]);
				 cards.add(card);
			 }
			 
			 return cards;
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
	
	@Override
	public List<CreditCardDTO> queryCardsByAccount(String accountNumber) throws MetroSystemDaoException{
		
		try{
			String query = "FROM CreditCardDTO pay" +
	                       " WHERE pay.account.accountNumber= ? ";
		
		    return this.queryListOfEntities(query, accountNumber,"Y","Y");
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
	
}
