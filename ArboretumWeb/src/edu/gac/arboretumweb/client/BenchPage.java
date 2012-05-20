package edu.gac.arboretumweb.client;



// ADD QUADRANT FOR BENCH
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;

import edu.gac.arboretumweb.shared.domain.Bench;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class BenchPage implements EntryPoint {

	Bench bench;
	final RootPanel rootPanel = RootPanel.get();

	Button buttonRoadMap = new Button("Road Map");
	Button buttonHybridMap = new Button("Hybrid Map");
	Button buttonSatelliteMap = new Button("Satellite Map");
	Button buttonHomePage = new Button("Home Page");
	Button buttonBack = new Button("Back");

	Label labelYourBench = new Label("Your Bench");
	Label labelDonatedFor = new Label("Donated For");
	Label labelYearDonated = new Label("Year Donated");
	Label labelBenchDonor = new Label("");
	Label labelBenchGetYearDonated = new Label("");

	//The zoom and mapType Strings are used in the url string that produces the static google maps image
	String zoom;
	String mapType = "road"; //to be added to the google maps URL where mapType is either "hybrid", "road", or "satellite"
	String url = new String("nothing");

	Image image = new Image((String) url);

	Float benchLatitude;
	Float benchLongitude;

	private final AbsolutePanel absolutePanel = new AbsolutePanel();

	@Override
	public void onModuleLoad() 
	{
		
		show(bench, new SearchParameter());
	}

	public BenchPage()
	{
		rootPanel.setSize("1600", "900");
		absolutePanel.setSize("861px", "770px");

		absolutePanel.add(image);
		image.setSize("479px", "409px");

		//adding the labels
		absolutePanel.add(labelYourBench, 499, 0);
		labelYourBench.setSize("66px", "18px");
		absolutePanel.add(labelDonatedFor, 499, 47);
		absolutePanel.add(labelYearDonated, 499, 71);
		absolutePanel.add(labelBenchDonor, 499, 113);
		absolutePanel.add(labelBenchGetYearDonated, 499, 157);

		//adding the buttons
		absolutePanel.add(buttonHomePage, 687, 120);
		buttonHomePage.setSize("115px", "55px");
		absolutePanel.add(buttonBack, 686, 26);
		buttonBack.setSize("112px", "55px");
		absolutePanel.add(buttonRoadMap, 10, 454);
		buttonRoadMap.setSize("115px", "55px");
		absolutePanel.add(buttonHybridMap, 188, 454);
		buttonHybridMap.setSize("115px", "55px");
		absolutePanel.add(buttonSatelliteMap, 364, 454);
		buttonSatelliteMap.setSize("115px", "55px");

		//adding the click handlers
		buttonSatelliteMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(bench, zoom, "satellite");
			}
		});

		buttonHybridMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(bench, zoom, "hybrid");
			}
		});

		buttonRoadMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(bench, zoom, "road");
			}
		});

		buttonHomePage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rootPanel.clear();
				PageController.sharedPageController().showMainPage();
			}
		});
	}

	public void show(Bench bench, final SearchParameter sp)
	{
		rootPanel.add(absolutePanel, 0, 0);

		benchLatitude = (float) Double.valueOf(bench.getLatitude()).doubleValue();
		benchLongitude = (float) Double.valueOf(bench.getLongitude()).doubleValue();

		zoom = checkSize(benchLatitude, benchLongitude);

		createMap(bench, zoom, mapType);
		
		labelBenchDonor.setText(bench.getDonatedFor());
		labelBenchGetYearDonated.setText(String.valueOf(bench.getYearDonated()));
		
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

	/**
	 * Returns "16" or "17" which represent zoom levels used in the google maps image based on the input lat/long coordinates
	 * @param treeLatitude
	 * @param treeLongitude
	 * @return
	 */
	private String checkSize(Float treeLatitude, Float treeLongitude) {
		if (treeLatitude > 44.32252 || treeLongitude > -93.974755)
		{
			return "16";
		}
		return"17";
	}

	private void createMap(final Bench Bench, String zoom, String mapType) {
		String url = new String("http://maps.googleapis.com/maps/api/staticmap?center=C44.323526,%-93.973789&zoom=" + zoom + "&size=512x512&maptype=" + mapType + "&markers=color:blue%7Clabel:S%7C44.323526,-93.973789&markers=color:green%7Clabel:G%7C" + Bench.getLatitude() + "," + Bench.getLongitude() + "&sensor=false");
		image.setUrl(url);
	}	
}



