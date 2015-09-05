package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Entity
@Table(name="bank_account")
@SQLDelete(sql="UPDATE bank_account SET deleted='Y' where bank_account_id = ?")
public class BankAccountDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer accountId;
	private String accountNumber;
	private double balance=0.0;
	private MetroUserDTO user;
	private Set<PaymentMethodDTO> payMethods;
	private String deleted;
	
	/** Default constructor*/
	public BankAccountDTO(){
		
	}
	

	public BankAccountDTO(String accountNumber, double balance, MetroUserDTO user){
		this.accountNumber=accountNumber;
		this.balance=balance;
		this.user=user;
	}
	
	public BankAccountDTO(String accountNumber,MetroUserDTO user){
		this(accountNumber, 0, user);
	}
	
	/**
	 * @return the accountId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bank_account_id")
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the accountNumber
	 */
	@Column(name="account_number",length=20,nullable=false,unique=true)
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the balance
	 */
	@Column(name="balance",nullable=false,scale=12,precision=2)
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	/**
	 * @return the user
	 */
	@ManyToOne(optional=false,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	public MetroUserDTO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(MetroUserDTO user) {
		this.user = user;
	}
	
	/**
	 * @return the payMethods
	 */
	@OneToMany(mappedBy="account",cascade=CascadeType.ALL)
	@Where(clause="deleted <> 'Y'")
	public Set<PaymentMethodDTO> getPayMethods() {
		return payMethods;
	}

	/**
	 * @param payMethods the payMethods to set
	 */
	public void setPayMethods(Set<PaymentMethodDTO> payMethods) {
		this.payMethods = payMethods;
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
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof BankAccountDTO)){
			return false;
		}
		
		BankAccountDTO otherAccount = (BankAccountDTO)object;
		
		return this.accountNumber==otherAccount.accountNumber;
	}
	
	@Override
	public int hashCode(){
		return this.accountNumber.hashCode();
	}
	
	public void addPaymentMethod(PaymentMethodDTO payMethod){
		if(this.payMethods == null){
			payMethods = new HashSet<PaymentMethodDTO>();
		}
		
		payMethods.add(payMethod);
//		payMethod.setAccount(this);
	}
}
