package com.metrosystem.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.metrosystem.dao.IMetroCardDao;
import com.metrosystem.dao.beans.MetroCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

@Repository("cardDao")
@Transactional(readOnly=true,rollbackFor={Exception.class})
public class MetroCardDaoImpl extends MetroSystemDaoImpl<Integer, MetroCardDTO> 
implements IMetroCardDao {

	
	public MetroCardDaoImpl(){
		super(MetroCardDTO.class);
	}

	@Override
	public List<MetroCardDTO> queryCardsForUser(String userIdentifier) throws MetroSystemDaoException {

		try{
			String query = "SELECT card FROM MetroCardDTO card " +
	                       " WHERE card.user.uniqueIdentifier = ?";
			
			return this.queryListOfEntities(query, userIdentifier);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

	@Override
	public MetroCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException {
		
		try{
			String query = "FROM MetroCardDTO where cardNumber=?";
			
			List<MetroCardDTO> cards = this.queryListOfEntities(query, cardNumber);
			
			if(cards == null || cards.size() ==0){
				return null;
			}
			
			return cards.get(0);
		}
		catch(Throwable e){
			throw new MetroSystemDaoException(e);
		}
	}

}
