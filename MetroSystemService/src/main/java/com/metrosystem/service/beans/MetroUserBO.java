package com.metrosystem.service.beans;

import java.util.Set;

public class MetroUserBO {

	private int userId;
	private String uniqueIdentifier;
	private String name;
	private Set<BankAccountBO> bankAccounts;
	private Set<MetroCardBO> metroCards;
	private Set<UserJourneyBO> journeys;
	
	/**
	 * Default constructor
	 */
	public MetroUserBO(){
		
	}

	public MetroUserBO(int userId,String identifier, String name, Set<BankAccountBO> bankAccounts,
			Set<MetroCardBO>  metroCards, Set<UserJourneyBO> journeys) {
		super();
		this.userId = userId;
		this.uniqueIdentifier=identifier;
		this.name = name;
		this.bankAccounts = bankAccounts;
		this.metroCards = metroCards;
		this.journeys = journeys;
	}

	public MetroUserBO(String identifier, String name){
		this.uniqueIdentifier=identifier;
		this.name=name;
	}
	
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bankAccount
	 */
	public Set<BankAccountBO> getBankAccounts() {
		return bankAccounts;
	}

	/**
	 * @param bankAccount the bankAccount to set
	 */
	public void setBankAccounts(Set<BankAccountBO> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
	 * @return the metroCard
	 */
	public Set<MetroCardBO> getMetroCards() {
		return metroCards;
	}

	/**
	 * @param metroCard the metroCard to set
	 */
	public void setMetroCards(Set<MetroCardBO> metroCards) {
		this.metroCards = metroCards;
	}

	/**
	 * @return the journeys
	 */
	public Set<UserJourneyBO> getJourneys() {
		return journeys;
	}

	/**
	 * @param journeys the journeys to set
	 */
	public void setJourneys(Set<UserJourneyBO> journeys) {
		this.journeys = journeys;
	}
	
	/**
	 * @return the uniqueIdentifier
	 */
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	/**
	 * @param uniqueIdentifier the uniqueIdentifier to set
	 */
	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroUserBO)){
			return false;
		}
		
		MetroUserBO otherUser = (MetroUserBO)object;
		
		return this.userId==otherUser.userId;
	}
	
	@Override
	public int hashCode(){
		return (this.userId*37);
	}
}
