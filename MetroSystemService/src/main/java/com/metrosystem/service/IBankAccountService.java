package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IBankAccountService {

	public Integer openBankAccount(String accountNumber, double balance, String userIdentifier)
	throws MetroSystemServiceException;
	
	public List<BankAccountBO> getBankAccountsForUser(String userIdentifier)throws  MetroSystemServiceException;
	
	public BankAccountBO findAccountByNumber(String accountNumber) throws MetroSystemServiceException;
	
	public void closeBankAccount(String accountNumber) throws MetroSystemServiceException;
}
