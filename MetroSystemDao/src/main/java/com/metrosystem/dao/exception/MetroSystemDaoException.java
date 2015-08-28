package com.metrosystem.dao.exception;

public class MetroSystemDaoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MetroSystemDaoException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public MetroSystemDaoException(String msg) {
		super(msg);
	}
	
	public MetroSystemDaoException(Throwable cause) {
		super(cause);
	}
}
