package com.metrosystem.service.beans;



public abstract class PaymentMethodBO {

	private Integer id;
	protected BankAccountBO account;
	protected String paymentType;
	protected String paymentId;
	
	public PaymentMethodBO(String paymentId,BankAccountBO account,String paymentType) {
		this.account = account;
		this.paymentType = paymentType;
		this.paymentId = paymentId;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the account
	 */
	public BankAccountBO getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(BankAccountBO account) {
		this.account = account;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof PaymentMethodBO)){
			return false;
		}
		
		PaymentMethodBO other = (PaymentMethodBO)obj;
		
		return this.paymentId==other.paymentId;
	}
	
	@Override
	public int hashCode(){
		return this.paymentId.hashCode();
	}
}
