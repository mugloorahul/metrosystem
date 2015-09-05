package com.metrosystem.dao.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="metro_train")
public class MetroTrainDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int trainNumber;
	private String name;
	private RouteDTO route;
	private Set<TrainJourneyDTO> trainJourneys;
	
	/**
	 * Default constructor
	 */
	public MetroTrainDTO(){
		
	}
	
	public MetroTrainDTO(int trainNumber,String name){
		this.trainNumber = trainNumber;
		this.name=name;
	}
	
	public MetroTrainDTO(int trainNumber,String name,RouteDTO route){
		this(trainNumber,name);
		this.attachRoute(route);
		if(route != null){
			route.attachTrain(this);
		}
		
	}
	
	/**
	 * @return the trainNumber
	 */
	@Id
	@Column(name="train_number")
	public int getTrainNumber() {
		return trainNumber;
	}
	/**
	 * @param trainNumber the trainNumber to set
	 */
	public void setTrainNumber(int trainNumber) {
		this.trainNumber = trainNumber;
	}
	/**
	 * @return the name
	 */
	@Column(name="name",length=100,nullable=false,unique=true)
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
	 * @return the route
	 */
	@ManyToOne(optional=true)
	@JoinColumn(name="route_id")
	public RouteDTO getRoute() {
		return route;
	}
	/**
	 * @param route the route to set
	 */
	public void setRoute(RouteDTO route) {
		this.route = route;
	}
	
	/**
	 * @return the trainJourneys
	 */
	@OneToMany(mappedBy="train",fetch=FetchType.LAZY)
	public Set<TrainJourneyDTO> getTrainJourneys() {
		return trainJourneys;
	}

	/**
	 * @param trainJourneys the trainJourneys to set
	 */
	public void setTrainJourneys(Set<TrainJourneyDTO> trainJourneys) {
		this.trainJourneys = trainJourneys;
	}

	@Override
	public boolean equals(Object object){
		if(object == null){
			return false;
		}
		
		if(!(object instanceof MetroTrainDTO)){
			return false;
		}
		
		MetroTrainDTO otherUser = (MetroTrainDTO)object;
		
		return this.trainNumber==otherUser.trainNumber;
	}
	
	@Override
	public int hashCode(){
		return (this.trainNumber*37);
	}
	
	public void attachRoute(RouteDTO route){
		this.route = route;
	}
}
