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

public class TreePage implements EntryPoint {

	@Override
	public void onModuleLoad() 
	{

	}

	public void show(Tree tree2, final SearchParameter sp)
	{
		final Tree tree = new Tree();	

		final RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1600", "900");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);

		Label lblYourTree = new Label("Your Tree");
		rootPanel.add(lblYourTree, 550, 10);

		String mapType = "hydrid";

		Float treeLatitude = (float) Double.valueOf(tree.getLatitude()).doubleValue();
		Float treeLongitude = (float) Double.valueOf(tree.getLongitude()).doubleValue();

		final String zoom = checkSize(treeLatitude, treeLongitude);

		createMap(tree, rootPanel, zoom, mapType);

		Button btnRoadMap = new Button("Road Map");
		rootPanel.add(btnRoadMap, 10, 449);
		btnRoadMap.setSize("115px", "55px");

		btnRoadMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(tree, rootPanel, zoom, "road");
			}
		});

		Button btnHybridMap = new Button("Hybrid Map");
		rootPanel.add(btnHybridMap, 193, 449);
		btnHybridMap.setSize("115px", "55px");

		btnHybridMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(tree, rootPanel, zoom, "hybrid");
			}
		});

		Button btnSatelliteMap = new Button("Satellite Map");
		rootPanel.add(btnSatelliteMap, 374, 449);
		btnSatelliteMap.setSize("115px", "55px");

		btnSatelliteMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(tree, rootPanel, zoom, "satellite");
			}
		});

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//
		//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Label lblCommonName = new Label("Common Name");
		rootPanel.add(lblCommonName, 531, 34);
		lblCommonName.setSize("92px", "18px");

		Label lblTreecommonname = new Label(tree.getCommonName());
		rootPanel.add(lblTreecommonname, 531, 58);

		Label lblScientificName = new Label("Scientific Name");
		rootPanel.add(lblScientificName, 531, 131);

		Label lblTreescientificname = new Label(tree.getScientificName());
		rootPanel.add(lblTreescientificname, 531, 155);

		Label lblYearPlanted = new Label("Year Planted");
		rootPanel.add(lblYearPlanted, 531, 219);

		Label lblTreeyearplanted = new Label(tree.getYearPlanted());
		rootPanel.add(lblTreeyearplanted, 531, 243);

		Label lblYearDonated = new Label("Year Donated");
		rootPanel.add(lblYearDonated, 531, 301);

		Label lblTreeyeardonated = new Label(String.valueOf(tree.getYearDonated()));
		rootPanel.add(lblTreeyeardonated, 531, 325);

		Label lblDonatedFor = new Label("Donated For");
		rootPanel.add(lblDonatedFor, 531, 380);

		Label lblTreedonatedfor = new Label(tree.getDonatedFor());
		rootPanel.add(lblTreedonatedfor, 531, 404);

		Button btnHomePage = new Button("Home Page");
		rootPanel.add(btnHomePage, 879, 34);
		btnHomePage.setSize("115px", "55px");

		btnHomePage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//PageController.sharedPageController().showMainPage();
			}
		});
		
		Button btnBack = new Button("Back");
		rootPanel.add(btnBack, 879, 119);
		btnBack.setSize("112px", "55px");

		btnBack.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageController.sharedPageController().showSearchResultsPage(sp);
			}
		});

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String checkSize(Float treeLatitude, Float treeLongitude) {
		if (treeLatitude > 44.32252 || treeLongitude > -93.974755){
			return "16";
		}
		return"17";
	}

	private void createMap(final Tree tree, final RootPanel rootPanel,
			String zoom, String mapType) {
		String url = new String("http://maps.googleapis.com/maps/api/staticmap?center=C44.323526,%-93.973789&zoom=" + zoom + "&size=512x512&maptype=" + mapType + "&markers=color:blue%7Clabel:S%7C44.323526,-93.973789&markers=color:green%7Clabel:G%7C" + tree.getLatitude() + "," + tree.getLongitude() + "&sensor=false");
		Image image = new Image((String) url);
		rootPanel.add(image, 10, 34);
		image.setSize("479px", "409px");
	}
}


//Quad 1 : Lat <= 44.319568
//Quad 2 : 44.319568 <= Lat <= 44.320024 
//Quad 3 : 44.320024 <= Lat <= 44.320901
//Quad 4 : 44.320901 <= Lat <= 44.321641
//Quad 5 : 44.321641 <= Lat <= 44.326000

