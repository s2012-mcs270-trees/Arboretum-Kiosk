package edu.gac.arboretumweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;

import edu.gac.arboretumweb.shared.domain.Brick;
import com.google.gwt.user.client.ui.Button;

public class BrickPage implements EntryPoint {

	@Override
	public void onModuleLoad() {
		//public void showBrickPage(Brick brick){	

		Brick brick = new Brick();
		final RootPanel rootPanel = RootPanel.get();
		rootPanel.setSize("1600", "900");

		Label lblYourBrick = new Label("Your Brick");
		rootPanel.add(lblYourBrick, 465, 34);
		lblYourBrick.setSize("66px", "18px");

		Label lblDonatedFor = new Label("Donated For");
		rootPanel.add(lblDonatedFor, 465, 86);

		Label lblYearDonated = new Label("Year Donated");
		rootPanel.add(lblYearDonated, 467, 183);

		Label lblBrickSize = new Label("Brick Size");
		rootPanel.add(lblBrickSize, 465, 272);

		Label lblDistance = new Label("Distance 1-Close, 4-Far");
		rootPanel.add(lblDistance, 465, 356);
		lblDistance.setSize("109px", "18px");

		Label lblBrickgetdonor = new Label(brick.getDonatedFor());
		rootPanel.add(lblBrickgetdonor, 465, 119);

		Label lblBrickgetyeardonated = new Label(String.valueOf(brick.getYearDonated()));
		rootPanel.add(lblBrickgetyeardonated, 465, 207);

		Label lblBrickgetsize = new Label(brick.getSize());
		rootPanel.add(lblBrickgetsize, 465, 307);

		Label lblBrickgetdistance = new Label(brick.getDistance());
		rootPanel.add(lblBrickgetdistance, 465, 403);
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);

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

}
