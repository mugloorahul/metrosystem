package com.metrosystem.service.beans;

import java.util.Set;

public class BankAccountBO {

	private Integer accountId;
	private String accountNumber;
	private double balance=0.0;
	private MetroUserBO user;
	private Set<PaymentMethodBO> payMethods;
	
	/** Default constructor*/
	public BankAccountBO(){
		
	}

	public BankAccountBO(String accountNumber, double balance,MetroUserBO user){
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.user = user;
	}
	
	public BankAccountBO(String accountNumber, double balance,
			             MetroUserBO user, Set<PaymentMethodBO> payMethods) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.user = user;
		this.payMethods = payMethods;
	}

	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the user
	 */
	public MetroUserBO getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(MetroUserBO user) {
		this.user = user;
	}

	/**
	 * @return the payMethods
	 */
	public Set<PaymentMethodBO> getPayMethods() {
		return payMethods;
	}

	/**
	 * @param payMethods the payMethods to set
	 */
	public void setPayMethods(Set<PaymentMethodBO> payMethods) {
		this.payMethods = payMethods;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof BankAccountBO)){
			return false;
		}
		
		BankAccountBO otherAccount = (BankAccountBO)object;
		
		return this.accountNumber==otherAccount.accountNumber;
	}
	
	@Override
	public int hashCode(){
		return this.accountNumber.hashCode();
	}
}
