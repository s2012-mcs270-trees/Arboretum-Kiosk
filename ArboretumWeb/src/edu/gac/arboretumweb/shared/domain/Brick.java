package edu.gac.arboretumweb.shared.domain;

import java.io.Serializable;

public class Brick extends DonatedObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private String size;
    private String distance;
    
    /**
     * used for debugging purposes
     */
    public Brick() {
//    	this.setDistance("1");
//    	this.setDonatedFor("Captian Morgan");
//    	this.setSize("Massive");
//    	this.setYearDonated("1999");
    }

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}    
}