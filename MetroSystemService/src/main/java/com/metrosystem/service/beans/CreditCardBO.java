package com.metrosystem.service.beans;

public class CreditCardBO extends PaymentMethodBO{

	String expiryMonth;
	String expiryYear;
	int cvvNumber;
	double creditLimit;
	
	public CreditCardBO(String creditCardNum,int cvvNumber,String expiryMonth,
			            String expiryYear,double creditLimit,BankAccountBO account) 
	{
		super(creditCardNum,account, "CC");
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.cvvNumber = cvvNumber;
		this.creditLimit = creditLimit;
	}

	/**
	 * @return the expiryMonth
	 */
	public String getExpiryMonth() {
		return expiryMonth;
	}

	/**
	 * @param expiryMonth the expiryMonth to set
	 */
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	/**
	 * @return the expiryYear
	 */
	public String getExpiryYear() {
		return expiryYear;
	}

	/**
	 * @param expiryYear the expiryYear to set
	 */
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	/**
	 * @return the cvvNumber
	 */
	public int getCvvNumber() {
		return cvvNumber;
	}

	/**
	 * @param cvvNumber the cvvNumber to set
	 */
	public void setCvvNumber(int cvvNumber) {
		this.cvvNumber = cvvNumber;
	}

	/**
	 * @return the creditLimit
	 */
	public double getCreditLimit() {
		return creditLimit;
	}

	/**
	 * @param creditLimit the creditLimit to set
	 */
	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}
	
	
}
