package com.metrosystem.dao.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="net_banking_payment")
@PrimaryKeyJoinColumn(name="pay_method_id")
public class NetBankingDTO extends PaymentMethodDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String password;
	
    public NetBankingDTO(){
		
	}
	
	public NetBankingDTO(String customerId,String password,BankAccountDTO account) {
		super(customerId,account, "NB");
		this.password = password;
	}

	/**
	 * @return the password
	 */
	@Column(name="password",length=30,nullable=false)
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
}
