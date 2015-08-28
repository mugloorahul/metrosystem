package com.metrosystem.travel.user;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.utils.Constants;

public class UserTravelScheduler implements Runnable{

	private BlockingQueue<UserJourney> scheduleQueue;
	private ScheduledExecutorService scheduler;
	private Date schedulerStartTime;
	private Date schedulerEndTime;
	
	
	public UserTravelScheduler(Date schedulerStartTime,Date schedulerEndTime){
		this.scheduleQueue = new PriorityBlockingQueue<UserJourney>();
		this.schedulerStartTime = schedulerStartTime;
		this.schedulerEndTime = schedulerEndTime;
	}
	

	@Override
	public void run() {
		System.out.println("User Travel scheduler thread start time: "+ new Date());
		try {
			while (!shouldSchedulerStart()) {
			}

			this.start();

			while (!shouldSchedulerStop()) {
				UserJourney userTravel = scheduleQueue.take();
		        
				Date currentTime = new Date();
				/*System.out.printf("User travel start time: %s\n",userTravel.getStartTime());
				System.out.printf("User travel current time: %s\n",currentTime);
				System.out.printf("User travel difference : %d\n",userTravel.getStartTime()
						.getTime() - currentTime.getTime());*/
				userTravel.getUser().writeLog("Time before start: " + (userTravel.getStartTime()
						.getTime() - currentTime.getTime()));
				scheduler.schedule(userTravel, (userTravel.getStartTime()
						.getTime() - currentTime.getTime()), TimeUnit.MILLISECONDS);
			}

			this.stop();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	private boolean shouldSchedulerStop(){
		Date currentTime = new Date();
		if(currentTime.compareTo(schedulerEndTime) >= 0){
			return true;
		}
		
		return false;
	}
	
	private boolean shouldSchedulerStart(){
		Date currentTime = new Date();
		if(currentTime.compareTo(schedulerStartTime) < 0){
			return false;
		}
		
		return true;
	}
	
	private void start(){
		
		scheduler = Executors.newScheduledThreadPool(Constants.DEFAULT_NO_OF_USER_SCHEDULER_THREADS);
		System.out.printf("User Travel scheduler started at %s\n",new Date());
	}
	
	private void stop(){
		scheduler.shutdown();
		System.out.printf("User Travel scheduler stopped at %s\n",new Date());
	}
	
	public void scheduleUserTravel(UserJourney trainTravel) throws MetroSystemException{
		try {
			scheduleQueue.put(trainTravel);
		} catch (InterruptedException e) {
			throw new MetroSystemException(e);
		}
	}

}
