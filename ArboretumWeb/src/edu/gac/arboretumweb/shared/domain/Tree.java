package edu.gac.arboretumweb.shared.domain;

public class Tree {
    private String commonName;
    private String scientificName;
    private String yearPlanted;
    private String yearDonated;
    private String health;
    private String longitude;
    private String latitude;
    private String diameter;
    private String donatedFor;
    private String donatedBy;
    
    public Tree() {
        
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

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

	public String getYearDonated() {
		return yearDonated;
	}

	public void setYearDonated(String yearDonated) {
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

	public String getDonatedBy() {
		return donatedBy;
	}

	public void setDonatedBy(String donatedBy) {
		this.donatedBy = donatedBy;
	}
}