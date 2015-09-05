package com.metrosystem.simulate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.metrosystem.beans.MetroStation;
import com.metrosystem.beans.MetroTrain;
import com.metrosystem.beans.MetroUser;
import com.metrosystem.beans.Route;
import com.metrosystem.beans.MetroCard;
import com.metrosystem.exception.MetroSystemException;
import com.metrosystem.payment.bank.beans.BankAccount;
import com.metrosystem.payment.beans.CreditCard;
import com.metrosystem.payment.beans.PaymentMethod;
import com.metrosystem.travel.train.TrainJourney;
import com.metrosystem.travel.train.TrainTravelScheduler;
import com.metrosystem.travel.user.UserJourney;
import com.metrosystem.travel.user.UserTravelScheduler;

public class MetroSystemSimulator {
	
	private final static MetroSystemSimulator simulator = new MetroSystemSimulator();
	private final static TrainTravelScheduler scheduler = simulator.simulateTrainScheduler();;
	private List<MetroTrain> trains;
	
	public static void main(String[] args) throws Exception{
		
		List<MetroStation> stations = simulator.simulateStations();
		List<Route> routes = simulator.simulateRoutes(stations);
		simulator.trains = simulator.simulateTrains(routes);
		List<MetroUser> users = simulator.simulateUsers();
		
		UserTravelScheduler userScheduler = simulator.simulateUserTravelScheduler();
		
		simulator.simulateTrainTravels(simulator.trains, scheduler);
		
		simulator.simulateUserTravels(users, stations, userScheduler);
		
	}

	
	
	private TrainTravelScheduler simulateTrainScheduler(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 120);
		Date endTime = calendar.getTime();
		
		TrainTravelScheduler trainScheduler = new TrainTravelScheduler(startTime, endTime);
		
		Thread schedulerThread = new Thread(trainScheduler);
		schedulerThread.start();
		
		return trainScheduler;
	}
	
	private UserTravelScheduler simulateUserTravelScheduler(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		Date startTime = calendar.getTime();
		calendar.add(Calendar.MINUTE, 120);
		Date endTime = calendar.getTime();
		
		UserTravelScheduler userScheduler = new UserTravelScheduler(startTime, endTime);
		
		Thread schedulerThread = new Thread(userScheduler);
		schedulerThread.start();
		
		return userScheduler;
	}
	
	private void simulateTrainTravels(List<MetroTrain> trains,TrainTravelScheduler scheduler) throws MetroSystemException, IOException{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		for(int i =0; i < 10 ;i++){
			calendar.add(Calendar.MINUTE, 1);
			Date startTime = calendar.getTime();
			TrainJourney travel = new TrainJourney(trains.get(i*2), startTime);
			scheduler.scheduleTrain(travel);
		}
	}
	
	
	private void simulateUserTravels(List<MetroUser> users,List<MetroStation> stations,UserTravelScheduler scheduler) throws Exception{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 1);
		//Up route users
		Random random = new Random();
		//Up route users 10 for each train travel
		for(int i =0; i < 10; i++){
			calendar.add(Calendar.SECOND, 60);
			for(int j = i * 10; j < (i*10)+10; j++){
				Date startTime = calendar.getTime();
				int sourceIndex = i * 10 + random.nextInt(10);
				int rem = sourceIndex%10;
				int destinationIndex = sourceIndex+random.nextInt(10-rem);
				if(destinationIndex == sourceIndex){
					destinationIndex++;
					if(destinationIndex >= ((i+1)*10)){
						destinationIndex--;
					}
				}
				MetroStation source = stations.get(sourceIndex);
				MetroStation destination = stations.get(destinationIndex);
				//System.out.printf("User %s has to travel from %s to %s \n",users.get(i).getName(),source.getName(),destination.getName());
				UserJourney travel = new UserJourney(source, destination, users.get(j), startTime);
				scheduler.scheduleUserTravel(travel);
			}
		}
		//calendar.add(Calendar.MINUTE, 1);
		/*MetroStation source = stations.get(1);
		MetroStation destination = stations.get(6);
		UserTravel travel = new UserTravel(source, destination, users.get(0), calendar.getTime());
		scheduler.scheduleUserTravel(travel);*/
	}
	
	private List<MetroStation> simulateStations(){
		List<MetroStation> stations = new ArrayList<MetroStation>();
		
		for(int i =0; i < 100;i++){
			MetroStation station = new MetroStation(i, "Station"+i, i*10, i*10);
			stations.add(station);
		}
		
		return stations;
	}
	
	private List<Route> simulateRoutes(List<MetroStation> stations){
		
		List<Route> routes = new ArrayList<Route>();
		for(int i =0; i < 10; i++){
			Route upRoute = new Route(i, "Route-"+i+"-Up");
			for(int j=i*10; j < i*10+10; j++){
				upRoute.addStation(stations.get(j));
			}
			//Create a new route for the down way
			Route downRoute = new Route(i, "Route-"+i+"-Down");
			for(int j=i*10+9; j >=i*10; j--){
				downRoute.addStation(stations.get(j));
			}
			routes.add(upRoute);
			routes.add(downRoute);
		}
		
		return routes;
	}
	
	private List<MetroTrain> simulateTrains(List<Route> routes) throws IOException{
		List<MetroTrain> trains = new ArrayList<MetroTrain>();
		for(int i =0; i < routes.size(); i++){
			MetroTrain train = new MetroTrain(i, "Train-"+i, routes.get(i));
			trains.add(train);
		}
		
		return trains;
	}
	
	public MetroTrain toogleTrain(int trainNumber){
		// if even means up route
		if(trainNumber%2 == 0){
			//Return down route train
			return this.trains.get(trainNumber+1);
		}
		else{
			return this.trains.get(trainNumber-1);
		}
	}
	
	private List<MetroUser> simulateUsers() throws Exception{
		List<MetroUser> users = new ArrayList<MetroUser>();
		for(int i =0; i < 100;i++){
			MetroUser user = new MetroUser(i, "User-"+i);
			BankAccount account = new BankAccount(i+"", user);
			account.addAmount(10000);
			PaymentMethod method = new CreditCard(i+"", i, "12", "2020", 20000, account);
			user.addPaymentMethod(method);
			MetroCard smartCard = new MetroCard(i, user);
			Map<String, Object> payMethodParams = new HashMap<String,Object>();
			payMethodParams.put("ccNum", i+"");
			payMethodParams.put("expMonth", "12");
			payMethodParams.put("expYear", "2020");
			smartCard.rechargeSmartCard(500, method, payMethodParams);
			users.add(user);
		}
		
		return users;
	}
	
	public static TrainTravelScheduler getScheduler(){
		return scheduler;
	}
	
	public static MetroSystemSimulator getSimulator(){
		return simulator;
	}
}
