package com.metrosystem.service.exception;

public class ServiceValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceValidationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ServiceValidationException(String msg) {
		super(msg);
	}
	
	public ServiceValidationException(Throwable cause) {
		super(cause);
	}
}
