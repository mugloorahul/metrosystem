package com.metrosystem.beans;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.travel.train.TrainJourney;
import com.metrosystem.utils.Constants;

public class MetroTrain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int trainNumber;
	private String trainName;
	private Route route;
	private PrintWriter log;
	private TrainJourney currentTrainJourney;
	
	
	public MetroTrain(int number,String name) throws IOException{
		this.trainNumber = number;
		this.trainName = name;
		//initiateLog();
	}
	
	public MetroTrain(int number,String name,Route route) throws IOException{
		this(number,name);
		this.attachRoute(route);
	}
	
	
	public int getTrainNumber(){
		return this.trainNumber;
	}
	
	public String getTrainName(){
		return this.trainName;
	}
	
	public void setTrainName(String name){
		this.trainName = name;
	}
	
	public Route getRoute(){
		return this.route;
	}
	
	public void setTrainJourney(TrainJourney journey){
		this.currentTrainJourney = journey;
	}
	
	public PrintWriter getUserLog(){
		
		return this.log;
	}
	
	
	public void initiateLog() throws IOException{
		String fileName = Constants.TRAIN_LOG_DIRECTORY+ File.separator + this.trainName+".log";
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

	
	public void board(MetroUser user) throws MetroSystemException{
		if(currentTrainJourney == null){
			throw new MetroSystemException("No active journey for this train");
		}
		currentTrainJourney.addTraveller(user);
	}
	
	
	public void alight(MetroUser user) throws MetroSystemException{
		if(currentTrainJourney == null){
			throw new MetroSystemException("No active journey for this train");
		}
		currentTrainJourney.removeTraveller(user);
	}
	
	public void attachRoute(Route route){
		this.route = route;
		this.route.addTrainToRoute(this);
	}
		

	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroTrain)){
			return false;
		}
		
		MetroTrain otherUser = (MetroTrain)object;
		
		return this.trainNumber==otherUser.trainNumber;
	}
	
	@Override
	public int hashCode(){
		return (this.trainNumber*37);
	}
}
