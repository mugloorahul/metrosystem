package com.metrosystem.dao.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="credit_card_payment")
@PrimaryKeyJoinColumn(name="pay_method_id")
public class CreditCardDTO extends PaymentMethodDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String expiryMonth;
	String expiryYear;
	int cvvNumber;
	double creditLimit;
	
	
    public CreditCardDTO(){
		
	}
	
	public CreditCardDTO(String creditCardNum,int cvvNum,String expiryMonth,String expiryYear,double creditLimit,BankAccountDTO account) {
		super(creditCardNum,account, "CC");
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.cvvNumber = cvvNum;
		this.creditLimit = creditLimit;
	}

	/**
	 * @return the expiryMonth
	 */
	@Column(name="expiry_month",length=2,nullable=false)
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
	@Column(name="expiry_year",length=4,nullable=false)
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
	@Column(name="cvv_number",nullable=false)
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
	@Column(name="credit_limit",nullable=false,scale=8,precision=2)
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
