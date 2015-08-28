package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name="metro_user")
public class MetroUserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String uniqueIdentifier;
	private String name;
	private Set<BankAccountDTO> bankAccounts;
	private Set<MetroCardDTO> metroCards;
	private Set<UserJourneyDTO> journeys;
	
	/**
	 * Default constructor
	 */
	public MetroUserDTO(){
		
	}
	
	public MetroUserDTO(String identifier,String name){
		this.name=name;
		this.uniqueIdentifier=identifier;
	}
		
	
	/**
	 * @return the userId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
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
	@Column(name="name",nullable=false,length=100)
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
	@OneToMany(mappedBy="user")
	@Where(clause="deleted <> 'Y'")
	public Set<BankAccountDTO> getBankAccounts() {
		return bankAccounts;
	}
	/**
	 * @param bankAccount the bankAccount to set
	 */
	public void setBankAccounts(Set<BankAccountDTO>  bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	/**
	 * @return the metroCard
	 */
	@OneToMany(mappedBy="user")
	@Where(clause="deleted <> 'Y'")
	public Set<MetroCardDTO> getMetroCards() {
		return metroCards;
	}
	/**
	 * @param metroCard the metroCard to set
	 */
	public void setMetroCards(Set<MetroCardDTO> metroCards) {
		this.metroCards = metroCards;
	}
	
	/**
	 * @return the journeys
	 */
	@OneToMany(mappedBy="user")
	public Set<UserJourneyDTO> getJourneys() {
		return journeys;
	}

	/**
	 * @param journeys the journeys to set
	 */
	public void setJourneys(Set<UserJourneyDTO> journeys) {
		this.journeys = journeys;
	}

	/**
	 * @return the uniqueIdentifier
	 */
	@Column(name="unique_id",length=30,nullable=false,unique=true)
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
		
		if(!(object instanceof MetroUserDTO)){
			return false;
		}
		
		MetroUserDTO otherUser = (MetroUserDTO)object;
		
		return this.userId==otherUser.userId;
	}
	
	@Override
	public int hashCode(){
		return (this.userId*37);
	}
}
