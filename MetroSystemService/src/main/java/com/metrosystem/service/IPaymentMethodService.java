package com.metrosystem.service;

import java.util.List;

import com.metrosystem.service.beans.CreditCardBO;
import com.metrosystem.service.exception.MetroSystemServiceException;

public interface IPaymentMethodService {

    public CreditCardBO findCreditCardByNumber(String creditCardNumber) throws MetroSystemServiceException;
	
	public List<CreditCardBO> findCreditCardsByUser(String userIdentifier) throws MetroSystemServiceException;
	
	public List<CreditCardBO> findCreditCardsByAccountNumber(String accountNumber) throws MetroSystemServiceException;

    public void deleteCreditCard(String creditCardNumber) throws MetroSystemServiceException;
    
    public Integer createCreditCard(CreditCardBO creditCard,String accountNumber) throws MetroSystemServiceException;
}
