package com.metrosystem.dao.beans;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="metro_station")
public class MetroStationDTO implements Serializable{

	/**
	 * Default serializable id
	 */
	private static final long serialVersionUID = 1L;
	private int stationId;
	private String name;
	private Set<StationRouteDTO> stationRoutes;
	private GeoLocationDTO location;
	private Set<UserJourneyDTO> boardedTravellers;
	private Set<UserJourneyDTO> alightedTravellers;
	private Set<TrainJourneyMonitorDTO> monitors;
	
	/**
	 * Default constructor
	 */
	public MetroStationDTO(){
		
	}
	
	public MetroStationDTO(String name, String latitude, String longitude){
		this.name=name;
		this.location=new GeoLocationDTO(latitude, longitude);
	}
	
	public MetroStationDTO(String name, String latitude, String longitude,Set<StationRouteDTO> stationRoutes){
		this(name, latitude,longitude);
		this.stationRoutes=stationRoutes;
	}
	
	
	/**
	 * @return the stationId
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="station_id")
	public int getStationId() {
		return stationId;
	}
	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(int stationId) {
		this.stationId = stationId;
	}
	
	/**
	 * @return the name
	 */
	@Column(name="name",length=50,nullable=false,unique=true)
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
	 * @return the location
	 */
	@Embedded
	public GeoLocationDTO getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(GeoLocationDTO location) {
		this.location = location;
	}
	
	/**
	 * @return the boardedTravellers
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="sourceStation")
	public Set<UserJourneyDTO> getBoardedTravellers() {
		return boardedTravellers;
	}

	/**
	 * @param boardedTravellers the boardedTravellers to set
	 */
	public void setBoardedTravellers(Set<UserJourneyDTO> boardedTravellers) {
		this.boardedTravellers = boardedTravellers;
	}

	/**
	 * @return the alightedTravellers
	 */
	@OneToMany(mappedBy="destinationStation",fetch=FetchType.LAZY)
	public Set<UserJourneyDTO> getAlightedTravellers() {
		return alightedTravellers;
	}

	/**
	 * @param alightedTravellers the alightedTravellers to set
	 */
	public void setAlightedTravellers(Set<UserJourneyDTO> alightedTravellers) {
		this.alightedTravellers = alightedTravellers;
	}

	/**
	 * @return the stationRoutes
	 */
	@OneToMany(mappedBy="station",cascade=CascadeType.ALL)
	public Set<StationRouteDTO> getStationRoutes() {
		return stationRoutes;
	}

	/**
	 * @param stationRoutes the stationRoutes to set
	 */
	public void setStationRoutes(Set<StationRouteDTO> stationRoutes) {
		this.stationRoutes = stationRoutes;
	}

	/**
	 * @return the monitors
	 */
	@OneToMany(mappedBy="station",fetch=FetchType.LAZY)
	public Set<TrainJourneyMonitorDTO> getMonitors() {
		return monitors;
	}

	/**
	 * @param monitors the monitors to set
	 */
	public void setMonitors(Set<TrainJourneyMonitorDTO> monitors) {
		this.monitors = monitors;
	}

	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof MetroStationDTO)){
			return false;
		}
		
		MetroStationDTO otherStation = (MetroStationDTO)obj;
		
		return this.stationId == otherStation.stationId;
	}
	
	@Override
	public int hashCode(){
		return (this.stationId * 37);
	}
	
	void addRoute(StationRouteDTO stationRoute){
		if(this.stationRoutes == null){
			stationRoutes = new HashSet<StationRouteDTO>();
		}
		
		stationRoutes.add(stationRoute);
		
	}
	
	public void addMonitors(TrainJourneyMonitorDTO monitor){
		if(this.monitors == null){
			monitors = new HashSet<TrainJourneyMonitorDTO>();
		}
		
		monitors.add(monitor);
	}
}
