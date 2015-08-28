package com.metrosystem.payment.beans;

import java.util.Map;

import com.metrosystem.payment.bank.beans.BankAccount;
import com.metrosystem.payment.bank.exception.BankException;
import com.metrosystem.payment.exception.PaymentException;

public class DebitCard extends PaymentMethod {

	String expiryMonth;
	String expiryYear;
	int cvvNumber;
	
	public DebitCard(String number,int cvvNum,String expiryMonth,String expiryYear,BankAccount account) {
		super(number,account, "DD");
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.cvvNumber = cvvNum;
	}

	private boolean authenticateUser(String cardNumber,String expiryMonth, String expiryYear){
		if(!this.paymentId.equals(cardNumber)){
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
		
	@Override
	public void pay(double amountToBePaid,Map<String, Object> inputParams) throws PaymentException {
		//Authenticate the user first
		if(!authenticateUser((String)inputParams.get("ddNum"),(String)inputParams.get("expMonth"),(String)inputParams.get("expYear"))){
			throw new PaymentException("User Authentication failed");
		}
		
		
		
		try {
			this.account.subtractAmount(amountToBePaid);
		} catch (BankException e) {
	              throw new PaymentException("Insufficient Balance. You may have reached your credit card limit.",e);
		}
	}

}
