package com.metrosystem.payment.beans;

import com.metrosystem.payment.IPayment;
import com.metrosystem.payment.bank.beans.BankAccount;

public abstract class PaymentMethod implements IPayment{

	protected BankAccount account;
	protected String paymentType;
	protected String paymentId;
	
	public PaymentMethod(String paymentId,BankAccount account,String paymentType) {
		this.account = account;
		this.paymentType = paymentType;
		this.paymentId=paymentId;
	}
	
	public String getPaymentType(){
		return this.paymentType;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof PaymentMethod)){
			return false;
		}
		
		PaymentMethod other = (PaymentMethod)obj;
		
		return this.paymentId==other.paymentId;
	}
	
	@Override
	public int hashCode(){
		return this.paymentId.hashCode();
	}
}
