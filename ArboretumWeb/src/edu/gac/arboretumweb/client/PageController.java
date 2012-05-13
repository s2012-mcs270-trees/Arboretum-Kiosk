package edu.gac.arboretumweb.client;

import com.google.gwt.core.client.EntryPoint;

import edu.gac.arboretumweb.shared.domain.Bench;
import edu.gac.arboretumweb.shared.domain.Brick;
import edu.gac.arboretumweb.shared.domain.Tree;

/**
 * This class is responsible for calling the methods for showing and closing the three
 * different pages in the application
 */

public class PageController implements EntryPoint
{
	private static PageController instance = null;

	private final MainPage mainPage = new MainPage();
	private final SearchResultsPage searchResultsPage = new SearchResultsPage();
	private final TreePage treePage = new TreePage();
	private final BenchPage benchPage = new BenchPage();
	private final BrickPage brickPage = new BrickPage();
	
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
		mainPage.show();
	}

	//Note that this method does not take the parameter for the list of trees that matched the query.
	//Thus it probably makes sense to have the SearchResultsPage be responsible for instantiating 
	//the Searcher class since it is the only one that will use the data (MainPage doesn't use the list)
	public void showSearchResultsPage(SearchParameter p)
	{
		// Right now, an entire new SearchResultsPage instantiation is created because extra columns get added 
		// to the dataGrid in the class every time it is called.  This call is thus redundant since
		// any class that wants to call showTreePage() could instantiate a new TreePage like here and run
		// .show(tree, sp).  This makes the PageController redundant in this case except to limit the 
		// knowledge of page classes between page classes
		// TODO: determine the best way to fix this structure of implementing pages
		// 1) one option would be to have all the initialization of the page be done in the constructor, 
		// then update the fields (if any) that need to be update in the .show() method of each page.
		// This would help keep the PageController.
		searchResultsPage.show(p);
	}

	/**
	 * @param tree - the tree page to be displayed
	 * @param sp, required so that if the user wishes to press the "Back" button, the search configuration can be loaded to 
	 * show their last search
	 */
	public void showTreePage(Tree tree, SearchParameter sp)
	{
		treePage.show(tree, sp);
	}
	
	public void showBenchPage(Bench bench, SearchParameter sp)
	{
		benchPage.show(bench, sp);
	}
	
	public void showBrickPage(Brick brick, SearchParameter sp)
	{
		brickPage.show(brick, sp);
	}
	
	@Override
	public void onModuleLoad() 
	{
		showMainPage();
	}
}

