package edu.gac.arboretumweb.client;

import java.util.ArrayList;
import java.util.Comparator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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

import edu.gac.arboretumweb.shared.domain.Tree;

public class SearchResultsPage implements EntryPoint
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
	
	@Override
	/**
	 * @wbp.parser.entryPoint
	 */
	public void onModuleLoad() 
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
		
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setWidth("1200");
		rootPanel.add(dataGrid, 10, 220);
		dataGrid.setSize("649px", "308px");
		dataGrid.setWidth("100%");
		
		// Attach a column sort handler to the ListDataProvider to sort the list of each column 
		// as needed (copied from example code) (not working)
		// TODO: get the sorter to work or erase the code for it if deemed not essential
	    ListHandler<Tree> sortHandler =
	        new ListHandler<Tree>(sampleTreelist);
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
		
		// Create a Pager to control the table.  This came straight from the example
		// I followed. I don't believe it adds any functionality to our project at all.
		// However, I kept it hoping that it was somehow responsible for paging our results
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(dataGrid);
	    pager.setVisible(true);
	    
	    // Add a selection model to handle the user clicking or touching a row selection 
	    // (corresponding to a tree being selected, 'selectedTree')
	    final SingleSelectionModel<Tree> selectionModel = new SingleSelectionModel<Tree>();
	    dataGrid.setSelectionModel(selectionModel);
	    
	    Label searchResultsLabel = new Label("Search Results for: ");
	    rootPanel.add(searchResultsLabel, 86, 20);
	    
	    Label inLabel = new Label("In:");
	    rootPanel.add(inLabel, 184, 57);
	    inLabel.setSize("14px", "24px");
	    
	    Label byLabel = new Label("By:");
	    rootPanel.add(byLabel, 184, 87);
	    byLabel.setSize("20px", "24px");
	    
	    Label locatedInQuadrantLabel = new Label("Located in Quadrant:");
	    rootPanel.add(locatedInQuadrantLabel, 77, 117);
	    locatedInQuadrantLabel.setSize("121px", "24px");
	    
	    Label label = new Label("");
	    rootPanel.add(label, 142, 93);
	    
	    Label donatedBetweenLabel = new Label("Donated Between:");
	    rootPanel.add(donatedBetweenLabel, 92, 147);
	    
	    TextBox earliestSearchYearTextBox = new TextBox();
	    rootPanel.add(earliestSearchYearTextBox, 202, 147);
	    earliestSearchYearTextBox.setSize("57px", "6px");
	    
	    Label andLabel = new Label("AND");
	    rootPanel.add(andLabel, 275, 147);
	    
	    TextBox latestSearchYearTextBox = new TextBox();
	    rootPanel.add(latestSearchYearTextBox, 308, 147);
	    latestSearchYearTextBox.setSize("57px", "6px");
	    
	    TextBox searchKeywordsTextBox = new TextBox();
	    rootPanel.add(searchKeywordsTextBox, 204, 10);
	    searchKeywordsTextBox.setSize("222px", "12px");
	    
	    ListBox inListBox = new ListBox();
	    inListBox.addItem("Trees", "Trees");
	    inListBox.addItem("Benches", "Benches");
	    inListBox.addItem("Stones", "Stones");
	    rootPanel.add(inListBox, 204, 53);
	    inListBox.setSize("171px", "22px");
	    inListBox.setVisibleItemCount(1);
	    
	    ListBox byListBox = new ListBox();
	    byListBox.addItem("Donated For");
	    byListBox.addItem("Common Name");
	    byListBox.addItem("Scientific Name");
	    rootPanel.add(byListBox, 204, 85);
	    byListBox.setSize("171px", "22px");
	    byListBox.setVisibleItemCount(1);
	    
	    ListBox quadrantListBox = new ListBox();
	    quadrantListBox.addItem("A", "A");
	    quadrantListBox.addItem("B", "B");
	    quadrantListBox.addItem("C", "C");
	    quadrantListBox.addItem("D", "D");
	    rootPanel.add(quadrantListBox, 204, 115);
	    quadrantListBox.setSize("171px", "22px");
	    quadrantListBox.setVisibleItemCount(1);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() 
	    {
	    	public void onSelectionChange(SelectionChangeEvent event) 
	    	{
	    		Tree selectedTree = selectionModel.getSelectedObject();
	    		if (selectedTree != null) 
	    		{
	    			Window.alert("You selected: " + selectedTree.getCommonName());
	    			//TODO: implement this event to go the the page of the selectedTree
	    		}
	    	}
	    });
	    
	    // TODO: have Tim add a map somewhere on this results page with stars showing where the trees that were hits to the 
	    // search
	    
	    // TODO: implement a way to see exactly what the search parameters were that were used to generate the search.  I didn't 
	    // implement this since we haven't implemented a page handler for the different pages required for the project
	}
}
