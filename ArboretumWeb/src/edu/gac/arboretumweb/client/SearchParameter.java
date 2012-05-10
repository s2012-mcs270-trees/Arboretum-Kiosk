package edu.gac.arboretumweb.client;

import java.util.ArrayList;

/**
 * A single class for representing the parameters that will be passed from the MainPage to the class responsible 
 * for conducting the search and to the SearchResultsPage so that it can display what the search parameters were.
 * 
 * This class is also home to the definitions of the enumerated types used for the SearchParameter:
 * (SearchType, SearchFor, and Quadrant).
 * @author Matt
 *
 */
public class SearchParameter 
{
	//An enumerated type for the parameter.  It specifies strings to be used for each value so that
	//instead of returning, for example, "commonName", it will return "Common Name".
	public enum SearchType
	{
		//allowed values with names specified in parentheses
        commonName("Common Name"), 
        scientificName("Scientific Name"), 
        yearDonated("Year Donated"), 
        donatedFor("Donated For");
        
        //declaring the name field
        private final String name;
        
        //constructor
        SearchType(String name)
        {
        	this.name = name;
        }
        
        //called when the value is used in a string environment
        public String toString()
        {
        	return name;
        }
    }
	
	enum SearchFor
	{
		trees("Trees"), 
		benches("Benches"), 
		bricks("Bricks");
		
		private final String name;
		
		SearchFor(String name)
		{
			this.name = name;
		}
		
		public String toString()
		{
			return name;
		}
	}
	
	// Specifying the strings to be returned here is not necessary
	enum Quadrant{
		A, B, C, D
	}
	
	String keywordQuery;
	private int lowBoundDonatedYear;
	private int highBoundDonatedYear;
	private ArrayList<SearchFor> searchForParams;//(trees, benches bricks)
	private SearchType searchType;
	private Quadrant quadrant;
	
	public SearchParameter(String keywordQuery, 
			ArrayList<SearchFor> searchForParams, 
			SearchType searchType, 
			Quadrant quadrant, 
			int lowBoundDonatedYear,
			int highBoundDonatedYear)
	{
		this.keywordQuery = keywordQuery;
		this.searchType = searchType;
		this.searchForParams = searchForParams;
		this.quadrant = quadrant;
		this.highBoundDonatedYear = highBoundDonatedYear;
		this.lowBoundDonatedYear = lowBoundDonatedYear;
	}

	public String getKeywordQuery()
	{
		return keywordQuery;
	}
	
	public int getLowBoundDonatedYear() {
		return lowBoundDonatedYear;
	}

	public int getHighBoundDonatedYear() {
		return highBoundDonatedYear;
	}

	public ArrayList<SearchFor> getSearchForParams() {
		return searchForParams;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public Quadrant getQuadrant() {
		return quadrant;
	}
}
