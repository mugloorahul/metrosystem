package com.metrosystem.dao.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;


@Entity
@Table(name="debit_card_payment")
@PrimaryKeyJoinColumn(name="pay_method_id")
@SQLDelete(sql="UPDATE payment_method SET deleted = 'Y' WHERE id=?")
public class DebitCardDTO extends PaymentMethodDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String expiryMonth;
	String expiryYear;
	int cvvNumber;
	
	public DebitCardDTO(){
		
	}
	
	public DebitCardDTO(String debitCardNumber,int cvvNum,String expiryMonth,String expiryYear,BankAccountDTO account) {
		super(debitCardNumber,account, "DD");
		this.expiryMonth = expiryMonth;
		this.expiryYear = expiryYear;
		this.cvvNumber = cvvNum;
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

	
}
