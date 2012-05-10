package edu.gac.arboretumweb.client;


import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.gac.arboretumweb.client.SearchParameter.SearchType;
import edu.gac.arboretumweb.shared.FieldVerifier;
import edu.gac.arboretumweb.shared.domain.Tree;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ArboretumWeb implements EntryPoint, ClickHandler {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	public void onModuleLoad() 
	{            
		String spreadSheetURL = "https://spreadsheets.google.com/feeds/worksheets/0AjkI1jvOdpNzdEYwZEc5Zk5nbHIwdlJmSUtMaVJRT3c/public/values";
	
		//final TreeFinderV2 treeFinder = new TreeFinderV2(spreadSheetURL);
		final Button searchButton = new Button("Search");
		searchButton.setStyleName("h1");
		final TextBox searchField = new TextBox();
		searchField.setText("search for");
		final Label errorLabel = new Label();
		
		// We can add style names to widgets
		searchButton.addStyleName("searchButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1600", "900");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		rootPanel.add(searchField, 258, 194);
		
		RootPanel.get("sendButtonContainer").add(searchButton, 768, 335);
		searchButton.setSize("134px", "56px");
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the search field when the app loads
		searchField.setFocus(true);
		
		//Creates the check boxes for either trees or benches
		//TODO make this exandable to an arbitrary number of "type" based on the number of 
		//sheets in the google docs spread sheet.  After this is done, the GetResults... method will
		//have to be updated
		CheckBox checkBoxTrees = new CheckBox("Trees");
		rootPanel.add(checkBoxTrees, 454, 194);
		
		CheckBox checkBoxBenches = new CheckBox("Benches");
		rootPanel.add(checkBoxBenches, 454, 245);
		
		//Creates the radio buttons for the search type (i.e. "Donor", "Common Name", "Scientific Name", etc. 
		
		final RadioButton commonNameSearchTypeRadioButton = new RadioButton("Search Type", "Common Name");
		rootPanel.add(commonNameSearchTypeRadioButton, 548, 220);
		
		final RadioButton scientificNameSearchTypeRadioButton = new RadioButton("Search Type", "Scientific Name");
		rootPanel.add(scientificNameSearchTypeRadioButton, 548, 245);
		
		final RadioButton donatedForSearchTypeRadioButton = new RadioButton("Search Type", "Donated For");
		rootPanel.add(donatedForSearchTypeRadioButton, 548, 194);
		
		final RadioButton yearDonatedSearchTypeRadioButton = new RadioButton("Search Type", "Year Donated");
		rootPanel.add(yearDonatedSearchTypeRadioButton, 548, 271);
		
		//Sets up the inline labels for "In", "By", and "Query" that go above the appropriate
		//search criteria
		InlineLabel inInlineLabel = new InlineLabel("In");
		inInlineLabel.setStyleName("sendButton");
		rootPanel.add(inInlineLabel, 454, 160);
		
		InlineLabel byInlineLabel = new InlineLabel("By");
		byInlineLabel.setStyleName("sendButton");
		rootPanel.add(byInlineLabel, 548, 160);
		
		InlineLabel queryInlineLabel = new InlineLabel("Query");
		queryInlineLabel.setStyleName("sendButton");
		rootPanel.add(queryInlineLabel, 258, 160);
		
		InlineLabel headerInlinelabel = new InlineLabel("Welcome to the Gustavus Adolphus College Arboretum Tree Search Application!");
		headerInlinelabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerInlinelabel.setStyleName("sendButton");
		rootPanel.add(headerInlinelabel, 258, 30);
		headerInlinelabel.setSize("532px", "50px");
		
		TextBox textBox = new TextBox();
		textBox.setStyleName("h1");
		textBox.setText("1851");
		rootPanel.add(textBox, 454, 361);
		textBox.setSize("109px", "18px");
		
		TextBox textBox_1 = new TextBox();
		textBox_1.setText("2012");
		rootPanel.add(textBox_1, 624, 361);
		textBox_1.setSize("118px", "18px");
		
		Label lblAnd = new Label("and");
		lblAnd.setStyleName("sendButton");
		rootPanel.add(lblAnd, 579, 361);
		lblAnd.setSize("61px", "34px");
		
		Label lblDonatedBetween = new Label("Donated Between");
		lblDonatedBetween.setStyleName("sendButton");
		rootPanel.add(lblDonatedBetween, 258, 361);
		lblDonatedBetween.setSize("173px", "30px");
		
		searchField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				searchButton.setEnabled(true);
				searchButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the searchButton.
			 */
			public void onClick(ClickEvent event) {
				SearchType type;
				
				if (scientificNameSearchTypeRadioButton.getValue() == true)
					type = SearchType.scientificName;
				else if (commonNameSearchTypeRadioButton.getValue() == true)
					type = SearchType.commonName;
				else if (yearDonatedSearchTypeRadioButton.getValue())
					type = SearchType.yearDonated;
				else
					type = null;//default search
				
				ArrayList<Tree> resultsList = new ArrayList<Tree>();
				
				//resultsList = treeFinder.getSearchResultsForSimpleSearch(searchField.getText(), type);
				
				if (type != null)
					sendQueryDataToServer(type.toString());
				else 
					sendQueryDataToServer(resultsList.toString());
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendQueryDataToServer("");
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendQueryDataToServer(final String searchType) {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = searchField.getText();
				if (!FieldVerifier.isValidName(textToServer)) 
				{
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				searchButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				
				dialogBox.setText("Remote Procedure Call - Failure");
				serverResponseLabel.addStyleName("serverResponseLabelError");
				serverResponseLabel.setHTML("You searched:\n\tFOR " + searchField.getText() + 
				"\n\tIN Trees (default) " + "\n\tBY " + searchType);
				dialogBox.center();
				closeButton.setFocus(true);
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		searchButton.addClickHandler(handler);
		searchField.addKeyUpHandler(handler);
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
}	

