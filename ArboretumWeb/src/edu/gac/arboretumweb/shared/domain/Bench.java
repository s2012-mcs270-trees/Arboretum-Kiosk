package edu.gac.arboretumweb.shared.domain;

import java.io.Serializable;

import edu.gac.arboretumweb.client.SearchParameter.Quadrant;

public class Bench extends DonatedObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String type;
    private String longitude;
    private String latitude;
    private Quadrant quadrant;
    
    public Bench ()
    {
    	this.setDonatedFor("Michael Jackson");
    	this.setLatitude("94.3345");
    	this.setLongitude("69.696969");
    	this.setType("An awesome one");
    	this.setYearDonated(1849);
    	this.setQuadrant();
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
	
	private void setQuadrant()
	{
		Float Latitude = (float) Double.valueOf(this.getLatitude()).doubleValue();
		Float Longitude = (float) Double.valueOf(this.getLongitude()).doubleValue();
		 
		if(Latitude < 44.323368 && Longitude < -93.976750)
		{
			this.quadrant = Quadrant.B;
		}
		else if (Latitude < 44.323368 && Longitude < -93.973827)
		{
			this.quadrant = Quadrant.A;
		}
		else if (Latitude < 44.323368 && Longitude >= -93.973827)
		{
			this.quadrant = Quadrant.D;
		}
		else if (Latitude >= 44.323368 && Longitude < -93.973827)
		{
			this.quadrant = Quadrant.C;
		}
		else
			this.quadrant = Quadrant.E;
	}
	
	public Quadrant getQuadrant()
	{
		return quadrant;
	}
}