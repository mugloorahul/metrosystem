package com.metrosystem.service.beans;


public class GeoLocationBO {

	private String latitude;
	private String longitude;
	
	/**
	 * Default constructor
	 */
	public GeoLocationBO(){
		
	}

	public GeoLocationBO(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
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
		
		if(!(obj instanceof GeoLocationBO)){
			return false;
		}
		
		GeoLocationBO otherLocation = (GeoLocationBO)obj;
		
		return latitude.equals(otherLocation.latitude) && longitude.equals(otherLocation.longitude);
	}
	
	@Override
	public int hashCode(){
		int hashCode = (latitude.hashCode() + longitude.hashCode() )*37;
		
		return hashCode;
	}
}
