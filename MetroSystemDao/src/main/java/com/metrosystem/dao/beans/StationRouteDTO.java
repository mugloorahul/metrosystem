package com.metrosystem.dao.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Check;

@Entity
@Table(name="stations_routes",
uniqueConstraints={@UniqueConstraint(columnNames={"route_id","station_id"})})
@Check(constraints="sequence > 0")
public class StationRouteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int sequence;
	private MetroStationDTO station;
	private RouteDTO route;
	
	public StationRouteDTO(){
		
	}
	
	public StationRouteDTO(int sequence,MetroStationDTO station,RouteDTO route){
		this.sequence=sequence;
		this.station=station;
		this.route=route;
	}
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the station
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="station_id",nullable=false)
	public MetroStationDTO getStation() {
		return station;
	}
	/**
	 * @param station the station to set
	 */
	public void setStation(MetroStationDTO station) {
		this.station = station;
	}
	/**
	 * @return the route
	 */
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="route_id",nullable=false)
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
	 * @return the sequence
	 */
	@Column(name="sequence",nullable=false)
	public int getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	

}
