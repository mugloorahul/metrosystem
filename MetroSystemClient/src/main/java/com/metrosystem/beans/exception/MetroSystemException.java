package com.metrosystem.exception;

public class MetroSystemException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MetroSystemException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MetroSystemException(String msg) {
		super(msg);
	}
	
	public MetroSystemException(Throwable cause) {
		super(cause);
	}
	
}
