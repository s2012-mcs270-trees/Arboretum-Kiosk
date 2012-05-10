package edu.gac.arboretumweb.client;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import edu.gac.arboretumweb.client.SearchParameter.Quadrant;
import edu.gac.arboretumweb.client.SearchParameter.SearchFor;
import edu.gac.arboretumweb.client.SearchParameter.SearchType;
import edu.gac.arboretumweb.shared.domain.Tree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class SearchResultsPage implements EntryPoint, Page
{
	/**
	 * The main DataGrid.
	 */
	@UiField(provided = true)
	DataGrid<Tree> dataGrid = new DataGrid<Tree>();

	/**
	 * The pager used to change the range of data.
	 */
	@UiField(provided = true)
	SimplePager pager;
	
	private RootPanel rootPanel = RootPanel.get();
	private AbsolutePanel absolutePanel = new AbsolutePanel();
		
	CheckBox checkBoxTrees = new CheckBox("Trees");
	CheckBox checkBoxBenches = new CheckBox("Benches");
	CheckBox checkBoxBricks = new CheckBox("Bricks");
    CheckBox checkBoxQuadrantA = new CheckBox("A");
    CheckBox checkBoxQuadrantB = new CheckBox("B");
    CheckBox checkBoxQuadrantC = new CheckBox("C");
    CheckBox checkBoxQuadrantD = new CheckBox("D");
    
	ListBox byListBox = new ListBox();
	
	Label searchResultsLabel = new Label("Search Results for: ");
	Label inLabel = new Label("In:");
	Label byLabel = new Label("By:");
	Label locatedInQuadrantLabel = new Label("Located in Quadrant:");
	Label donatedBetweenLabel = new Label("Donated Between:");
	Label andLabel = new Label("AND");
	
	TextBox earliestSearchYearTextBox = new TextBox();
	TextBox latestSearchYearTextBox = new TextBox();
	TextBox searchKeywordsTextBox = new TextBox();
	
	Button updateSearchButton = new Button("UPDATE SEARCH CRITERIA");
	Button backButton = new Button("BACK");
	
	@Override
	/**
	 * @wbp.parser.entryPoint
	 */
	public void onModuleLoad() 
	{
		//This method of calling calling the page is used only for debugging purposes.  The SearchResultsPage will actually
		//be instantiated by the PageController which will also be responsible for opening it when necessary
		ArrayList<SearchFor> searchForParams = new ArrayList<SearchFor>();
		searchForParams.add(SearchFor.trees);
		SearchParameter searchParameter = new SearchParameter("Grandpa Jo", searchForParams, SearchType.commonName, Quadrant.C, 1862, 2012);
		this.show(searchParameter);
	}

	public void show(final SearchParameter searchParameter) 
	{
		ArrayList<Tree> sampleTreelist = new ArrayList<Tree>();
		for (int i = 0; i < 100; i++)
		{
			Tree newTree = new Tree();
			newTree.setCommonName("Oak" + i);
			newTree.setDiameter("16");
			newTree.setDonatedBy("Matthew Knutson");
			newTree.setDonatedFor("Jesus");
			newTree.setHealth("Great");
			newTree.setLatitude("10.30");
			newTree.setLongitude("15.50");
			newTree.setScientificName("Oakas Burillus");
			newTree.setYearDonated(String.valueOf(1950 + i));
			newTree.setYearPlanted("1998");
			sampleTreelist.add(newTree);
		}
		
		rootPanel.setSize("1200", "900");
		rootPanel.add(absolutePanel, 0, 0);
	    absolutePanel.setSize("1062px", "1001px");
	    
	    absolutePanel.add(dataGrid, 0, 173);
	    dataGrid.setSize("1040px", "308px");
	    
	    //adding the items to the listBox 
	    byListBox.addItem("Donated For");
	    byListBox.addItem("Common Name");
	    byListBox.addItem("Scientific Name");
	    byListBox.setSize("192px", "74px");
	    byListBox.setVisibleItemCount(1);
	    absolutePanel.add(byListBox, 46, 83);
	    
	    //adding all the checkBoxes to the panel
	    absolutePanel.add(checkBoxTrees, 46, 52);
	    checkBoxTrees.setSize("67px", "20px");
	    absolutePanel.add(checkBoxBenches, 112, 52);
	    checkBoxBenches.setSize("83px", "20px");
	    absolutePanel.add(checkBoxBricks, 191, 52);
	    checkBoxBricks.setSize("83px", "20px");
	    
	    //adding the quadrants to the panel
	    absolutePanel.add(checkBoxQuadrantA, 528, 10);
	    checkBoxQuadrantA.setSize("36px", "20px");
	    absolutePanel.add(checkBoxQuadrantB, 570, 10);
	    checkBoxQuadrantB.setSize("36px", "20px");
	    absolutePanel.add(checkBoxQuadrantC, 611, 10);
	    checkBoxQuadrantC.setSize("36px", "20px");
	    absolutePanel.add(checkBoxQuadrantD, 653, 10);
	    checkBoxQuadrantD.setSize("36px", "20px");
	    
	    //adding all the labels to the panel
	    absolutePanel.add(searchResultsLabel, 24, 10);
	    absolutePanel.add(inLabel, 24, 52);
	    inLabel.setSize("14px", "24px");
	    absolutePanel.add(byLabel, 20, 84);
	    byLabel.setSize("20px", "24px");
	    absolutePanel.add(locatedInQuadrantLabel, 401, 10);
	    locatedInQuadrantLabel.setSize("121px", "24px");
	    absolutePanel.add(donatedBetweenLabel, 401, 52);
	    absolutePanel.add(andLabel, 600, 50);
	    
	    //adding all the text boxes to the panel
	    absolutePanel.add(earliestSearchYearTextBox, 528, 45);
	    earliestSearchYearTextBox.setSize("57px", "18px");
	    earliestSearchYearTextBox.setText(String.valueOf(searchParameter.getLowBoundDonatedYear()));
	    absolutePanel.add(latestSearchYearTextBox, 649, 46);
	    latestSearchYearTextBox.setSize("57px", "18px");
	    latestSearchYearTextBox.setText(String.valueOf(searchParameter.getHighBoundDonatedYear()));
	    absolutePanel.add(searchKeywordsTextBox, 149, 10);
	    searchKeywordsTextBox.setSize("222px", "12px");
	    searchKeywordsTextBox.setText(searchParameter.getKeywordQuery());
	    
	    //setting the initial value enabled by the the byListBox based on the searchParameter
	    if(searchParameter.getSearchType() == SearchType.donatedFor)
	    	byListBox.setItemSelected(0, true);
	    if(searchParameter.getSearchType() == SearchType.commonName)
	    	byListBox.setItemSelected(1, true);
	    if(searchParameter.getSearchType() == SearchType.scientificName)
	    	byListBox.setItemSelected(2, true);
	    
	    //setting which of the Tree/Bench/Brick check boxes are checked base on the searchParameter
	    if(searchParameter.getSearchForParams().contains(SearchFor.trees))
	    	checkBoxTrees.setValue(true);
	    if(searchParameter.getSearchForParams().contains(SearchFor.benches))
	    	checkBoxBenches.setValue(true);
	    if(searchParameter.getSearchForParams().contains(SearchFor.bricks))
	    	checkBoxBricks.setValue(true);
	    
	    //setting which of the Quadrant check boxes are checked base on the searchParameter
	    if(searchParameter.getQuadrant() == Quadrant.A)
	    	checkBoxQuadrantA.setValue(true);
	    if(searchParameter.getQuadrant() == Quadrant.B)
	    	checkBoxQuadrantB.setValue(true);
	    if(searchParameter.getQuadrant() == Quadrant.C)
	    	checkBoxQuadrantC.setValue(true);
	    if(searchParameter.getQuadrant() == Quadrant.D)
	    	checkBoxQuadrantD.setValue(true);

	    // Attach a column sort handler to the ListDataProvider to sort the list of each column 
	    // of the dataGrid as needed (copied from example code) (not working)
	    // TODO: get the sorter to work or erase the code for it if deemed not essential
	    ListHandler<Tree> sortHandler = new ListHandler<Tree>(sampleTreelist);
	    dataGrid.addColumnSortHandler(sortHandler);
	    
	    //The following lines instantiate Column objects that will be added to the DataGrid
	    Column<Tree, String> commonNameColumn = new Column<Tree, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(Tree tree)
	    	{
	    		return tree.getCommonName();
	    	}
	    };
	    //Set up the sorter for the column
	    commonNameColumn.setSortable(true);
	    sortHandler.setComparator(commonNameColumn, new Comparator<Tree>() {
	      public int compare(Tree tree1, Tree tree2) {
	        return tree1.getCommonName().compareTo(tree2.getCommonName());
	      }
	    });
	    
	    Column<Tree, String> scientificNameColumn = new Column<Tree, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(Tree tree)
	    	{
	    		return tree.getScientificName();
	    	}
	    };
	    
	    Column<Tree, String> donatedForColumn = new Column<Tree, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(Tree tree)
	    	{
	    		return tree.getDonatedFor();
	    	}
	    };
	    
	    Column<Tree, String> yearDonatedColumn = new Column<Tree, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(Tree tree)
	    	{
	    		return tree.getYearDonated();
	    	}
	    };
	    //Set up the sorter for the column
	    yearDonatedColumn.setSortable(true);
	    sortHandler.setComparator(yearDonatedColumn, new Comparator<Tree>() {
	    	public int compare(Tree tree1, Tree tree2) {
	    		return tree1.getYearDonated().compareTo(tree2.getYearDonated());
	    	}
	    });
	    
	    //Add the previously created columns to the grid
	    dataGrid.addColumn(commonNameColumn, "Common Name");
	    dataGrid.addColumn(scientificNameColumn, "Scientific Name");
	    dataGrid.addColumn(donatedForColumn, "Donated For");
	    dataGrid.addColumn(yearDonatedColumn, "Year Donated");

	    //The dataGrid was instantiated with it's generic type "Tree", thus it's row data must consist of an ArrayList of Trees
	    dataGrid.setRowData(sampleTreelist);

	    //documentation says this isn't mandatory but is suggested
	    dataGrid.setRowCount(sampleTreelist.size());

	    //this is displayed when the user hovers the mouse over the gridTable
	    dataGrid.setTitle("Search Results");

	    // This sets how many results should be displayed on one page.  Currently, it limits how many results 
	    // will be shown in the grid and ignore all other results.  For example, the mock ArrayList<Tree> sampleTreelist
	    // contains 100 objects, but only 50 are displayed in the table, and there is no way to see the other 50
	    // TODO: fix the problem above (this could be done by getting the paging to work, otherwise we will have to 
	    // go with scrolling
	    dataGrid.setPageSize(50);
	    
	    //not sure what this does
	    //pager.setDisplay(dataGrid);
	    
	    absolutePanel.add(updateSearchButton, 839, 24);
	    updateSearchButton.setSize("173px", "81px");
	    //Add the association  ClickHandler to the updateSearchButton
	    updateSearchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				//TODO: check to make sure the current configuration is acceptable and searchable
				// this could be done in realtime by displaying some red text when the current configuration is not correct
				// or it could be checked when this method is run then told to the user by displaying a window
    			ArrayList<SearchFor> searchForParams = new ArrayList<SearchFor>();
    			
    			if(searchParameter.getSearchForParams().contains(SearchFor.trees))
    		    	searchForParams.add(SearchFor.trees);
    		    if(searchParameter.getSearchForParams().contains(SearchFor.benches))
    		    	searchForParams.add(SearchFor.benches);
    		    if(searchParameter.getSearchForParams().contains(SearchFor.bricks))
    		    	searchForParams.add(SearchFor.bricks);
    			
    		    SearchType searchType = SearchType.commonName;
    		    
    		    if(searchParameter.getSearchType() == SearchType.donatedFor)
    		    	searchType = SearchType.donatedFor;
    		    if(searchParameter.getSearchType() == SearchType.commonName)
    		    	searchType = SearchType.commonName;
    		    if(searchParameter.getSearchType() == SearchType.scientificName)
    		    	searchType = SearchType.scientificName;
    		    
    			SearchParameter newParam = new SearchParameter(searchKeywordsTextBox.getText(), 
    					searchForParams, 
    					searchType, 
    					Quadrant.D, //TODO: this will have to be changed once the SearchParameter class is changed to take a List of Quadrants 
    					Integer.parseInt(earliestSearchYearTextBox.getText()), //TODO: catch these two parses if input is incorrect
    					Integer.parseInt(latestSearchYearTextBox.getText()));
    			PageController.sharedPageController().showSearchResultsPage(newParam);
			}
		});
	    
	    absolutePanel.add(backButton, 839, 494);
	    backButton.setSize("173px", "81px");
	    backButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event)
			{
				rootPanel.clear();
    			PageController.sharedPageController().showMainPage();
			}
		});
	    	    	    
	    // Add a selection model to handle the user clicking or touching a row selection 
	    // (corresponding to a tree being selected, 'selectedTree')
	    final SingleSelectionModel<Tree> selectionModel = new SingleSelectionModel<Tree>();
	    dataGrid.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() 
	    {
	    	public void onSelectionChange(SelectionChangeEvent event) 
	    	{
	    		Tree selectedTree = selectionModel.getSelectedObject();
	    		if (selectedTree != null) 
	    		{
	    			rootPanel.clear();
	    			PageController.sharedPageController().showTreePage(selectedTree, searchParameter);
	    		}
	    	}
	    });
	    
	    // Create a Pager to control the table.  This came straight from the example
	    // I followed. I don't believe it adds any functionality to our project at all.
	    // However, I kept it hoping that it was somehow responsible for paging our results
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setVisible(true);
	    
	    // TODO: have Tim add a map somewhere on this results page with stars showing where the trees that were hits to the 
	    // search
	}

	@Override
	public void close() 
	{		
		rootPanel.clear();
	}
	
	@Override
	public void reload(){
		rootPanel.setVisible(true);
	}
}
