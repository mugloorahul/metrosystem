package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.DebitCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IDebitCardDao extends IMetroSystemDao<Integer, DebitCardDTO>{

    public DebitCardDTO queryActiveCardByNumber(String cardNumber) throws MetroSystemDaoException;
	
	public List<DebitCardDTO> queryActiveCardsByUser(String userIdentifier) throws MetroSystemDaoException;
	
	public List<DebitCardDTO> queryActiveCardsByAccount(String accountNumber) throws MetroSystemDaoException;
	
	public DebitCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException;
		
	public List<DebitCardDTO> queryCardsByUser(String userIdentifier) throws MetroSystemDaoException;
		
	public List<DebitCardDTO> queryCardsByAccount(String accountNumber) throws MetroSystemDaoException;
}
