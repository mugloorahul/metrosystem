package com.metrosystem.dao.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name="payment_method")
@Inheritance(strategy=InheritanceType.JOINED)
@SQLDelete(sql="UPDATE payment_method SET deleted = 'Y' WHERE id=?")
public abstract class PaymentMethodDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	protected BankAccountDTO account;
	protected String paymentType;
	protected String paymentId;
	private String deleted;
	
	/**
	 * Default constructor
	 */
	public PaymentMethodDTO(){
		
	}
	
	public PaymentMethodDTO(String paymentId,BankAccountDTO account,String paymentType) {
		this.account = account;
		this.paymentType = paymentType;
		this.paymentId=paymentId;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
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
	@ManyToOne
	@JoinColumn(name="bank_account_id",nullable=false)
	@Where(clause="deleted <> 'Y'")
	public BankAccountDTO getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(BankAccountDTO account) {
		this.account = account;
	}

	/**
	 * @return the paymentType
	 */
	@Column(name="payment_type",length=4,nullable=false)
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
	@Column(name="pay_method_id",length=30,nullable=false,unique=true)
	public String getPaymentId() {
		return paymentId;
	}

	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	
	/**
	 * @return the deleted
	 */
	@Column(name="deleted",length=1,nullable=false,insertable=false)
	public String getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof PaymentMethodDTO)){
			return false;
		}
		
		PaymentMethodDTO other = (PaymentMethodDTO)obj;
		
		return this.paymentId==other.paymentId;
	}
	
	@Override
	public int hashCode(){
		return this.paymentId.hashCode();
	}
	
}
