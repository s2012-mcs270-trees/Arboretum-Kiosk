package edu.gac.arboretumweb.shared.domain;

import java.io.Serializable;

public class Bench extends DonatedObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String type;
    private String longitude;
    private String latitude;

    public Bench ()
    {
    	this.setDonatedFor("Michael Jackson");
    	this.setLatitude("94.3345");
    	this.setLongitude("69.696969");
    	this.setType("An awesome one");
    	this.setYearDonated(1849);
    }
    
    public String getBenchType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String d) {
		this.latitude = d;
	}
}