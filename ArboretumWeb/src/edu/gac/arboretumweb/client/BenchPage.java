package edu.gac.arboretumweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;

import edu.gac.arboretumweb.shared.domain.Bench;
import com.google.gwt.user.client.ui.Button;

public class BenchPage implements EntryPoint {

	@Override
	public void onModuleLoad() {
		//public void showBenchPage(Bench Bench){	

		final Bench Bench = new Bench();
		final RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1600", "900");




		String mapType = "hydrid";

		Float BenchLatitude = (float) Double.valueOf(Bench.getLatitude()).doubleValue();
		Float BenchLongitude = (float) Double.valueOf(Bench.getLongitude()).doubleValue();


		final String zoom = checkSize(BenchLatitude, BenchLongitude);

		createMap(Bench, rootPanel, zoom, mapType);



		Button btnRoadMap = new Button("Road Map");
		rootPanel.add(btnRoadMap, 10, 449);
		btnRoadMap.setSize("115px", "55px");

		btnRoadMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(Bench, rootPanel, zoom, "road");
			}
		});


		Button btnHybridMap = new Button("Hybrid Map");
		rootPanel.add(btnHybridMap, 193, 449);
		btnHybridMap.setSize("115px", "55px");

		btnHybridMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(Bench, rootPanel, zoom, "hybrid");
			}
		});


		Button btnSatelliteMap = new Button("Satellite Map");
		rootPanel.add(btnSatelliteMap, 374, 449);
		btnSatelliteMap.setSize("115px", "55px");

		btnSatelliteMap.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				createMap(Bench, rootPanel, zoom, "satellite");
			}
		});


		Label lblYourBench = new Label("Your Bench");
		rootPanel.add(lblYourBench, 550, 10);
		lblYourBench.setSize("66px", "18px");

		Label lblDonatedFor = new Label("Donated For");
		rootPanel.add(lblDonatedFor, 550, 86);

		Label lblYearDonated = new Label("Year Donated");
		rootPanel.add(lblYearDonated, 550, 183);


		Label lblBenchgetdonor = new Label(Bench.getDonatedFor());
		rootPanel.add(lblBenchgetdonor, 550, 119);

		Label lblBenchgetyeardonated = new Label(String.valueOf(Bench.getYearDonated()));
		rootPanel.add(lblBenchgetyeardonated, 550, 207);


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
				//PageController.sharedPageController().showPreviousPage();
			}
		});	
	}

	private String checkSize(Float treeLatitude, Float treeLongitude) {
		if (treeLatitude > 44.32252 || treeLongitude > -93.974755){
			return "16";
		}
		return"17";
	}

	private void createMap(final Bench Bench, final RootPanel rootPanel,
			String zoom, String mapType) {
		String url = new String("http://maps.googleapis.com/maps/api/staticmap?center=C44.323526,%-93.973789&zoom=" + zoom + "&size=512x512&maptype=" + mapType + "&markers=color:blue%7Clabel:S%7C44.323526,-93.973789&markers=color:green%7Clabel:G%7C" + Bench.getLatitude() + "," + Bench.getLongitude() + "&sensor=false");
		Image image = new Image((String) url);
		rootPanel.add(image, 10, 34);
		image.setSize("479px", "409px");
	}
}

