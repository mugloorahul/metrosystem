package com.metrosystem.service.exception;

public class MetroSystemServiceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MetroSystemServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MetroSystemServiceException(String msg) {
		super(msg);
	}
	
	public MetroSystemServiceException(Throwable cause) {
		super(cause);
	}
}
