package edu.gac.arboretumweb.client;

import java.util.ArrayList;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
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
import edu.gac.arboretumweb.shared.domain.Bench;
import edu.gac.arboretumweb.shared.domain.Brick;
import edu.gac.arboretumweb.shared.domain.DonatedObject;
import edu.gac.arboretumweb.shared.domain.Tree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;

public class SearchResultsPage implements EntryPoint
{
	/**
	 * The main DataGrid.
	 */
	@UiField(provided = true)
	DataGrid<DonatedObject> dataGrid = new DataGrid<DonatedObject>();

	/**
	 * The pager used to change the range of data.
	 */
	@UiField(provided = true)
	SimplePager pager;
	
	private RootPanel rootPanel = RootPanel.get();
	private AbsolutePanel absolutePanel = new AbsolutePanel();
	
	Image image;
	
	Searcher searcher = new Searcher();
	
	ArrayList<DonatedObject> sampleTreeList = new ArrayList<DonatedObject>();
	ArrayList<DonatedObject> sampleBenchList = new ArrayList<DonatedObject>();
	ArrayList<DonatedObject> sampleBrickList = new ArrayList<DonatedObject>();
	
    CheckBox checkBoxQuadrantA = new CheckBox("A");
    CheckBox checkBoxQuadrantB = new CheckBox("B");
    CheckBox checkBoxQuadrantC = new CheckBox("C");
    CheckBox checkBoxQuadrantD = new CheckBox("D");
    CheckBox checkBoxQuadrantE = new CheckBox("E");
    
    //Columns used for trees benches and bricks
    Column<DonatedObject, String> donatedForColumn;
    Column<DonatedObject, String> yearDonatedColumn;
    Column<DonatedObject, String> quadrantColumn;//not used for bricks
    
    //Columns used for trees only
    Column<DonatedObject, String> commonNameColumn;
    Column<DonatedObject, String> scientificNameColumn;
    
    //Column used for Benches only
    Column<DonatedObject, String> benchTypeColumn;
    
    //Column used for bricks
    Column<DonatedObject, String> brickLocationColumn;
    Column<DonatedObject, String> brickSizeColumn;
    
	ListBox byListBox = new ListBox();
	ListBox inListBox = new ListBox();
	
	Label searchResultsLabel = new Label("Search Results for: ");
	Label inLabel = new Label("In:");
	Label byLabel = new Label("By:");
	Label locatedInQuadrantLabel = new Label("Located in Quadrant:");
	TextBox searchKeywordsTextBox = new TextBox();
	
	Button updateSearchButton = new Button("UPDATE SEARCH CRITERIA");
	Button backButton = new Button("BACK");
	
	@Override
	/**
	 * @wbp.parser.entryPoint
	 */
	public void onModuleLoad() 
	{
		//This method of calling calling the page is used only for debugging purposes (GWT Designer).  The SearchResultsPage will actually
		//be instantiated by the PageController which will also be responsible for opening it when necessary
		
		this.show(new SearchParameter());
	}
	
	public SearchResultsPage()
	{	
		rootPanel.setSize("1200", "900");
	    absolutePanel.setSize("1062px", "1001px");	    
	    absolutePanel.add(dataGrid, 0, 133);
	    dataGrid.setSize("1040px", "308px");
	    
		searcher.updateLocalMasterLists();
		
		for(int i = 0; i < 100; i++)
		{
			sampleBenchList.add(new Bench());
			sampleBrickList.add(new Brick());
			sampleTreeList.add(new Tree());
		}
		
	    //The following lines instantiate Column objects that will be added to the DataGrid
	    commonNameColumn = new Column<DonatedObject, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(DonatedObject tree)
	    	{
	    		if(tree instanceof Tree)
	    			return ((Tree)tree).getCommonName();
	    		else
	    			return null;
	    	}
	    };
	    	    
