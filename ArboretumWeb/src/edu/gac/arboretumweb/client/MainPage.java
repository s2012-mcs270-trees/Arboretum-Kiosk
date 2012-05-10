package edu.gac.arboretumweb.client;

import java.util.ArrayList;
import edu.gac.arboretumweb.client.SearchParameter.Quadrant;
import edu.gac.arboretumweb.client.SearchParameter.SearchFor;
import edu.gac.arboretumweb.client.SearchParameter.SearchType;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * This is the page that loads when the application is started.  It is responsible for taking in parameters for a search which consist of 
 * String query - the search text
 * ArrayList<SearchFor> - the list of Benches, Bricks, and/or Trees which specifies what the search is looking for
 * SearchType - the value (i.e. "Common Name", "Donated For", etc. that the search is applying the query string to search for
 * Donated between two integer years - lowerLimit and upperLimit
 * ArrayList<Quadrant> - A, B, C, D defined by the map that shows on the mainPage
 */
public class MainPage implements EntryPoint, ClickHandler, Page {
	
	//All variable are declared above the show method to make reading the code easier
	
	RootPanel rootPanel = RootPanel.get();
	AbsolutePanel absolutePanel = new AbsolutePanel();
	
	final String INCONSISTENT_SEARCH_CRITERIA = "Inconsistent Search Criteria";
	//TODO: once it is determined exactly what criteria is searchable, update this string so that if it were 
	// ever encountered, the user could see the rules for the criteria and determine what is wrong in their
	// search
	
	//any variables that must be referenced inside a handler must be declared final
	final RadioButton commonNameSearchTypeRadioButton = new RadioButton("Search Type", "Common Name");
	final RadioButton sizeSearchTypeRadioButton = new RadioButton("Search Type", "Size");
	final RadioButton donatedForSearchTypeRadioButton = new RadioButton("Search Type", "Donated For");
	final RadioButton scientificNameSearchTypeRadioButton = new RadioButton("Search Type", "Scientific Name");
	
	final CheckBox checkBoxBenches = new CheckBox("Benches");
	final CheckBox checkBoxTrees = new CheckBox("Trees");
	final CheckBox checkBoxBricks = new CheckBox("Bricks");
	private final CheckBox checkBoxNotYetDonated = new CheckBox("Bricks");
	
	final TextBox searchField = new TextBox();
	final TextBox lowerLimitYearDonatedTextBox = new TextBox();
	final TextBox upperLimitYearDonatedTextBox = new TextBox();
	
	Label labelIn = new InlineLabel("In");
	Label labelBy = new InlineLabel("By");
	Label labelQuery = new InlineLabel("Query");
	Label labelHeader = new InlineLabel("Welcome to the Gustavus Adolphus College Arboretum Tree Search Application!");
	Label labelDonatedBetween = new Label("Donated Between");
	Label labelAnd = new Label("and");
	
	Button searchButton = new Button("Search");
	
	public void onModuleLoad() 
	{
		//This method of calling calling the page is used only for debugging purposes.  The SearchResultsPage will actually
		//be instantiated by the PageController which will also be responsible for opening it when necessary
		this.show();
	}

	//not sure what this is for
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
	}

	public void show() 
	{
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		rootPanel.setSize("1600", "900");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.add(absolutePanel, 0, 0);
		absolutePanel.setSize("962px", "636px");

		//set the default text that will appear in the searchField
		searchField.setText("search");
		
		//Adds the radio buttons to the absolutePanel (i.e. "Donor", "Common Name", "Scientific Name", etc. 
		absolutePanel.add(commonNameSearchTypeRadioButton, 492, 244);
		absolutePanel.add(sizeSearchTypeRadioButton, 492, 218);
		sizeSearchTypeRadioButton.setSize("113px", "20px");
		absolutePanel.add(donatedForSearchTypeRadioButton, 492, 192);
		absolutePanel.add(scientificNameSearchTypeRadioButton, 492, 270);
		checkBoxTrees.setStyleName("h1");
		absolutePanel.add(checkBoxTrees, 337, 192);
				
		//add a click handler so that when the Bricks checkBox is checked/unchecked, the corresponding
		//radioButtons that can/can't be selected are enabled or disabled.  For example, if someone
		//wants to search for bricks, the radioButton they select can't be search by "Common Name"
		//TODO: make organize these better.  There need only be one private method that checks all
		// 8 possible scenarios and handles each case appropriately
		checkBoxBricks.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(checkBoxBricks.getValue())//Brick checkBox was enabled
				{
					commonNameSearchTypeRadioButton.setEnabled(false);
					scientificNameSearchTypeRadioButton.setEnabled(false);
					
					if(!checkBoxTrees.getValue() && !checkBoxBenches.getValue())
						sizeSearchTypeRadioButton.setEnabled(true);
				}
				else//brick checkBox was disabled
				{
					if(checkBoxTrees.getValue() && !checkBoxBenches.getValue())
					{
						commonNameSearchTypeRadioButton.setEnabled(true);
						scientificNameSearchTypeRadioButton.setEnabled(true);
					}
				}
			}
		});
		
		absolutePanel.add(checkBoxBricks, 337, 244);
		checkBoxBricks.setSize("71px", "20px");
		
		//Again, add the click handler
		checkBoxBenches.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(checkBoxBenches.getValue())//if it is checked, be sure that commonName and scientific name and size are not searchable
				{
					commonNameSearchTypeRadioButton.setEnabled(false);
					scientificNameSearchTypeRadioButton.setEnabled(false);
					sizeSearchTypeRadioButton.setEnabled(false);
				}
				else//benches check button was unchecked by the event
				{
					if(checkBoxTrees.getValue() && !checkBoxBricks.getValue())//trees is checked but not bricks
					{
						commonNameSearchTypeRadioButton.setEnabled(true);
						scientificNameSearchTypeRadioButton.setEnabled(true);
						sizeSearchTypeRadioButton.setEnabled(false);
					}
					else if(!checkBoxTrees.getValue() && checkBoxBricks.getValue())//bricks are checked by not trees
					{
						commonNameSearchTypeRadioButton.setEnabled(false);
						scientificNameSearchTypeRadioButton.setEnabled(false);
						sizeSearchTypeRadioButton.setEnabled(true);
					}
					else if(checkBoxTrees.getValue() && checkBoxBricks.getValue())//both are checked
					{
						sizeSearchTypeRadioButton.setEnabled(false);
						commonNameSearchTypeRadioButton.setEnabled(false);
						scientificNameSearchTypeRadioButton.setEnabled(false);
					}
				}
			}
		});
		
		checkBoxTrees.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(checkBoxTrees.getValue())//if it is checked, we will not allow a search by size (available for bricks only)
				{
					sizeSearchTypeRadioButton.setEnabled(false);
					if(!checkBoxBenches.getValue() && ! checkBoxBricks.getValue())
					{
						commonNameSearchTypeRadioButton.setEnabled(true);
						scientificNameSearchTypeRadioButton.setEnabled(true);
					}
				}
				else
				{
					if(checkBoxBenches.getValue())//common Name, scientific name and size are all not searchable if benches is checked
					{
						commonNameSearchTypeRadioButton.setEnabled(false);
						scientificNameSearchTypeRadioButton.setEnabled(false);
						sizeSearchTypeRadioButton.setEnabled(false);
					}
					else if(checkBoxBricks.getValue())//neither benches nor trees are checks
						sizeSearchTypeRadioButton.setEnabled(true);
					
				}
			}
		});
		absolutePanel.add(checkBoxBenches, 337, 218);
		
		checkBoxNotYetDonated.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(checkBoxNotYetDonated.getValue())
				{
					donatedForSearchTypeRadioButton.setEnabled(false);
					if(donatedForSearchTypeRadioButton.getValue())
						donatedForSearchTypeRadioButton.setValue(false);
				}
					
				if(!checkBoxNotYetDonated.getValue())
					donatedForSearchTypeRadioButton.setEnabled(true);
			}
		});
		checkBoxNotYetDonated.setHTML("Not Yet Donated");
		absolutePanel.add(checkBoxNotYetDonated, 618, 192);
		checkBoxNotYetDonated.setSize("136px", "20px");

		//Sets up the labels for "In", "By", and "Query" that go above the appropriate search criteria
		absolutePanel.add(labelIn, 337, 158);
		labelIn.setStyleName("sendButton");
		absolutePanel.add(labelBy, 492, 158);
		labelBy.setStyleName("sendButton");
		absolutePanel.add(labelQuery, 80, 158);
		labelQuery.setStyleName("sendButton");

		//Sets up the header of the application main page
		absolutePanel.add(labelHeader, 222, 10);
		labelHeader.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		labelHeader.setStyleName("sendButton");
		labelHeader.setSize("532px", "50px");

		//Sets up the position, size, style, and initial String contained the the boxes
		// for inputting the lower and upper bounds for the donated year
		absolutePanel.add(lowerLimitYearDonatedTextBox, 259, 367);
		lowerLimitYearDonatedTextBox.setStyleName("h1");
		lowerLimitYearDonatedTextBox.setText("1851");
		lowerLimitYearDonatedTextBox.setSize("127px", "24px");
		absolutePanel.add(upperLimitYearDonatedTextBox, 475, 367);
		upperLimitYearDonatedTextBox.setText(String.valueOf(java.util.Calendar.YEAR));//TODO: get this to display current year
		upperLimitYearDonatedTextBox.setSize("118px", "18px");
		
		//add the labels for "Donated Between" and "AND"
		absolutePanel.add(labelDonatedBetween, 80, 361);
		labelDonatedBetween.setStyleName("sendButton");
		labelDonatedBetween.setSize("173px", "30px");
		absolutePanel.add(labelAnd, 408, 367);
		labelAnd.setStyleName("sendButton");
		labelAnd.setSize("61px", "34px");
		
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the searchButton.
			 */
			public void onClick(ClickEvent event) 
			{
				if(checkCurrentSearchConfigurationForConsistency())
				{//this could/perhaps should be moved to a private method for readability
					SearchType searchBy = SearchType.commonName;//default
					
					if(commonNameSearchTypeRadioButton.getValue())
						searchBy = SearchType.commonName;
					if(scientificNameSearchTypeRadioButton.getValue())
						searchBy = SearchType.scientificName;
					if(donatedForSearchTypeRadioButton.getValue())
						searchBy = SearchType.donatedFor;

					ArrayList<SearchFor>  searchTypes = new ArrayList<SearchFor>();//the ArrayList to pass the
																					//constructor of SearchParameter

					if(checkBoxTrees.getValue())
						searchTypes.add(SearchFor.trees);
					
					if(checkBoxBenches.getValue())
						searchTypes.add(SearchFor.benches);

					if(checkBoxBricks.getValue())
						searchTypes.add(SearchFor.bricks);
					
					SearchParameter searchParameter = new SearchParameter(searchField.getText(), 
							searchTypes, 
							searchBy, 
							Quadrant.C, 
							Integer.valueOf(lowerLimitYearDonatedTextBox.getText()), 
							Integer.valueOf(upperLimitYearDonatedTextBox.getText()));
					
					rootPanel.clear();
					PageController.sharedPageController().showSearchResultsPage(searchParameter);
				}
				else
					Window.alert(INCONSISTENT_SEARCH_CRITERIA);				
			}
			
			private boolean checkCurrentSearchConfigurationForConsistency() {
				// TODO write this method
				return true;
			}

			//TODO: implement the parameter for Quadrant so that an ArrayList of Quadrants are passed so that the user can select more than one
			
			//not sure what this is for
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub

			}
		}

		absolutePanel.add(searchButton, 663, 442);
		searchButton.setStyleName("h1");
		searchButton.addStyleName("searchButton");
		searchButton.setSize("134px", "56px");
		searchField.setFocus(true);

		// Add a handler to send the search parameter data to the SearchResultsPage when the user presses "Search"
		MyHandler handler = new MyHandler();
		searchField.addKeyUpHandler(handler);
		searchButton.addClickHandler(handler);
		absolutePanel.add(searchField, 80, 204);
	}

	@Override
	public void close() 
	{		
		rootPanel.clear();
	}

	@Override
	public void reload()
	{

	}
}	