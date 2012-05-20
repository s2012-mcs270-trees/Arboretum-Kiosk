package edu.gac.arboretumweb.shared.domain;

import edu.gac.arboretumweb.client.SearchParameter.Quadrant;

public class DonatedObject {

	public String yearDonated;
	public String donatedFor;

	public String getYearDonated() 
	{
		return yearDonated;
	}
	
	public void setYearDonated(String yearDonated) 
	{
		this.yearDonated = yearDonated;
	}
	
	public String getDonatedFor() 
	{
		return donatedFor;
	}
	
	public void setDonatedFor(String donatedFor) 
	{
		this.donatedFor = donatedFor;
	}
	
	public Quadrant getQuadrant()
	{
		return Quadrant.E;
	}
}