	    scientificNameColumn = new Column<DonatedObject, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(DonatedObject tree)
	    	{
	    		if(tree instanceof Tree)
	    			return ((Tree)tree).getScientificName();
	    		else
	    			return null;
	    	}
	    };
	    
	    donatedForColumn = new Column<DonatedObject, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(DonatedObject o)
	    	{
	    		return o.getDonatedFor();
	    	}
	    };
	    
	    yearDonatedColumn = new Column<DonatedObject, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(DonatedObject o)
	    	{
	    		return o.getYearDonated();
	    	}
	    };
	    
	    quadrantColumn = new Column<DonatedObject, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(DonatedObject o)
	    	{
	    		return o.getQuadrant().toString();
	    	}
	    };
	    
	    benchTypeColumn = new Column<DonatedObject, String>(new TextCell())
	    {
	    	@Override
	    	public String getValue(DonatedObject o)
	    	{
	    		if(o instanceof Bench)
	    			return ((Bench)o).getBenchType();
	    		else
	    			return null;
	    		
	    	}
		};
		
		brickLocationColumn = new Column<DonatedObject, String>(new TextCell())
		{
			@Override
	    	public String getValue(DonatedObject brick)
	    	{
	    		if(brick instanceof Brick)
	    			return ((Brick)brick).getDistance();
	    		else
	    			return null;
	    	}
		};
		
		brickSizeColumn = new Column<DonatedObject, String>(new TextCell())
				{
					@Override
			    	public String getValue(DonatedObject brick)
			    	{
			    		if(brick instanceof Brick)
			    			return ((Brick)brick).getSize();
			    		else
			    			return null;
			    	}
				};
	    	    
	    //Add the previously created columns to the grid
	    dataGrid.addColumn(commonNameColumn, "Common Name");
	    dataGrid.addColumn(scientificNameColumn, "Scientific Name");
	    dataGrid.addColumn(donatedForColumn, "Donated For");
	    dataGrid.addColumn(yearDonatedColumn, "Year Donated");	 
	    dataGrid.addColumn(quadrantColumn, "Quadrant Location");
	    
	    //this is displayed when the user hovers the mouse over the gridTable
	    dataGrid.setTitle("Search Results");
	    
	    byListBox.addItem("Donated For");
	    byListBox.addItem("Common Name");
	    byListBox.addItem("Scientific Name");
	    byListBox.setSize("192px", "74px");
	    byListBox.setVisibleItemCount(1);//Sets the number of options that are visible at one time, setting it to one makes it a 
	    inListBox.addItem("Trees");				// dropdown selection box
	    inListBox.addItem("Benches");
	    inListBox.addItem("Bricks");
	    inListBox.setSize("117px", "22px");
	    inListBox.setVisibleItemCount(1);
	    
	    //adding the items to the listBox 
	    absolutePanel.add(byListBox, 427, 52);
	    absolutePanel.add(inListBox, 44, 52);
	  	    
	    //adding the quadrants to the panel
	    absolutePanel.add(checkBoxQuadrantA, 528, 10);
	    checkBoxQuadrantA.setSize("36px", "20px");
	    absolutePanel.add(checkBoxQuadrantB, 570, 10);
	    checkBoxQuadrantB.setSize("36px", "20px");
	    absolutePanel.add(checkBoxQuadrantC, 611, 10);
	    checkBoxQuadrantC.setSize("36px", "20px");
	    absolutePanel.add(checkBoxQuadrantD, 653, 10);
	    checkBoxQuadrantD.setSize("36px", "20px");
	    absolutePanel.add(checkBoxQuadrantE, 695, 10);
	    
	    //adding all the labels to the panel
	    absolutePanel.add(searchResultsLabel, 24, 10);
	    absolutePanel.add(inLabel, 24, 52);
	    inLabel.setSize("14px", "24px");
	    absolutePanel.add(byLabel, 401, 53);
	    byLabel.setSize("20px", "24px");
	    absolutePanel.add(locatedInQuadrantLabel, 401, 10);
	    locatedInQuadrantLabel.setSize("121px", "24px");
	    absolutePanel.add(searchKeywordsTextBox, 149, 10);
	    searchKeywordsTextBox.setSize("222px", "12px");
	    
	    absolutePanel.add(updateSearchButton, 839, 24);
	    updateSearchButton.setSize("173px", "81px");
	    
	    //Add the association  ClickHandler to the updateSearchButton
	    updateSearchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) 
			{
				//TODO: check to make sure the current configuration is acceptable and searchable
				// this could be done in realtime by displaying some red text when the current configuration is not correct
				// or it could be checked when this method is run then told to the user by displaying a window
    			if(currentConfigurationIsAcceptable())
    			{
    				SearchParameter newParam = extractSearchParameterFromConfiguration();
        			configureColumnsBasedOnSearchParameter(newParam);			
    			}
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
	    final SingleSelectionModel<DonatedObject> selectionModel = new SingleSelectionModel<DonatedObject>();
	    dataGrid.setSelectionModel(selectionModel);
	    
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() 
	    {
	    	public void onSelectionChange(SelectionChangeEvent event) 
	    	{
	    		DonatedObject selectedObject= selectionModel.getSelectedObject();
	    		if (selectedObject != null) 
	    		{
	    			rootPanel.clear();
	    			SearchParameter searchParameter = extractSearchParameterFromConfiguration(); 
	    			if(selectedObject instanceof Tree)
	    			{
	    				PageController.sharedPageController().showTreePage((Tree)selectedObject, searchParameter);
	    			}
	    			if(selectedObject instanceof Bench)
	    			{
	    				PageController.sharedPageController().showBenchPage((Bench)selectedObject, searchParameter);
	    			}
	    			if(selectedObject instanceof Brick)
	    			{
	    				PageController.sharedPageController().showBrickPage((Brick)selectedObject, searchParameter);
	    			}
	    		}
	    	}
	    });
	}

	public void show(final SearchParameter searchParameter) 
	{	    
		//ultimately responsible for the SearchResultsPage (which is basically just this absolute panel) being 
		// shown on the screen
		rootPanel.add(absolutePanel, 0, 0);
				
		rootPanel.add(image, 54, 44);
		image.setSize("100px", "100px");
	    
		// Sets up which columns to display for Trees, Benches, or Bricks since each
	    // selection shows different columns. Also sets up the data for each row based
	    // on the ArrayList that is returned by the searcher
	    configureColumnsBasedOnSearchParameter(searchParameter);
				
		searchKeywordsTextBox.setText(searchParameter.getKeywordQuery());
	    	    	    	    
	    //setting the initial value enabled by the the byListBox based on the searchParameter
	    if(searchParameter.getSearchType() == SearchType.donatedFor)
	    	byListBox.setItemSelected(0, true);
	    if(searchParameter.getSearchType() == SearchType.commonName)
	    	byListBox.setItemSelected(1, true);
	    if(searchParameter.getSearchType() == SearchType.scientificName)
	    	byListBox.setItemSelected(2, true);
	    
	    //setting which of the Tree/Bench/Brick check boxes are checked base on the searchParameter
	    if(searchParameter.getSearchFor() == SearchFor.trees)
	    	inListBox.setItemSelected(0, true);
	    if(searchParameter.getSearchFor() == SearchFor.benches)
	    	inListBox.setItemSelected(1, true);
	    if(searchParameter.getSearchFor() == SearchFor.bricks)
	    	inListBox.setItemSelected(2, true);
	    
	    //setting which of the Quadrant check boxes are checked base on the searchParameter
	    if(searchParameter.getQuadrants().contains(Quadrant.A))
	    	checkBoxQuadrantA.setValue(true);
	    if(searchParameter.getQuadrants().contains(Quadrant.B))
	    	checkBoxQuadrantB.setValue(true);
	    if(searchParameter.getQuadrants().contains(Quadrant.C))
	    	checkBoxQuadrantC.setValue(true);
	    if(searchParameter.getQuadrants().contains(Quadrant.D))
	    	checkBoxQuadrantD.setValue(true);
	    if(searchParameter.getQuadrants().contains(Quadrant.E))
	    	checkBoxQuadrantE.setValue(true);

	    // Attach a column sort handler to the ListDataProvider to sort the list of each column 
	    // of the dataGrid as needed (copied from example code) (not working)
	    // TODO: get the sorter to work or erase the code for it if deemed not essential
	    // Cindy says it would be nice to have if possible, but not essential
//	    ListHandler<DonatedObject> sortHandler = new ListHandler<DonatedObject>(sampleTreelist);
//	    dataGrid.addColumnSortHandler(sortHandler);
	    	    
	    //Set up the sorter for the column
//	    commonNameColumn.setSortable(true);
//	    sortHandler.setComparator(commonNameColumn, new Comparator<DonatedObject>() {
//	      public int compare(DonatedObject tree1, DonatedObject tree2) {
//	        return ((Tree)tree1).getCommonName().compareTo(((Tree)tree2).getCommonName());
//	      }
//	    });

	    // This sets how many results should be displayed on one page.  Currently, it limits how many results 
	    // will be shown in the grid and ignore all other results.  For example, the mock ArrayList<Tree> sampleTreelist
	    // contains 100 objects, but only 50 are displayed in the table, and there is no way to see the other 50
	    // TODO: fix the problem above (this could be done by getting the paging to work, otherwise we will have to 
	    // go with scrolling
	    dataGrid.setPageSize(50);
	    
	    //not sure what this does
	    //pager.setDisplay(dataGrid);
	    	    	    	    	    	    		    
	    // Create a Pager to control the table.  This came straight from the example
	    // I followed. I don't believe it adds any functionality to our project at all.
	    // However, I kept it hoping that it was somehow responsible for paging our results
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setVisible(true);
	    
	    // TODO: have Tim add a map somewhere on this results page with stars showing where the trees that were hits to the 
	    // search
	}
	
	private void configureColumnsBasedOnSearchParameter(SearchParameter searchParameter)
	{
		if(searchParameter.getSearchFor() == SearchFor.trees)
		{
			ArrayList<Tree> trees = searcher.searchResultsForTreeSearch(searchParameter);
			dataGrid.setRowData(trees);
			if(dataGrid.getColumnIndex(scientificNameColumn) == -1)//dataGrid does not contain commonNameColumn
			{
				dataGrid.insertColumn(0, scientificNameColumn, "Scientific Name");
			}
			if(dataGrid.getColumnIndex(commonNameColumn) == -1)
			{
				dataGrid.insertColumn(0, commonNameColumn, "Common Name");
			}
			if(!(dataGrid.getColumnIndex(benchTypeColumn) == -1))
			{
				dataGrid.removeColumn(benchTypeColumn);
			}
			if(!(dataGrid.getColumnIndex(brickLocationColumn) == -1))
			{
				dataGrid.removeColumn(brickLocationColumn);
			}
			if(!(dataGrid.getColumnIndex(brickSizeColumn) == -1))
			{
				dataGrid.removeColumn(brickSizeColumn);
			}		
			setupMapForTrees(trees);
		}
		if(searchParameter.getSearchFor() == SearchFor.benches)
		{
			ArrayList<Bench> benches = searcher.searchResultsForBenchSearch(searchParameter);
			dataGrid.setRowData(sampleBenchList);
			if(!(dataGrid.getColumnIndex(commonNameColumn) == -1))
			{
				dataGrid.removeColumn(commonNameColumn);
			}
			if(!(dataGrid.getColumnIndex(scientificNameColumn) == -1))
			{
				dataGrid.removeColumn(scientificNameColumn);
			}
			if(dataGrid.getColumnIndex(benchTypeColumn) == -1)
			{
				dataGrid.addColumn(benchTypeColumn, "Bench Type");
			}
			if(!(dataGrid.getColumnIndex(brickLocationColumn) == -1))
			{
				dataGrid.removeColumn(brickLocationColumn);
			}
			if(!(dataGrid.getColumnIndex(brickSizeColumn) == -1))
			{
				dataGrid.removeColumn(brickSizeColumn);
			}
			setupMapForBenches(benches);
			//documentation says this isn't mandatory but is suggested
		    //dataGrid.setRowCount(sampleTreelist.size());
		}
		if(searchParameter.getSearchFor() == SearchFor.bricks)
		{
			//searcher.searchResultsForBrickSearch(searchParameter)
			dataGrid.setRowData(sampleBrickList);
			if(!(dataGrid.getColumnIndex(commonNameColumn) == -1))
			{
				dataGrid.removeColumn(commonNameColumn);
			}
			if(!(dataGrid.getColumnIndex(scientificNameColumn) == -1))
			{
				dataGrid.removeColumn(scientificNameColumn);
			}
			if(!(dataGrid.getColumnIndex(benchTypeColumn) == -1))
			{
				dataGrid.removeColumn(benchTypeColumn);
			}
			if(dataGrid.getColumnIndex(brickLocationColumn) == -1)
			{
				dataGrid.addColumn(brickLocationColumn, "Brick Location");
			}
			if(dataGrid.getColumnIndex(brickSizeColumn) == -1)
			{
				dataGrid.addColumn(brickSizeColumn, "Brick Size");
			}
		}
		dataGrid.redraw();
	}
	
	private void setupMapForTrees(ArrayList<Tree> sampleTreeList2) 
	{
		String baseString = "http://maps.googleapis.com/maps/api/staticmap?center=C44.323526,%-93.973789&zoom=15&size=512x512&maptype=" 
	+ "hybrid&markers=color:blue%7Clabel:S%7C44.323526,-93.973789";
		for(Tree tree:sampleTreeList2)
		{
			String appendingString= "&markers=color:green%7Clabel:S%7C" + tree.getLatitude() + ", " 
					+ tree.getLongitude();

			baseString += appendingString;
		}
		baseString += "&sensor=false";
		
		image.setUrl(baseString);
	}
	
	private void setupMapForBenches(ArrayList<Bench> benches) 
	{
		String baseString = "http://maps.googleapis.com/maps/api/staticmap?center=C44.323526,%-93.973789&zoom=15&size=512x512&maptype=" 
	+ "hybrid&markers=color:blue%7Clabel:S%7C44.323526,-93.973789";
		for(Bench bench:benches)
		{
			String appendingString= "&markers=color:green%7Clabel:S%7C" + bench.getLatitude() + ", " 
					+ bench.getLongitude();

			baseString += appendingString;
		}
		baseString += "&sensor=false";
		
		image.setUrl(baseString);
	}

	private boolean currentConfigurationIsAcceptable() 
	{
		if(!checkBoxQuadrantA.getValue() && !checkBoxQuadrantB.getValue() && !checkBoxQuadrantC.getValue() 
				&& ! checkBoxQuadrantD.getValue() && !checkBoxQuadrantE.getValue())
		{
			Window.alert("At least one quadrant must be selected.");
			return false;
		}
		if(inListBox.getSelectedIndex() == -1)//I don't think this is possible using dropdown box, but included to be safe
		{
			Window.alert("At least one of \"Trees\", \"Benches\", or \"Bricks\" must be selected.");
			return false;
		}
		String searchInSelectedText = inListBox.getItemText(inListBox.getSelectedIndex());
		String searchForSelectedText = byListBox.getItemText(byListBox.getSelectedIndex());
		
		if((searchInSelectedText.equals("Benches") || searchInSelectedText.equals("Bricks")) 
				&& searchForSelectedText.equals("Common Name") || searchForSelectedText.equals("Scientific Name"))
		{
			Window.alert("Cannot search by \"Common Name\" or \"Scientific Name\" when \"Bricks\" or " +
					"\"Benches\" is selected.");
			return false;
		}
		return true;
	}
	
	private SearchParameter extractSearchParameterFromConfiguration() {
		SearchFor searchFor = SearchFor.trees;
		
		if(inListBox.getItemText(inListBox.getSelectedIndex()).equals("Trees"))
			searchFor = SearchFor.trees;
		if(inListBox.getItemText(inListBox.getSelectedIndex()).equals("Benches"))
			searchFor = SearchFor.benches;
		if(inListBox.getItemText(inListBox.getSelectedIndex()).equals("Bricks"))
			searchFor = SearchFor.bricks;
		        			
		SearchType searchType = SearchType.commonName;
		
		if(byListBox.getItemText(byListBox.getSelectedIndex()).equals("Donated For"))
			searchType = SearchType.donatedFor;
		if(byListBox.getItemText(byListBox.getSelectedIndex()).equals("Common Name"))
			searchType = SearchType.commonName;
		if(byListBox.getItemText(byListBox.getSelectedIndex()).equals("Scientific Name"))
			searchType = SearchType.scientificName;
		
		ArrayList<Quadrant> quadrants = new ArrayList<Quadrant>();
		
		if(checkBoxQuadrantA.getValue())
			quadrants.add(Quadrant.A);
		if(checkBoxQuadrantB.getValue())
			quadrants.add(Quadrant.B);
		if(checkBoxQuadrantC.getValue())
			quadrants.add(Quadrant.C);
		if(checkBoxQuadrantD.getValue())
			quadrants.add(Quadrant.D);
		if(checkBoxQuadrantE.getValue())
			quadrants.add(Quadrant.E);
		
		SearchParameter newParam = new SearchParameter(searchKeywordsTextBox.getText(), 
				searchFor, 
				searchType, 
				quadrants,
				false);
		return newParam;
	}
}
