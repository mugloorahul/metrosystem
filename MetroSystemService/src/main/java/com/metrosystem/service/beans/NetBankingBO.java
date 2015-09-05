package com.metrosystem.service.beans;


public class NetBankingBO extends PaymentMethodBO{

	String password;
	
	public NetBankingBO(String customerId,String password,BankAccountBO account) {
		super(customerId,account, "NB");
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
