package edu.gac.arboretumweb.shared.domain;

import java.io.Serializable;

import edu.gac.arboretumweb.client.SearchParameter.Quadrant;

public class Tree extends DonatedObject implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String commonName;
    private String scientificName;
    private String yearPlanted;
    private String health;
    private String longitude;
    private String latitude;
    private String diameter;
    private String donatedFor;
    private Quadrant quadrant;
    
    //TODO: decide whether or not to include yearPlanted, diameter, and health since they aren't 
       //used in the UI
    
    //default constructor for debugging and to ensure that all trees created have specified fields
    public Tree() 
    {
    	super.setYearDonated(1990);
    	commonName = "White Oak";
    	scientificName = "Quercus alba";
    	yearPlanted = "1999";
    	health = "good";
    	longitude = "93.9578";
    	latitude = "44.3236";
    	diameter = "2.2";
    	donatedFor = "Mark Johnson";
    	this.setQuadrant();
    }
    
    public static void main(String[] args) {

    }

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getYearPlanted() {
		return yearPlanted;
	}

	public void setYearPlanted(String yearPlanted) {
		this.yearPlanted = yearPlanted;
	}

	public void setYearDonated(int yearDonated) {
		this.yearDonated = yearDonated;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
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

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getDonatedFor() {
		return donatedFor;
	}

	public void setDonatedFor(String donatedFor) {
		this.donatedFor = donatedFor;
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