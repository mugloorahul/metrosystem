package com.metrosystem.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IDebitCardDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.DebitCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("debitCardDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class DebitCardDaoImpl extends MetroSystemDaoImpl<Integer, DebitCardDTO> implements 
     IDebitCardDao {

	public DebitCardDaoImpl(){
		super(DebitCardDTO.class);
	}
	
	@Override
    public DebitCardDTO queryActiveCardByNumber(String cardNumber) throws MetroSystemDaoException{
    	
    	try{
    		String query = "FROM DebitCardDTO WHERE paymentId =? AND deleted != ?";
    		
    		List<DebitCardDTO> cards = this.queryListOfEntities(query, cardNumber,"Y");
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
	public List<DebitCardDTO> queryActiveCardsByUser(String userIdentifier) throws MetroSystemDaoException{
		
		try{
			String query = "SELECT pay,account " +
		                   " FROM DebitCardDTO pay" +
					       " INNER JOIN pay.account account"+
		                   " INNER JOIN account.user user " +
					       " WHERE user.uniqueIdentifier = ?"+
		                   "   AND account.deleted != ?"+
					       "   AND pay.deleted != ?";
			
			 List<?> result= this.queryListOfEntities(query, userIdentifier,"Y","Y");
			 
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
	
	@Override
	public List<DebitCardDTO> queryActiveCardsByAccount(String accountNumber) throws MetroSystemDaoException{
		
		try{
			String query = "FROM DebitCardDTO pay" +
	                       " WHERE pay.account.accountNumber= ? AND deleted != ?" +
					       "   AND pay.account.deleted != ?";
		
		    return this.queryListOfEntities(query, accountNumber,"Y","Y");
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
	
	@Override
    public DebitCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException{
    	
    	try{
    		String query = "FROM DebitCardDTO WHERE paymentId =?";
    		
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
	
	@Override
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
	
	@Override
	public List<DebitCardDTO> queryCardsByAccount(String accountNumber) throws MetroSystemDaoException{
		
		try{
			String query = "FROM DebitCardDTO pay" +
	                       " WHERE pay.account.accountNumber= ?";
		
		    return this.queryListOfEntities(query, accountNumber);
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
	}
}
