package edu.gac.arboretumweb.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;

import edu.gac.arboretumweb.shared.domain.Tree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class TreePage implements EntryPoint {
	
	private Tree tree;
	
	final RootPanel rootPanel = RootPanel.get();
	AbsolutePanel absolutePanel = new AbsolutePanel();
	
	Button buttonSatelliteMap = new Button("Satellite Map");
	Button buttonRoadMap = new Button("Road Map");
	Button buttonHybridMap = new Button("Hybrid Map");
	Button buttonHomePage = new Button("Home Page");
	Button buttonBack = new Button("Back");
	
	Float treeLatitude;
	Float treeLongitude;
	
	String zoom = "16";
	String mapType = "road";
	
	String url = null;
	
	Image image = new Image("nothing");
	
	Label labelYourTree = new Label("Your Tree");
	Label labelCommonName = new Label("Common Name");
	Label labelGetCommonName = new Label("");
	Label labelScientificName = new Label("Scientific Name");
	Label labelGetScientificName = new Label("");
	Label labelYearPlanted = new Label("Year Planted");
	Label labelGetYearPlanted = new Label("");
	Label labelYearDonated = new Label("Year Donated");
	Label labelGetYearDonated = new Label("");
	Label labelDonatedFor = new Label("Donated For");
	Label labelGetDonatedFor = new Label("");
	
	//not used in the actual application, but this allows the designer to still be used
	@Override
	public void onModuleLoad() 
	{
		show(tree, new SearchParameter());
	}
	
	public void show(Tree tree, final SearchParameter sp)
	{
		treeLatitude = (float) Double.valueOf(tree.getLatitude()).doubleValue();
		treeLongitude = (float) Double.valueOf(tree.getLongitude()).doubleValue();

		zoom = checkSize(treeLatitude, treeLongitude);
		createMap(tree, zoom);

		rootPanel.add(absolutePanel, 10, 10);
		
		labelGetCommonName.setText(tree.getCommonName());
		labelGetScientificName.setText(tree.getScientificName());
		labelGetYearPlanted.setText(tree.getYearPlanted());
		labelGetYearDonated.setText(tree.getYearDonated());
		labelGetDonatedFor.setText(tree.getDonatedFor());
		
		//This handler should be in the constructor of the BenchPage, however, the constructor does not have knowledge of the SearchParameter 
		// Not sure as to the best way around this.  Having it this way, a new clickHandler will be added to the back button every time the
		// bench page is shown.  This means that if it is shown n times, then the back button is clicked, n SearchResultsPage show() methods
		// will be run.  
		buttonBack.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rootPanel.clear();
				PageController.sharedPageController().showSearchResultsPage(sp);
			}
		});
	}

	public TreePage()
	{
		rootPanel.setSize("1600", "900");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		absolutePanel.setSize("1004px", "822px");
		absolutePanel.add(labelYourTree, 480, 0);
		
		absolutePanel.add(image);
		image.setSize("479px", "409px");
		
		//adding buttons
		absolutePanel.add(buttonSatelliteMap, 364, 453);
		buttonSatelliteMap.setSize("115px", "55px");
		absolutePanel.add(buttonHomePage, 889, 50);
		buttonHomePage.setSize("115px", "55px");
		absolutePanel.add(buttonBack, 889, 152);
		buttonBack.setSize("112px", "55px");
		absolutePanel.add(buttonRoadMap, 10, 453);
		buttonRoadMap.setSize("115px", "55px");
		absolutePanel.add(buttonHybridMap, 180, 453);
		buttonHybridMap.setSize("115px", "55px");
		
		//adding labels
		absolutePanel.add(labelCommonName, 560, 50);
		labelCommonName.setSize("92px", "18px");
		absolutePanel.add(labelGetCommonName, 560, 68);
		absolutePanel.add(labelScientificName, 560, 118);
		absolutePanel.add(labelGetScientificName, 560, 136);
		absolutePanel.add(labelYearPlanted, 560, 171);
		absolutePanel.add(labelGetYearPlanted, 560, 189);
		absolutePanel.add(labelYearDonated, 560, 230);
		absolutePanel.add(labelGetYearDonated, 560, 248);
		absolutePanel.add(labelDonatedFor, 560, 297);
		absolutePanel.add(labelGetDonatedFor, 560, 315);

		buttonHybridMap.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				mapType = "hybrid";
				createMap(tree, zoom);
			}
		});

		buttonRoadMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mapType = "road";
				createMap(tree, zoom);
			}
		});
		
		buttonHomePage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rootPanel.clear();
				PageController.sharedPageController().showMainPage();
			}
		});

		buttonSatelliteMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mapType = "satellite";
				createMap(tree, zoom);
			}
		});
	}
	
	
	
	private String checkSize(Float treeLatitude, Float treeLongitude) {
		if (treeLatitude > 44.32252 || treeLongitude > -93.974755){
			return "16";
		}
		return"17";
	}

	private void createMap(final Tree tree, String zoom) {
		String url = new String("http://maps.googleapis.com/maps/api/staticmap?center=C44.323526,%-93.973789&zoom=" + zoom + 
				"&size=512x512&maptype=" + mapType + "&markers=color:blue%7Clabel:S%7C44.323526,-93.973789&markers=color:green%7Clabel:G%7C" + 
				tree.getLatitude() + "," + tree.getLongitude() + "&sensor=false");
		image.setUrl(url);
	}
}


//Quad 1 : Lat <= 44.319568
//Quad 2 : 44.319568 <= Lat <= 44.320024 
//Quad 3 : 44.320024 <= Lat <= 44.320901
//Quad 4 : 44.320901 <= Lat <= 44.321641
//Quad 5 : 44.321641 <= Lat <= 44.326000

