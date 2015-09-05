package com.metrosystem.travel.train;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.metrosystem.beans.MetroStation;
import com.metrosystem.beans.MetroTrain;
import com.metrosystem.beans.MetroUser;
import com.metrosystem.beans.Route;
import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.simulate.MetroSystemSimulator;
import com.metrosystem.travel.monitor.TrainMonitor;
import com.metrosystem.utils.Constants;

public class TrainJourney implements Runnable,Comparable<TrainJourney> {

	private MetroTrain train;
	private MetroStation start;
	private MetroStation end;
	private Date startTime;
	//private List<MetroStation> stations;
	private TrainMonitor monitor;
	private String trainTravelLog;
	private List<MetroUser> travellers;
	
	public TrainJourney(MetroTrain train,Date startTime) throws MetroSystemException, IOException{
		this.train = train;
		Route trainRoute = train.getRoute();
		//this.stations = trainRoute.getStations();
		this.start = trainRoute.getStartStation();
		this.end = trainRoute.getEndStation();
		this.startTime=startTime;
		this.travellers = new ArrayList<MetroUser>();
		
		
		this.trainTravelLog = "Train Travel: " + train.getTrainName() + " from " + start.getName() + " to " + end.getName();
		train.initiateLog();
		train.writeLog(trainTravelLog + " to be started at " + startTime);
		
		monitor = new TrainMonitor(train);
		monitor.setTrainArrival(start, new Date(), trainRoute.getNextStation(start), startTime);
		
	}
	
	

	@Override
	public int compareTo(TrainJourney otherTrainTravel) {
		/**if(otherTrainTravel.train.equals(this.train)){
			return this.startTime.compareTo(otherTrainTravel.startTime);
		}*/
		
		return this.startTime.compareTo(otherTrainTravel.startTime);
	}

	
	public Date getStartTime(){
		return this.startTime;
	}

	public void finishTrainJourney(MetroStation lastStation) throws MetroSystemException{
		
		train.writeLog(trainTravelLog + " inside method finishTrainJourney.");
		Date endTime = new Date();
		/*System.out.printf(
				"Train %s (%s - %s) finished its journey at station %s at time %s\n",
				train.getTrainName(), start.getName(), end.getName(),
				lastStation.getName(),endTime);*/
				
		train.writeLog(trainTravelLog + " finished its journey at time " + endTime + ".");		
				
		monitor.setTrainArrival(lastStation, endTime, null, null);
		
		train.writeLog(trainTravelLog + " exiting method finishTrainJourney.");
		monitor = null;
		this.train.setTrainJourney(null);

	}
	
