package com.metrosystem.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.INetBankingDao;
import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.beans.NetBankingDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("netBankingDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class NetBankingDaoImpl extends MetroSystemDaoImpl<Integer, NetBankingDTO> implements 
      INetBankingDao {

	public NetBankingDaoImpl(){
		super(NetBankingDTO.class);
	}

	public NetBankingDTO queryByCustomerId(String customerId) throws MetroSystemDaoException{
		
    	
    	try{
    		String query = "FROM NetBankingDTO WHERE paymentId =? AND deleted != ?";
    		
    		List<NetBankingDTO> cards = this.queryListOfEntities(query, customerId,"Y");
    		if(cards == null || cards.size() == 0){
    			return null;
    		}
    		
    		return cards.get(0);
    	}
    	catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}
    	
	}
	
	public List<NetBankingDTO> queryByUser(String userIdentifier) throws MetroSystemDaoException{
		
		try{
			String query = "SELECT pay,account " +
		                   " FROM NetBankingDTO pay" +
					       " INNER JOIN pay.account account"+
		                   " INNER JOIN account.user user " +
					       " WHERE user.uniqueIdentifier = ?"+
		                   "   AND account.deleted != ?"+
					       "   AND pay.deleted != ?";
			
			 List<?> result= this.queryListOfEntities(query, userIdentifier,"Y","Y");
			 
			 List<NetBankingDTO> list = new ArrayList<NetBankingDTO>();
			 for(int i =0; i < result.size(); i++){
				 Object[] entities = (Object[])result.get(i);
				 NetBankingDTO nb = (NetBankingDTO) entities[0];
				 nb.setAccount((BankAccountDTO)entities[1]);
				 list.add(nb);
			 }
			 
			 return list;
		}
		catch(Throwable e){
    		throw new MetroSystemDaoException(e);
    	}		
	}
	
	public NetBankingDTO queryByAccountNumber(String accountNumber) throws MetroSystemDaoException{
		
		try{
			String query = "FROM NetBankingDTO pay" +
                           " WHERE pay.account.accountNumber= ? AND deleted != ?" +
					       "   AND pay.account.deleted != ?";
			
    		List<NetBankingDTO> cards = this.queryListOfEntities(query, accountNumber,"Y","Y");
    		if(cards == null || cards.size() == 0){
    			return null;
    		}
    		
    		return cards.get(0);			
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}
	
}
