package com.metrosystem.dao.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GeoLocationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String latitude;
	private String longitude;
	
	/**
	 * Default constructor
	 */
	public GeoLocationDTO(){
		
	}
	
	public GeoLocationDTO(String latitude,String longitude){
		this.latitude=latitude;
		this.longitude=longitude;
	}
	
	/**
	 * @return the latitude
	 */
	@Column(name="latitude",length=30,nullable=false)
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	@Column(name="longitude",length=30,nullable=false)
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof GeoLocationDTO)){
			return false;
		}
		
		GeoLocationDTO otherLocation = (GeoLocationDTO)obj;
		
		return latitude.equals(otherLocation.latitude) && longitude.equals(otherLocation.longitude);
	}
	
	@Override
	public int hashCode(){
		int hashCode = (latitude.hashCode() + longitude.hashCode() )*37;
		
		return hashCode;
	}
	
	
}
