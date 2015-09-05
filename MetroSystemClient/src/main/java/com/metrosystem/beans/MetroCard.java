package com.metrosystem.beans;

import java.awt.Point;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.payment.beans.PaymentMethod;
import com.metrosystem.payment.exception.PaymentException;
import com.metrosystem.utils.Constants;

public class MetroCard implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int smartCardId;
	private MetroUser cardUser;
	private double cardBalance;
	
	public MetroCard(int smartCardId, MetroUser user){
		this.smartCardId = smartCardId;
		this.cardUser = user;
		cardUser.provideSmartCard(this);
		this.cardBalance = 0;
	}
	
	
	public void rechargeSmartCard(double amount, PaymentMethod payMethod,Map<String, Object> payMethodParams)
	throws MetroSystemException{
		
		if(amount <= 0){
			throw new IllegalArgumentException("Invalid Recharge amount specified.");
		}
		
		//Check if the payMethod is right or now
		if(!cardUser.checkPayMethodExists(payMethod)){
			throw new MetroSystemException("Invalid payment method specified");
		}
		
		try {
			payMethod.pay(amount, payMethodParams);
			this.cardBalance += amount;
			cardUser.writeLog("Smart card: successfully rechared with amount: " + amount);
			cardUser.writeLog("Smart card: available balance: " + cardBalance);
		} catch (PaymentException e) {
			throw new MetroSystemException(e);
		}
	}
	
	public void deductAmount(double amount) throws MetroSystemException{
		if(amount <= 0){
			throw new MetroSystemException("Not enough smart card balance");
		}
		
		this.cardBalance -= amount;
	}
	
	public MetroUser getCardUser(){
		return this.cardUser;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroCard)){
			return false;
		}
		
		MetroCard otherUser = (MetroCard)object;
		
		return this.smartCardId==otherUser.smartCardId;
	}
	
	@Override
	public int hashCode(){
		return (this.smartCardId*37);
	}
	
	public void swipeIn(MetroStation swipeInStation) throws MetroSystemException{
		if(this.cardBalance <= Constants.MINIMUM_SMART_CARD_BALANCE){
			throw new MetroSystemException("Your balance is insufficient.");
		}
		
		Date swipeInTime = new Date();
		cardUser.writeLog("User " + cardUser.getName() + " swiped in at station " + swipeInStation.getName() + " at time " + swipeInTime + ".");
		//System.out.printf("User %s swiped in at station %s at time %s\n",cardUser.getName(),swipeInStation.getName(),swipeInTime);
	    this.cardUser.setLastSwippedInStation(swipeInStation);
	}
	
	public void swipeOut(MetroStation swipeOutStation) throws MetroSystemException{
		
		Date swipeOutTime = new Date();
		cardUser.writeLog("User " + cardUser.getName() + " swiped out at station " + swipeOutStation.getName() + " at time " + swipeOutTime + ".");
		//System.out.printf("User %s swiped out at station %s at time %s\n",cardUser.getName(),swipeOutStation.getName(),swipeOutTime);
	  
		double distanceTravelled = calculateTravelDistance(this.cardUser.getLastSwippedInStation(),swipeOutStation);
		cardUser.writeLog("Smart card: Distance travelled from " + 
                cardUser.getLastSwippedInStation().getName() + " to " + swipeOutStation.getName() + ": " + distanceTravelled);
		double amountCharged = distanceTravelled * 15;
		this.cardBalance -= amountCharged;
		cardUser.writeLog("Smart card: Amount charged for travel from " + 
		                  cardUser.getLastSwippedInStation().getName() + " to " + swipeOutStation.getName() + ": " + amountCharged);
		cardUser.writeLog("Smart card: available balance: " + cardBalance);
	}
	
	private double calculateTravelDistance(MetroStation swipeInStation, MetroStation swipeOutStation){
		Point p1 = swipeInStation.getLocation();
		Point p2 = swipeOutStation.getLocation();
		
		return Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
	}
	
}
