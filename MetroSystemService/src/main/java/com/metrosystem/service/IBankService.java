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
	
	public List<BankAccountBO> getActiveBankAccountsForUser(String userIdentifier)throws  MetroSystemServiceException;
	
	public BankAccountBO findActiveBankAccountByNumber(String accountNumber) throws MetroSystemServiceException;
	
	public void closeBankAccount(String accountNumber) throws MetroSystemServiceException;
	
	public CreditCardBO findActiveCreditCardByNumber(String creditCardNumber) throws MetroSystemServiceException;
		
	public List<CreditCardBO> findActiveCreditCardsByUser(String userIdentifier) throws MetroSystemServiceException;
		
	public List<CreditCardBO> findActiveCreditCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException;

	public void deleteCreditCard(String creditCardNumber) throws MetroSystemServiceException;
	    
	public Integer createCreditCard(CreditCardBO creditCard,String accountNumber) throws MetroSystemServiceException;

	public DebitCardBO findActiveDebitCardByNumber(String debitCardNumber) throws MetroSystemServiceException;
	    
	public List<DebitCardBO> findActiveDebitCardsByUser(String userIdentifier) throws MetroSystemServiceException;
	    
	public List<DebitCardBO> findActiveDebitCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException;
	    
	public void deleteDebitCard(String debitCardNumber) throws MetroSystemServiceException;
	    
	public Integer createDebitCard(DebitCardBO debitCard, String accountNumber) throws MetroSystemServiceException;	

    public Integer activateNetBanking(NetBankingBO netBanking,String accountNumber) throws MetroSystemServiceException;
    
    public void deactivateNetBanking(String customerId) throws MetroSystemServiceException;
    
    public NetBankingBO findActiveNetBankingByCustomerId(String customerId) throws MetroSystemServiceException;
    
    public NetBankingBO findActiveNetBankingByAccount(String accountNumber) throws MetroSystemServiceException;
    
    public List<NetBankingBO> findActiveNetBankingByUser(String userIdentifier) throws MetroSystemServiceException;
}
