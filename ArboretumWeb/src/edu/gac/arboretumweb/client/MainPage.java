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
public class MainPage implements EntryPoint, ClickHandler {
	
	//All variable are declared above the show method to make reading the code easier
	
	RootPanel rootPanel = RootPanel.get();
	AbsolutePanel absolutePanel = new AbsolutePanel();
	
	final String INCONSISTENT_SEARCH_CRITERIA = "Inconsistent Search Criteria";
	//TODO: once it is determined exactly what criteria is searchable, update this string so that if it were 
	// ever encountered, the user could see the rules for the criteria and determine what is wrong in their
	// search
	
	//any variables that must be referenced inside a handler must be declared final
	final RadioButton commonNameSearchTypeRadioButton = new RadioButton("Search Type", "Common Name");
	final RadioButton donatedForSearchTypeRadioButton = new RadioButton("Search Type", "Donated For");
	final RadioButton scientificNameSearchTypeRadioButton = new RadioButton("Search Type", "Scientific Name");
	
	final RadioButton benchesRadioButton = new RadioButton("Search For", "Benches");
	final RadioButton treesRadioButton = new RadioButton("Search For", "Trees");
	final RadioButton bricksRadioButton = new RadioButton("Search For", "Bricks");
	private final CheckBox checkBoxNotYetDonated = new CheckBox("Bricks");
	
	CheckBox checkBoxQuadrantA = new CheckBox("A");
	CheckBox checkBoxQuadrantB = new CheckBox("B");
	CheckBox checkBoxQuadrantC = new CheckBox("C");
	CheckBox checkBoxQuadrantD = new CheckBox("D");
	CheckBox checkBoxQuadrantE = new CheckBox("E");
	
	final TextBox searchField = new TextBox();
	
	Label labelIn = new InlineLabel("In");
	Label labelBy = new InlineLabel("By");
	Label labelQuery = new InlineLabel("Query");
	Label labelHeader = new InlineLabel("Welcome to the Gustavus Adolphus College Arboretum Tree Search Application!");
	Label labelLocatedInQuadrant = new Label("Located In Quadrant(s)");
	
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
			
		//Adds the Quadrant checkBoxes and sets their initial values
		absolutePanel.add(checkBoxQuadrantA, 663, 192);
		absolutePanel.add(checkBoxQuadrantB, 663, 218);
		absolutePanel.add(checkBoxQuadrantC, 663, 244);
		absolutePanel.add(checkBoxQuadrantD, 732, 192);
		absolutePanel.add(checkBoxQuadrantE, 732, 218);
		checkBoxQuadrantA.setValue(true);
		checkBoxQuadrantB.setValue(true);
		checkBoxQuadrantC.setValue(true);
		checkBoxQuadrantD.setValue(true);
		checkBoxQuadrantE.setValue(true);
		
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
		absolutePanel.add(checkBoxNotYetDonated, 492, 270);
		checkBoxNotYetDonated.setSize("136px", "20px");

		//Sets up the labels for "In", "By", "Query", and "Located in Quadrant(s)" that go above the appropriate search criteria
		absolutePanel.add(labelIn, 337, 158);
		labelIn.setStyleName("sendButton");
		absolutePanel.add(labelBy, 492, 158);
		labelBy.setStyleName("sendButton");
		absolutePanel.add(labelQuery, 80, 158);
		labelQuery.setStyleName("sendButton");
		labelLocatedInQuadrant.setStyleName("sendButton");
		absolutePanel.add(labelLocatedInQuadrant, 663, 158);
		
		//Sets up the header of the application main page
		absolutePanel.add(labelHeader, 222, 10);
		labelHeader.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		labelHeader.setStyleName("sendButton");
		labelHeader.setSize("532px", "50px");
		
