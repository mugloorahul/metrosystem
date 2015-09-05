package com.metrosystem.beans;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.payment.bank.beans.BankAccount;
import com.metrosystem.payment.bank.exception.BankException;
import com.metrosystem.payment.beans.PaymentMethod;
import com.metrosystem.utils.Constants;

public class MetroUser implements Serializable{

	/**
	 *  */
	private static final long serialVersionUID = 1L;

	private int metroUserId;
	
	private String name;
	
	private BankAccount bankAccount;
	
	private MetroCard smartCard;
	
	private Set<PaymentMethod> paymentMethods;
	
	private PaymentMethod defaultPayMethod;
	
	private MetroStation lastSwippedInStation;
	
	private PrintWriter log;
	
	public PrintWriter getUserLog(){
		return this.log;
	}
	
	public MetroUser(int metroUserId,String name, BankAccount account) throws IOException{
		this(metroUserId,name);
		this.bankAccount = account;
	}
	
	public MetroUser(int metroUserId,String name, BankAccount account,Set<PaymentMethod> payMethods) throws IOException{
		this(metroUserId,name);
		this.bankAccount = account;
		this.paymentMethods = payMethods;
	}
	
	public void initiateLog() throws IOException{
		String fileName = Constants.USER_LOG_DIRECTORY+ File.separator + this.getName()+".log";
		File file = new File(fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		log= new PrintWriter(file);
	}
	
	public void writeLog(String logMessage){
		log.println(logMessage);
		log.flush();
	}
	
	
	
	public void closeLogFile(){
		if(log != null){
			log.flush();
			log.close();
		}

	}
	
	public MetroUser(int metroUserId, String name) throws IOException{
		this.metroUserId = metroUserId;
		this.name = name;
		initiateLog();
	}
	
	public String getName(){
		return this.name;		
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public BankAccount getBankAccount() throws BankException{
		if(this.bankAccount == null){
			throw new BankException("No bank account assigned.");
		}
		
		return this.bankAccount;
	}
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroUser)){
			return false;
		}
		
		MetroUser otherUser = (MetroUser)object;
		
		return this.metroUserId==otherUser.metroUserId;
	}
	
	@Override
	public int hashCode(){
		return (this.metroUserId*37);
	}
	
	public void provideSmartCard(MetroCard smartCard){
		this.smartCard = smartCard;
	}
	
	public MetroCard getSmartCard(){
		return this.smartCard;
	}
	
	public void addPaymentMethod(PaymentMethod payMethod){
		if(this.paymentMethods == null){
			paymentMethods = new HashSet<PaymentMethod>();
		}
		
		paymentMethods.add(payMethod);
	}
	
	public Set<PaymentMethod> getPaymentMethods(){
		return this.paymentMethods;
	}
	
	public PaymentMethod getDefaultPaymentMethd(){
		return this.defaultPayMethod;
	}
	
	public void setDefaultPaymentMethod(PaymentMethod payMethod) throws MetroSystemException{
		
		if(!checkPayMethodExists(payMethod)){
			throw new MetroSystemException("Invalid payment method specified.");
		}
		
		this.defaultPayMethod = payMethod;
		
		
	}
	
	public void setBankAccount(BankAccount account){
		this.bankAccount = account;
	}
	
	boolean checkPayMethodExists(PaymentMethod payMethod){
		
		if(this.paymentMethods.isEmpty()){
			return false;
		}
		
		for(PaymentMethod method: this.paymentMethods){
			if(method.equals(payMethod)){
				return true;
			}
		}
		
		return false;
	}
	
	public MetroStation getLastSwippedInStation(){
		return this.lastSwippedInStation;
	}
	
	public void setLastSwippedInStation(MetroStation station){
		this.lastSwippedInStation = station;
	}
}
