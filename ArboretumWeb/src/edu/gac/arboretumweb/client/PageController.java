package edu.gac.arboretumweb.client;

import edu.gac.arboretumweb.shared.domain.Tree;

public class PageController 
{
private static PageController instance = null;
	
	protected PageController() 
	{
		// Exists only to defeat instantiation.
	}
	
	// Retrieve the singleton instance of page controller, usually to 
	// access it's methods of switching pages
	public static PageController sharedPageController() 
	{
		if(instance == null) 
		{
			instance = new PageController();
	    }
	    return instance;
	}
	
	public void showMainPage()
	{
		
	}
	
	public void showSearchResultsPage(SearchParameter p)
	{
		
	}
	
	public void showTreePage(Tree tree)
	{
		
	}
}

