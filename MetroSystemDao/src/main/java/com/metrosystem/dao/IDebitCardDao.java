package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.DebitCardDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IDebitCardDao extends IMetroSystemDao<Integer, DebitCardDTO>{

    public DebitCardDTO queryCardByNumber(String cardNumber) throws MetroSystemDaoException;
	
	public List<DebitCardDTO> queryCardsByUser(String userIdentifier) throws MetroSystemDaoException;
	
	public List<DebitCardDTO> queryCardByAccount(String accountNumber) throws MetroSystemDaoException;
}
