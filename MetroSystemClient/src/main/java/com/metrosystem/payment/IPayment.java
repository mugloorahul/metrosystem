package com.metrosystem.payment;

import java.util.Map;

import com.metrosystem.payment.exception.PaymentException;

public interface IPayment {

	public void pay(double amountToBePaid,Map<String, Object> inputParams) throws PaymentException;
	
}
