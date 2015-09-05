package com.metrosystem.payment.bank.beans;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.metrosystem.beans.MetroUser;
import com.metrosystem.payment.bank.exception.BankException;

public class BankAccount {

	private String accountNumber;
	private MetroUser user;
	private double balance;
	private Lock accountLock ;
	
	public BankAccount(String accountNumber, MetroUser user, double balance){
		this.accountNumber = accountNumber;
		this.user = user;
		this.balance = balance;
		this.user.setBankAccount(this);
		accountLock = new ReentrantLock();
	}
	
	public BankAccount(String accountNumber, MetroUser user){
		this(accountNumber,user,0);
	}
	
	public MetroUser getAccountUser(){
		return this.user;
	}
	
	public double getAccountBalance(){
		return this.balance;
	}
	
	public void addAmount(double amount) throws BankException{
		
		try{
			accountLock.lock();
			if(amount < 0){
				throw new IllegalArgumentException("Invalid amount specified");
			}
			
			this.balance += amount;
		}
		finally{
			accountLock.unlock();
		}

	}
	
	public void subtractAmount(double amount) throws BankException{
		try{
			accountLock.lock();
			if(amount < 0){
				throw new IllegalArgumentException("Invalid amount specified");
			}
			
			if(this.balance <= 0){
				throw new BankException("Not enough balance");
			}
			
			this.balance -= amount;
		}
		finally{
			accountLock.unlock();
		}

	}
	
	public String getAccountNumber(){
		return this.accountNumber;
	}
	
	
	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroUser)){
			return false;
		}
		
		BankAccount otherAccount = (BankAccount)object;
		
		return this.accountNumber==otherAccount.accountNumber;
	}
	
	@Override
	public int hashCode(){
		return this.accountNumber.hashCode();
	}
}
