package com.metrosystem.dao;

import java.util.List;

import com.metrosystem.dao.beans.BankAccountDTO;
import com.metrosystem.dao.exception.MetroSystemDaoException;

public interface IBankAccountDao extends IMetroSystemDao<Integer, BankAccountDTO> {

	public List<BankAccountDTO> queryActiveBankAccountsForUser(String userIdentifier) throws MetroSystemDaoException;

	public BankAccountDTO queryActiveBankAccountByNumber(String accountNumber) throws MetroSystemDaoException;
  
	public List<BankAccountDTO> queryBankAccountsForUser(String userIdentifier) throws MetroSystemDaoException;

	public BankAccountDTO queryBankAccountByNumber(String accountNumber) throws MetroSystemDaoException;
}
