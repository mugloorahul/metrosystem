package com.metrosystem.travel.user;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.metrosystem.beans.MetroStation;
import com.metrosystem.beans.MetroTrain;
import com.metrosystem.beans.MetroUser;
import com.metrosystem.beans.Route;
import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.travel.monitor.TrainMonitor;

public class UserJourney implements Runnable,Comparable<UserJourney>{

	private MetroStation sourceStation;
	private MetroStation destinationStation;
	private MetroUser user;
	private Date startTime;
	private String userTravelLog;
	
	
	public UserJourney(MetroStation source, MetroStation destination, MetroUser user,Date startTime) 
	throws IOException{
		this.sourceStation=source;
		this.destinationStation=destination;
		this.user=user;
		this.startTime = startTime;
		this.userTravelLog = "User Travel: " + user.getName() + " from " + sourceStation.getName() + " to " + destinationStation.getName();
		
		
		user.writeLog(userTravelLog + " to be started at " + startTime);
	}
	
	public MetroUser getUser(){
		return this.user;
	}
	
	private void repeatTravelProcess() throws MetroSystemException{
		// Get the train to be boarded
		
		user.writeLog(userTravelLog + " inside method repeatTravelProcess.");
		TrainMonitor monitor = findRightTrainMonitor();
		boardTrain(monitor);
		keepTravelling(monitor);
		alightTrain(monitor);
		user.writeLog(userTravelLog + " exiting method repeatTravelProcess.");
	}
	
	@Override
	public void run() {
		user.writeLog("Current thread: " + Thread.currentThread().getName());
		user.writeLog(userTravelLog + " started at time " + new Date() + ".");
		boolean swipeInFlag = swipeIn(sourceStation);
		while (true) {
			try {
				// System.out.println("Running user travel thread");
				// First swipe in
				if (swipeInFlag) {
					repeatTravelProcess();
					swipeOut(destinationStation);
				}
				user.writeLog(userTravelLog + " finished at time " + new Date() + ".");
				user.writeLog("Current thread: " + Thread.currentThread().getName());
				user.closeLogFile();
				return;
			} catch (MetroSystemException e) {
				//e.printStackTrace();
				e.printStackTrace(user.getUserLog());
			} catch (Exception e) {
				//e.printStackTrace();
				e.printStackTrace(user.getUserLog());
				//user.writeLog(e.getMessage());
				//throw new RuntimeException(e);
			}
		}
		
	}
	
	
	
	private void boardTrain(TrainMonitor monitor) throws MetroSystemException{
		user.writeLog(userTravelLog + " inside method boardTrain.");
		if(monitor.matchToCurrentStation(sourceStation)){
			throw new MetroSystemException("Unable to board the train");
		}
		MetroTrain train = monitor.getTrain();
		train.board(user);
		user.writeLog(userTravelLog + " boarded train " + train.getTrainName() + " at station " + 
				sourceStation.getName() + " at time " + new Date() + ".");
		/*System.out.printf("User %s boarded train %s at station %s at time %s\n",user.getName(),train.getTrainName(),
				           monitor.getCurrentStation().getName(), new Date());*/
		user.writeLog(userTravelLog + " exiting method boardTrain.");
	}
	
	private void keepTravelling(TrainMonitor monitor){
		user.writeLog(userTravelLog + " inside method keepTravelling.");
		//MetroStation currenStation = null;
		try{
			user.writeLog(userTravelLog + " inside method keepTravelling looking for current station.");
			//currenStation = monitor.getCurrentStation();
			user.writeLog(userTravelLog + " inside method keepTravelling waiting for destination station to match.");
			monitor.matchToCurrentStation(destinationStation);
			user.writeLog(userTravelLog + " inside method keepTravelling matched destination station.");
			//user.writeLog(userTravelLog + " inside method keepTravelling found current station as: " + currenStation.getName());
			/*if(!currenStation.equals(destinationStation)){
				TimeUnit.SECONDS.sleep(60);
				keepTravelling(monitor);
			}*/
			
		 /*while((currenStation = monitor.getCurrentStation()) == null || !currenStation.equals(destinationStation)){
			
		  }*/
		 user.writeLog(userTravelLog + " exiting method keepTravelling.");
		}
		catch(Exception e){
			user.writeLog(userTravelLog + e.getMessage());
			//e.printStackTrace();
		}
		
	}
	
	private void alightTrain(TrainMonitor monitor) throws MetroSystemException{
		user.writeLog(userTravelLog + " inside method alightTrain.");
		if(monitor.matchToCurrentStation(destinationStation)){
			throw new MetroSystemException("Unable to get down from the train");
		}
		MetroTrain train = monitor.getTrain();
		train.alight(user);
		user.writeLog(userTravelLog + " alighted from train " + train.getTrainName() + " at station " + 
				destinationStation.getName() + " at time " + new Date() + ".");
		user.writeLog(userTravelLog + " exiting method alightTrain.");
		/*System.out.printf("User %s alighted from train %s at station %s at time %s\n",user.getName(),train.getTrainName(),
				           monitor.getCurrentStation().getName(), new Date());*/
	}
	
	private TrainMonitor findRightTrainMonitor(){
		user.writeLog(userTravelLog + " inside method findRightTrainMonitor.");
		//Get the train monitor for this station
		user.writeLog(userTravelLog + " inside method findRightTrainMonitor looking for monitors.");
		Collection<TrainMonitor> monitors = sourceStation.getMonitors();
		user.writeLog(userTravelLog + " inside method findRightTrainMonitor found monitors.");
		TrainMonitor monitor = null;
		/*while(monitors.size() == 0){
			synchronized (monitor) {
				try {
					monitors.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}*/
		
		while((monitor =isRightTrainFound(monitors)) == null){
			user.writeLog(userTravelLog + " inside method looking for source station monitors.");
			monitors = sourceStation.getMonitors();
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace(user.getUserLog());
			}
		}
		
		/*while(monitors == null || monitors.size() == 0 || (monitor =isRightTrainFound(monitors)) == null){
			monitors = sourceStation.getMonitors();
		}*/
		
		user.writeLog(userTravelLog + " exiting method findRightTrainMonitor.");
		
		return monitor;
	}
	
	private TrainMonitor isRightTrainFound(Collection<TrainMonitor> monitors){
		
		Iterator<TrainMonitor> iter = monitors.iterator();
		while(iter.hasNext()){
			TrainMonitor monitor = iter.next();
			// Get the monitor route and current station
			//MetroStation currentStation = monitor.getCurrentStation();
			monitor.matchToCurrentStation(sourceStation);
			Route route = monitor.getTrainRoute();
			/*if(currentStation != null && currentStation.equals(sourceStation) && route.isCorrectRoute(sourceStation, destinationStation)){
				return monitor;
			}*/
			if(route.isCorrectRoute(sourceStation, destinationStation)){
				return monitor;
			}
		}
		
		return null;
	}
	
	private boolean swipeIn(MetroStation station) {
		try {
			this.user.getSmartCard().swipeIn(station);
		} 
		catch (MetroSystemException e) {
			//e.printStackTrace();
			e.printStackTrace(user.getUserLog());
			return false;
		}
		
		return true;
	}
	
	private void swipeOut(MetroStation station) throws MetroSystemException {
		try {
			this.user.getSmartCard().swipeOut(station);
		} 
		catch (MetroSystemException e) {
			throw e;
		}
		
	}
	
	public Date getStartTime(){
		return this.startTime;
	}
	
	@Override
	public int compareTo(UserJourney otherUserTravel) {
		return this.startTime.compareTo(otherUserTravel.startTime);
	}

}
