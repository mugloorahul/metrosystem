package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.CreditCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface ICreditCardDao extends IMetroSystemDao<Integer, CreditCardDTO> {

	public CreditCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException;
	
	public List<CreditCardDTO> queryCardsByUser(String userIdentifier) throws MetroSystemDaoException;
	
	public List<CreditCardDTO> queryCardByAccount(String accountNumber) throws MetroSystemDaoException;
	
}
