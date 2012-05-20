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
	public enum Quadrant{
		A, B, C, D, E
	}

	String keywordQuery;
	private SearchFor searchFor;//(trees, benches bricks)
	private SearchType searchType;
	private ArrayList<Quadrant> quadrants;
	private boolean undonatedBoxChecked;

	/**
	 * A default constructor used for debugging and testing purposes
	 * 
	 */
	public SearchParameter()
	{
		ArrayList<Quadrant> quadrants = new ArrayList<Quadrant>();
		quadrants.add(Quadrant.E);
		this.keywordQuery = "Grandpa Jo";
		this.searchFor = SearchFor.trees;
		this.searchType = SearchType.commonName;
		this.quadrants = quadrants;
		this.undonatedBoxChecked = false;
	}
	
	/**
	 * 
	 * @param keywordQuery
	 * @param searchFor
	 * @param searchType
	 * @param quadrants
	 * @param unDonatedBoxChecked
	 */
	public SearchParameter(String keywordQuery, 
			SearchFor searchFor, 
			SearchType searchType, 
			ArrayList<Quadrant> quadrants, 
			boolean unDonatedBoxChecked)
	{
		this.keywordQuery = keywordQuery;
		this.searchType = searchType;
		this.searchFor= searchFor;
		this.quadrants = quadrants;
		this.undonatedBoxChecked = unDonatedBoxChecked;
	}
	
	public boolean isUndonatedBoxChecked(){
		return undonatedBoxChecked;
	}
	
	public String getKeywordQuery()
	{
		return keywordQuery;
	}

	public String getSearchTypeAsString() {
		return searchType.toString();
	}

	public ArrayList<Quadrant> getQuadrants() {
		return quadrants;
	}
	public SearchType getSearchType() {
		return searchType;
	}
	public SearchFor getSearchFor() {
		return searchFor;
	}
}