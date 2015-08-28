package com.metrosystem.payment.beans;

import java.util.Map;

import com.metrosystem.payment.bank.beans.BankAccount;
import com.metrosystem.payment.bank.exception.BankException;
import com.metrosystem.payment.exception.PaymentException;

public class NetBanking extends PaymentMethod {

	String password;
	
	public NetBanking(String customerId,String password,BankAccount account) {
		super(customerId,account, "NB");
		this.password = password;
	}

	private boolean authenticateUser(String customerId,String password){
		if(!this.paymentId.equals(customerId)){
			return false;
		}
		if(!this.password.equals(password)){
			return false;
		}
		
		return true;
	}
		
	@Override
	public void pay(double amountToBePaid,Map<String, Object> inputParams) throws PaymentException {
		//Authenticate the user first
		if(!authenticateUser((String)inputParams.get("customerId"),(String)inputParams.get("netBankingPwd"))){
			throw new PaymentException("User Authentication failed");
		}
				
		try {
			this.account.subtractAmount(amountToBePaid);
		} catch (BankException e) {
	              throw new PaymentException("Insufficient Balance. You may have reached your credit card limit.",e);
		}
	}

}
