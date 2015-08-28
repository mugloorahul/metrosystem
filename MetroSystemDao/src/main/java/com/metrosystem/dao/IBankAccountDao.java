package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IBankAccountDao extends IMetroSystemDao<Integer, BankAccountDTO> {

	public List<BankAccountDTO> queryBankAccountsForUser(String userIdentifier) throws MetroSystemDaoException;

	public BankAccountDTO queryAccountByNumber(String accountNumber) throws MetroSystemDaoException;
  
}
