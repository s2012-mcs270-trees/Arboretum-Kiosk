package edu.gac.arboretumweb.shared.domain;

import java.io.Serializable;

import edu.gac.arboretumweb.client.SearchParameter.Quadrant;

public class Bench extends DonatedObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	private String type;
    private double longitude;
    private double latitude;
    private Quadrant quadrant;
    
    public Bench ()
    {
//    	this.setDonatedFor("Michael Jackson");
//    	this.setLatitude(94.3345);
//    	this.setLongitude(69.696969);
//    	this.setType("An awesome one");
//    	this.setYearDonated("1849");
//    	this.setQuadrant();//set quadrants based on lat/long that was initialized
    }
    
    public String getBenchType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double d) {
		this.latitude = d;
	}
	
	public void setQuadrant()
	{
		double latitude = this.getLatitude();
		double longitude = this.getLongitude();
		 
		if(latitude < 44.323368 && longitude< -93.976750)
		{
			this.quadrant = Quadrant.B;
		}
		else if (latitude < 44.323368 && longitude< -93.973827)
		{
			this.quadrant = Quadrant.A;
		}
		else if (latitude < 44.323368 && longitude >= -93.973827)
		{
			this.quadrant = Quadrant.D;
		}
		else if (latitude >= 44.323368 && longitude < -93.973827)
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