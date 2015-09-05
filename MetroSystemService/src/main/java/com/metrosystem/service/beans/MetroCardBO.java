package com.metrosystem.service.beans;

public class MetroCardBO {

	private int cardId;
	private double balance=0;
	private MetroUserBO user;
	private String cardNumber;
	
	/**
	 * Default constructor
	 */
	public MetroCardBO(){
		
	}

	public MetroCardBO(String cardNumber,double balance, MetroUserBO user){
		this.balance = balance;
		this.user = user;
		this.cardNumber = cardNumber;
	}
	
	public MetroCardBO(int cardId, double balance, MetroUserBO user) {
		this.cardId = cardId;
		this.balance = balance;
		this.user = user;
	}

	/**
	 * @return the cardId
	 */
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
	public MetroUserBO getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(MetroUserBO user) {
		this.user = user;
	}
	
	/**
	 * @return the cardNumber
	 */
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
		
		if(!(object instanceof MetroCardBO)){
			return false;
		}
		
		MetroCardBO otherUser = (MetroCardBO)object;
		
		return this.cardId==otherUser.cardId;
	}
	
	@Override
	public int hashCode(){
		return (this.cardId*37);
	}
}
