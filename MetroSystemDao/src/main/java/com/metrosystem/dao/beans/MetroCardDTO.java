package com.metrosystem.dao.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;


@Entity
@Table(name="metro_card")
@SQLDelete(sql="UPDATE metro_card SET deleted = 'Y' WHERE card_id=?")
public class MetroCardDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cardId;
	private double balance=0;
	private MetroUserDTO user;
	private String deleted;
	private String cardNumber;
	
	/**
	 * Default constructor
	 */
	public MetroCardDTO(){
		
	}
	
	public MetroCardDTO(String cardNumber,double balance,MetroUserDTO user){
		this.cardNumber = cardNumber;
		this.balance=balance;
		this.user=user;
	}
	
	public MetroCardDTO(String cardNumber,MetroUserDTO user){
		this(cardNumber,0, user);
	}
	
	/**
	 * @return the cardId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="card_id")
	public int getCardId() {
		return cardId;
	}
	/**
	 * @param cardId the cardId to set
	 */
	public void setCardId(int cardId) {
		this.cardId = cardId;
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
	 * @return the deleted
	 */
	@Column(name="deleted",length=1,insertable=false,nullable=false)
	public String getDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the cardNumber
	 */
	@Column(name="card_number",length=30,nullable=false,unique=true)
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroCardDTO)){
			return false;
		}
		
		MetroCardDTO otherUser = (MetroCardDTO)object;
		
		return this.cardId==otherUser.cardId;
	}
	
	@Override
	public int hashCode(){
		return (this.cardId*37);
	}
}
