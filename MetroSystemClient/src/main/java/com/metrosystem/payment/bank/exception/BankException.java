package com.metrosystem.payment.bank.exception;

public class BankException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BankException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public BankException(String msg) {
		super(msg);
	}
	
	public BankException(Throwable cause) {
		super(cause);
	}
	
}
