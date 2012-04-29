package edu.gac.arboretumweb.client;

import java.util.ArrayList;

import edu.gac.arboretumweb.shared.domain.Tree;
 
public interface SearchResultsRetrieval
{
    enum SearchType{
        commonName, scientificName, yearDonated, donatedFor;
    }
    /**
     * This provides an interface for the UI to retrieve the search results 
     * from the database given a certain search word and type
     * 
     * This interface should accommodate a SearchType of null, implying the algorithm 
     * should search through all types 
     * 
     * It acts as a mediator for information between the search-process level
     * of the application and the User interface
     * 
     * Will have to be expanded to include ArrayList<String> or an enumerated type 
     * to accommodate being able to search for Trees, Benches, Rocks, etc if time
     * allows
     *
     * @param search The String coming from the search box
     * @param type The type (donor, species, etc.) the search is for
     * @return returns an ArrayList of trees that match the search type
     */
    public ArrayList<Tree> getSearchResultsForSimpleSearch(String search, SearchType type);
}