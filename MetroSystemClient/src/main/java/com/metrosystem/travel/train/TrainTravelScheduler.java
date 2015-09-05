package com.metrosystem.travel.train;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.utils.Constants;

public class TrainTravelScheduler implements Runnable{

	private BlockingQueue<TrainJourney> scheduleQueue;
	private ScheduledExecutorService scheduler;
	private Date schedulerStartTime;
	private Date schedulerEndTime;
	
	public TrainTravelScheduler(Date schedulerStartTime,Date schedulerEndTime){
		this.scheduleQueue = new PriorityBlockingQueue<TrainJourney>();
		this.schedulerStartTime = schedulerStartTime;
		this.schedulerEndTime = schedulerEndTime;
	}
	

	@Override
	public void run() {
		System.out.println("Train Travel scheduler thread start time: "+ new Date());
		try{
			while(!shouldSchedulerStart()){}

			this.start();
			
			while(!shouldSchedulerStop()){
				TrainJourney trainTravel = scheduleQueue.take();
		        

				Date currentTime = new Date();
					/*scheduler.scheduleWithFixedDelay(trainTravel, 
		                    trainTravel.getStartTime().getTime()-schedulerStartTime.getTime(), 
		                    Constants.NEXT_TRAIN_DEPARTURE_DURATION, TimeUnit.MILLISECONDS);*/
			    scheduler.schedule(trainTravel, 
				    trainTravel.getStartTime().getTime()-currentTime.getTime(), TimeUnit.MILLISECONDS);
				
			}
			
			this.stop();
		}
		catch(Exception e){
			e.printStackTrace();
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
		
		scheduler = Executors.newScheduledThreadPool(Constants.DEFAULT_NO_OF_TRAIN_SCHEDULER_THREADS);
		System.out.printf("Train travel scheduler started at %s\n",new Date());
	}
	
	private void stop(){
		scheduler.shutdown();
		System.out.printf("Train travel scheduler stopped at %s\n",new Date());
	}
	
	public void scheduleTrain(TrainJourney trainTravel) throws MetroSystemException{
		try {
			scheduleQueue.put(trainTravel);
		} catch (InterruptedException e) {
			throw new MetroSystemException(e);
		}
	}

}
