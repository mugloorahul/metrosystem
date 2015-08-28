package com.metrosystem.service.beans;


public class DebitCardBO extends PaymentMethodBO{

	String expiryMonth;
	String expiryYear;
	int cvvNumber;
	
	public DebitCardBO(String debitCardNumber,int cvvNum,String expiryMonth,
			           String expiryYear,BankAccountBO account) 
	{
		super(debitCardNumber,account, "DD");
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.cvvNumber = cvvNum;
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
	
	
}