	public void scheduleToggledRouteTravel() throws MetroSystemException, IOException{
		//train.writeLog(trainTravelLog + " inside method scheduleToggledRouteTravel.");
		MetroTrain train = MetroSystemSimulator.getSimulator().toogleTrain(this.train.getTrainNumber());
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, (int)Constants.NEXT_TRAIN_DEPARTURE_DURATION_IN_SECONDS);
		TrainJourney trainTravel = new TrainJourney(train, calendar.getTime());
		MetroSystemSimulator.getScheduler().scheduleTrain(trainTravel);
		//train.writeLog(trainTravelLog + " exiting method scheduleToggledRouteTravel.");
	}

	@Override
	public void run(){
		train.writeLog(trainTravelLog + " started at time " + new Date() + ".");
		this.train.setTrainJourney(this);
		try {
			MetroStation current = start;
			MetroStation next = train.getRoute().getNextStation(current);
			//MetroStation next = stations.get(++stationCounter);
			/*System.out.printf(
					"Train %s (%s - %s) started its journey at time %s\n",
					train.getTrainName(), start.getName(), end.getName(),
					new Date());*/
			
			train.writeLog(trainTravelLog + " started its journey at time " + new Date() + ".");

			//Till the end station is not reached keep on travelling
			while(!current.equals(end)){
				travel(current, next);
				//Date arrivalTime = new Date();
				current = next;
				next = 	train.getRoute().getNextStation(current);
				if(next == null){
					next = current;
				}
				//next = stations.get(++stationCounter);
                arrive(current, next);
				
                if(!current.equals(end)){
    				/*while(!readyForDeparture(arrivalTime)){}*/
                	TimeUnit.SECONDS.sleep(Constants.TRAIN_ARRIVAL_DEPARTURE_DURATION_IN_SECONDS);
    				depart(current, next);
                }


			}
			
			finishTrainJourney(current);
			scheduleToggledRouteTravel();
			train.writeLog(trainTravelLog + " finished at time " + new Date() + ".");
			train.closeLogFile();
		}
		catch(Exception e){
			e.printStackTrace(train.getUserLog());
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void arrive(MetroStation arrivalStation,MetroStation nextStation) throws MetroSystemException{
		train.writeLog(trainTravelLog + " inside method arrive.");
		Date arrivalTime = new Date();
		monitor.setTrainArrival(arrivalStation, arrivalTime, nextStation, getScheduledDepartureTime(arrivalTime));
		/*System.out
		 .printf("Train %s (%s - %s) arrived at station %s at time %s\n",
				 train.getTrainName(), start.getName(),
				 end.getName(), arrivalStation.getName(), arrivalTime);*/
		train.writeLog(trainTravelLog + " arrived at station " + arrivalStation.getName() + " at time " + arrivalTime + ".");
		train.writeLog(trainTravelLog + " exiting method arrive.");
	}
	
	private void depart(MetroStation departStation,MetroStation nextStation) throws MetroSystemException{
		train.writeLog(trainTravelLog + " inside method depart.");
		Date departTime = new Date();
		monitor.setTrainDeparture(departTime);
		/*System.out
		 .printf("Train %s (%s - %s) departed from station %s to station %s at time %s\n",
				 train.getTrainName(), start.getName(),
				 end.getName(),departStation.getName(), nextStation.getName(), departTime);*/
		train.writeLog(trainTravelLog + " departed from station " + departStation.getName() + " at time " + departTime + ".");
		train.writeLog(trainTravelLog + " exiting method depart.");
	}
	
	private void travel(MetroStation currentStation,MetroStation nextStation) throws InterruptedException, MetroSystemException{
		train.writeLog(trainTravelLog + " inside method travel.");
		// Sleep for random time no of seconds
		long duration = 40 + (long) (Math.random() * 20);
	     monitor.setNextStationExpectedArrivalTime(new Date(duration));

	     train.writeLog(trainTravelLog + " travelling from " + currentStation.getName() + " to " + nextStation.getName() + " duration: " + duration + " seconds.");
		/*System.out
		 .printf("Train %s (%s - %s) travelling from station %s to station %s for %d seconds\n",
				 train.getTrainName(), start.getName(),
				 end.getName(),currentStation.getName(), nextStation.getName(), duration);*/
		TimeUnit.SECONDS.sleep(duration);
		train.writeLog(trainTravelLog + " exiting method travel.");
	}
	
	/*private boolean readyForDeparture(Date arrivalTime){
		
		
		if(arrivalTime == null){
			return true;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(arrivalTime);
		calendar.add(Calendar.SECOND, (int)Constants.TRAIN_ARRIVAL_DEPARTURE_DURATION_IN_SECONDS);
		
		Date departTime = calendar.getTime();
		Date currentTime = new Date();
		
		if(currentTime.compareTo(departTime) >= 0){
			return true;
		}
		
		return false;
	}*/
	
	private Date getScheduledDepartureTime(Date arrivalTime){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(arrivalTime);
		calendar.add(Calendar.SECOND, (int)Constants.TRAIN_ARRIVAL_DEPARTURE_DURATION_IN_SECONDS);
		
		Date departTime = calendar.getTime();
		
		return departTime;
	}


	public void addTraveller(MetroUser user){
	      travellers.add(user);
	}
	
	
	public void removeTraveller(MetroUser user){
		travellers.remove(user);
	}

}
