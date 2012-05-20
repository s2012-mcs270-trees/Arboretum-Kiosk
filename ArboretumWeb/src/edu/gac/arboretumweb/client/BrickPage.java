package edu.gac.arboretumweb.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;

import edu.gac.arboretumweb.shared.domain.Brick;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class BrickPage implements EntryPoint {

	Brick brick = new Brick();
	final RootPanel rootPanel = RootPanel.get();
	private final AbsolutePanel absolutePanel = new AbsolutePanel();
	private final Label labelYourBrick = new Label("Your Brick");
	private final Label labelDonatedFor = new Label("Donated For");
	private final Label labelYearDonated = new Label("Year Donated");
	private final Label labelBrickSize = new Label("Brick Size");
	private final Label labelDistance = new Label("Distance 1-Close, 4-Far");
	private final Label labelGetDonatedFor = new Label("");
	private final Label labelGetYearDonated = new Label("");
	private final Label labelGetSize = new Label("");
	private final Label labelGetDistance = new Label("");
	private final Button buttonHomePage = new Button("Home Page");
	private final Button buttonBack = new Button("Back");
	
	@Override
	public void onModuleLoad() 
	{
		show(brick, new SearchParameter());
	}

	public BrickPage()
	{
		rootPanel.setSize("1600", "900");
		rootPanel.getElement().getStyle().setPosition(Position.RELATIVE);
		
		absolutePanel.setSize("1344px", "605px");
		absolutePanel.add(labelYourBrick, 427, 10);
		labelYourBrick.setSize("66px", "18px");
		absolutePanel.add(labelDonatedFor, 372, 58);
		absolutePanel.add(labelYearDonated, 372, 104);
		absolutePanel.add(labelBrickSize, 372, 145);
		absolutePanel.add(labelDistance, 372, 187);
		labelDistance.setSize("109px", "18px");
		absolutePanel.add(labelGetDonatedFor, 497, 58);
		absolutePanel.add(labelGetYearDonated, 497, 104);
		absolutePanel.add(labelGetSize, 497, 145);
		absolutePanel.add(labelGetDistance, 497, 187);
		
		absolutePanel.add(buttonHomePage, 712, 120);
		buttonHomePage.setSize("115px", "55px");
		
		buttonHomePage.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageController.sharedPageController().showMainPage();
			}
		});
		
		absolutePanel.add(buttonBack, 712, 33);
		buttonBack.setSize("112px", "55px");
	}
	
	public void show(Brick brick2, final SearchParameter sp)
	{
		
		rootPanel.add(absolutePanel, 0, 0);
		
		labelGetSize.setText(brick.getSize());
		labelGetYearDonated.setText(brick.getYearDonated());
		labelGetDonatedFor.setText(brick.getDonatedFor());
		labelGetDistance.setText(brick.getDistance());
		
		buttonBack.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageController.sharedPageController().showSearchResultsPage(sp);
			}
		});
		

	
		
		
	}
}
