package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.CreditCardBO;
import com.metrosystem.service.beans.DebitCardBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IPaymentMethodService {

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
    
}
