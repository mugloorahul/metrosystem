package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.BankAccountBO;
import com.metrosystem.service.beans.CreditCardBO;
import com.metrosystem.service.beans.DebitCardBO;
import com.metrosystem.service.beans.NetBankingBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IBankService {

	public Integer openBankAccount(String accountNumber, double balance, String userIdentifier)
	throws MetroSystemServiceException;
	
	public List<BankAccountBO> getBankAccountsForUser(String userIdentifier)throws  MetroSystemServiceException;
	
	public BankAccountBO findAccountByNumber(String accountNumber) throws MetroSystemServiceException;
	
	public void closeBankAccount(String accountNumber) throws MetroSystemServiceException;
	
	public CreditCardBO findCreditCardByNumber(String creditCardNumber) throws MetroSystemServiceException;
		
	public List<CreditCardBO> findCreditCardsByUser(String userIdentifier) throws MetroSystemServiceException;
		
	public List<CreditCardBO> findCreditCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException;

	public void deleteCreditCard(String creditCardNumber) throws MetroSystemServiceException;
	    
	public Integer createCreditCard(CreditCardBO creditCard,String accountNumber) throws MetroSystemServiceException;

	public DebitCardBO findDebitCardByNumber(String debitCardNumber) throws MetroSystemServiceException;
	    
	public List<DebitCardBO> findDebitCardsByUser(String userIdentifier) throws MetroSystemServiceException;
	    
	public List<DebitCardBO> findDebitCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException;
	    
	public void deleteDebitCard(String debitCardNumber) throws MetroSystemServiceException;
	    
	public Integer createDebitCard(DebitCardBO debitCard, String accountNumber) throws MetroSystemServiceException;	

    public Integer activateNetBanking(NetBankingBO netBanking,String accountNumber) throws MetroSystemServiceException;
    
    public void deactivateNetBanking(String customerId) throws MetroSystemServiceException;
    
    public NetBankingBO findNetBankingByCustomerId(String customerId) throws MetroSystemServiceException;
    
    public NetBankingBO findNetBankingByAccount(String accountNumber) throws MetroSystemServiceException;
    
    public List<NetBankingBO> findNetBankingByUser(String userIdentifier) throws MetroSystemServiceException;
}
