package com.metrosystem.payment.beans;

import java.util.Map;

import com.metrosystem.payment.bank.beans.BankAccount;
import com.metrosystem.payment.bank.exception.BankException;
import com.metrosystem.payment.exception.PaymentException;

public class CreditCard extends PaymentMethod {

	String expiryMonth;
	String expiryYear;
	int cvvNumber;
	double creditLimit;
	
	public CreditCard(String creditCardNum,int cvvNum,String expiryMonth,String expiryYear,double creditLimiit,BankAccount account) {
		super(creditCardNum,account, "CC");
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.cvvNumber = cvvNum;
		this.creditLimit = creditLimiit;
	}

	private boolean authenticateUser(String creditCardNum,String expiryMonth, String expiryYear){
		if(!this.paymentId.equals(creditCardNum)){
			return false;
		}
		if(!this.expiryMonth.equals(expiryMonth)){
			return false;
		}
		if(!this.expiryYear.equals(expiryYear)){
			return false;
		}
		
		return true;
	}
	
	public void increaseLimit(double newLimit) throws PaymentException{
		
		double creditAmount = newLimit - this.creditLimit;
		try {
			creditMoney(creditAmount);
			this.creditLimit=newLimit;
		} catch (PaymentException e) {
			throw new PaymentException(e);
		}
	}
	
	@Override
	public void pay(double amountToBePaid,Map<String, Object> inputParams) throws PaymentException {
		//Authenticate the user first
		if(!authenticateUser((String)inputParams.get("ccNum"),(String)inputParams.get("expMonth"),(String)inputParams.get("expYear"))){
			throw new PaymentException("User Authentication failed");
		}
		
		
		
		try {
			this.account.subtractAmount(amountToBePaid);
		} catch (BankException e) {
	              throw new PaymentException("Insufficient Balance. You may have reached your credit card limit.",e);
		}
	}

	
	private void creditMoney(double amount) throws PaymentException{
		try {
			this.account.addAmount(amount);
		} catch (BankException e) {
			throw new PaymentException(e);
		}
	}


}