		/**
		 * Click handler for the Trees, Benches, and Bricks radioButtons
		 * @author Matt
		 *
		 */
		class MySearchForRadioButtonHandler implements ClickHandler{
			public void onClick(ClickEvent event)
			{
				checkCurrentSearchConfiguration();
			}
			/**
			 * Checks to make sure that the current search configuration is valid 
			 * i.e. makes sure you can't search by common name if brick is selected
			 */
			private void checkCurrentSearchConfiguration() {
				if(!bricksRadioButton.getValue() && !benchesRadioButton.getValue())
				{
					commonNameSearchTypeRadioButton.setEnabled(true);
					scientificNameSearchTypeRadioButton.setEnabled(true);
				}
				else
				{
					commonNameSearchTypeRadioButton.setEnabled(false);
					scientificNameSearchTypeRadioButton.setEnabled(false);

					if(commonNameSearchTypeRadioButton.getValue())
						commonNameSearchTypeRadioButton.setValue(false);
					if(scientificNameSearchTypeRadioButton.getValue())
						scientificNameSearchTypeRadioButton.setValue(false);
				}
			}
		}

		//Adds the radio buttons to the absolutePanel (i.e. "Donor", "Common Name", "Scientific Name", etc. 
		absolutePanel.add(commonNameSearchTypeRadioButton, 492, 218);
		absolutePanel.add(donatedForSearchTypeRadioButton, 492, 192);
		absolutePanel.add(scientificNameSearchTypeRadioButton, 492, 244);
		treesRadioButton.setHTML("Trees");
		treesRadioButton.setStyleName("h1");
		absolutePanel.add(treesRadioButton, 337, 192);
		bricksRadioButton.setHTML("Bricks");
		absolutePanel.add(bricksRadioButton, 337, 244);
		bricksRadioButton.setSize("71px", "20px");
		benchesRadioButton.setHTML("Benches");
		absolutePanel.add(benchesRadioButton, 337, 218);
		treesRadioButton.addClickHandler(new MySearchForRadioButtonHandler());
		benchesRadioButton.addClickHandler(new MySearchForRadioButtonHandler());
		bricksRadioButton.addClickHandler(new MySearchForRadioButtonHandler());
		
		// Create a handler for the sendButton and nameField
		class MySendButtonHandler implements ClickHandler, KeyUpHandler {
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

					SearchFor  searchFor = SearchFor.trees; //the ArrayList to pass the constructor of SearchParameter

					if(treesRadioButton.getValue())
						searchFor = SearchFor.trees;
					if(benchesRadioButton.getValue())
						searchFor = SearchFor.benches;
					if(bricksRadioButton.getValue())
						searchFor = SearchFor.bricks;
					
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
					
					SearchParameter searchParameter = new SearchParameter(searchField.getText(), 
							searchFor, 
							searchBy, 
							quadrants,
							checkBoxNotYetDonated.getValue());
					
					rootPanel.clear();
					PageController.sharedPageController().showSearchResultsPage(searchParameter);
				}		
			}
			
			private boolean checkCurrentSearchConfigurationForConsistency() {
				System.out.println(checkBoxQuadrantA.getValue());
				System.out.println(checkBoxQuadrantB.getValue());
				System.out.println(checkBoxQuadrantC.getValue());
				System.out.println(checkBoxQuadrantD.getValue());
				System.out.println(checkBoxQuadrantE.getValue());
				
				if(!checkBoxQuadrantA.getValue() && !checkBoxQuadrantB.getValue() && !checkBoxQuadrantC.getValue() 
						&& ! checkBoxQuadrantD.getValue() && !checkBoxQuadrantE.getValue())
				{
					Window.alert("At least one quadrant must be selected.");
					return false;
				}
				if(!benchesRadioButton.getValue() && !bricksRadioButton.getValue() && ! treesRadioButton.getValue())
				{
					Window.alert("At least one of \"Trees\", \"Benches\", or \"Bricks\" must be selected.");
					return false;
				}
				
				
				return true;				
			}
			
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
		MySendButtonHandler handler = new MySendButtonHandler();
		searchField.addKeyUpHandler(handler);
		searchButton.addClickHandler(handler);
		absolutePanel.add(searchField, 80, 204);
	}
}	