package edu.gac.arboretumweb.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.gac.arboretumweb.shared.MasterListRetrieval;
import edu.gac.arboretumweb.shared.domain.*;

public class SpreadsheetRetrieval extends RemoteServiceServlet implements
MasterListRetrieval {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5367523128487203951L;
	public ArrayList<Tree> treeMasterList;
	private ArrayList<Brick> brickMasterList;
	public ArrayList<Bench> benchMasterList;
	private URL treeURL;

	//class used to update all master lists from the google spreadsheet
	public void updateMasterLists() throws IOException, ServiceException {
		SpreadsheetService treeSpreadsheetService = new SpreadsheetService("TreeSpreadsheet");


		//update tree list
		WorksheetFeed feed = treeSpreadsheetService.getFeed(treeURL,
	       WorksheetFeed.class);
		List<WorksheetEntry> worksheets = feed.getEntries();

		if (worksheets.size() == 0) {
			//TODO: worksheet not found
		}

		WorksheetEntry treeWorksheet = new WorksheetEntry();
		for (WorksheetEntry worksheet : worksheets) {
			if ("Trees".equals(worksheet.getTitle().getPlainText())) {
				treeWorksheet = worksheet;
			}
		}

	   // Fetch the list feed of the worksheet.
		URL listFeedUrl = treeWorksheet.getListFeedUrl();
		ListFeed listFeed = treeSpreadsheetService.getFeed(listFeedUrl, ListFeed.class);

		this.setTreeMasterList(new ArrayList<Tree>());


		//populate the master list with tree objects
		for (ListEntry entry : listFeed.getEntries()) {
			Tree tree = new Tree();

			for (String tag : entry.getCustomElements().getTags()) {

				if ("commonname".equals(tag)) {
					String commonName = entry.getCustomElements().getValue(tag);
					tree.setCommonName(commonName);
				} else if ("scientificname".equals(tag)) {
					String scientificName = entry.getCustomElements().getValue(tag);
					tree.setScientificName(scientificName);
				} else if ("yearplanted".equals(tag)) {
					String stringYearPlanted = entry.getCustomElements().getValue(tag);
					try {
						Integer yearPlanted = new Integer(stringYearPlanted);
						tree.setYearPlanted(""+yearPlanted.intValue());
					} catch (NumberFormatException e) {

					}
				} else if ("health".equals(tag)) {
					String health = entry.getCustomElements().getValue(tag);
					tree.setHealth(health);
				} else if ("longitude".equals(tag)) {
					String longitude = entry.getCustomElements().getValue(tag);
					tree.setLongitude(longitude);
				} else if ("latitude".equals(tag)) {
					String latitude = entry.getCustomElements().getValue(tag);
					tree.setLatitude(latitude);
				} else if ("diameter".equals(tag)) {
					String diameter = entry.getCustomElements().getValue(tag);
					tree.setDiameter(diameter);
				} else if ("yeardonated".equals(tag)) {
					String stringYearDonated = entry.getCustomElements().getValue(tag);
					try {
						Integer yearDonated = new Integer(stringYearDonated);
						tree.setYearDonated(yearDonated.intValue());
					} catch (NumberFormatException e) {

					}

				} else if ("donatedfor".equals(tag)) {
					String donatedFor = entry.getCustomElements().getValue(tag);
					tree.setDonatedFor(donatedFor);
				}
			}
			this.treeMasterList.add(tree);
		}

		//update bench list

		this.setBenchMasterList(new ArrayList<Bench>());

		if (worksheets.size() == 0) {
			//TODO: worksheet not found
		}

		WorksheetEntry benchWorksheet = new WorksheetEntry();
		for (WorksheetEntry worksheet : worksheets) {
			if ("Benches".equals(worksheet.getTitle().getPlainText())) {
				benchWorksheet = worksheet;
			}
		}

		// Fetch the list feed of the worksheet
		listFeedUrl = benchWorksheet.getListFeedUrl();
		listFeed = treeSpreadsheetService.getFeed(listFeedUrl, ListFeed.class);

		//populate the master list with bench objects
		for (ListEntry entry : listFeed.getEntries()) {
			Bench bench = new Bench();

			for (String tag : entry.getCustomElements().getTags()) {
				if ("benchtype".equals(tag)) {
					String benchType = entry.getCustomElements().getValue(tag);
					bench.setType(benchType);
				} else if ("yeardonated".equals(tag)) {
					String stringYearDonated = entry.getCustomElements().getValue(tag);
					try {
					Integer yearDonated = new Integer(stringYearDonated);
					bench.setYearDonated(yearDonated.intValue());
					} catch (NumberFormatException e) {

					}
				} else if ("longitude".equals(tag)) {
					String longitude = entry.getCustomElements().getValue(tag);
					bench.setLongitude(longitude);
				} else if ("latitude".equals(tag)) {
					String latitude = entry.getCustomElements().getValue(tag);
					bench.setLatitude(latitude);
				} else if ("donatedfor".equals(tag)) {
					String donatedFor = entry.getCustomElements().getValue(tag);
					bench.setDonatedFor(donatedFor);
				}
			}
			this.benchMasterList.add(bench);
		}

		//update brick list
		this.setBrickMasterList(new ArrayList<Brick>());

		WorksheetEntry brickWorksheet = new WorksheetEntry();
		for (WorksheetEntry worksheet : worksheets) {
			if ("Bricks".equals(worksheet.getTitle().getPlainText())) {
				brickWorksheet = worksheet;
			}
		}

	   // Fetch the list feed of the worksheet.
		listFeedUrl = brickWorksheet.getListFeedUrl();
		listFeed = treeSpreadsheetService.getFeed(listFeedUrl, ListFeed.class);

		//populate the master list with brick objects
		for (ListEntry entry : listFeed.getEntries()) {
			Brick brick = new Brick();

			for (String tag : entry.getCustomElements().getTags()) {
				if ("size".equals(tag)) {
					String brickSize = entry.getCustomElements().getValue(tag);
					brick.setSize(brickSize);
				} else if ("yeardonated".equals(tag)) {
					String stringYearDonated = entry.getCustomElements().getValue(tag);
					try {
						Integer yearDonated = new Integer(stringYearDonated);
						brick.setYearDonated(yearDonated.intValue());
					} catch (NumberFormatException e) {

					}
				} else if ("donatedfor".equals(tag)) {
					String donatedFor = entry.getCustomElements().getValue(tag);
					brick.setDonatedFor(donatedFor);
				} else if ("distance".equals(tag)) {
					String distance = entry.getCustomElements().getValue(tag);
					brick.setDistance(distance);
				}
			}
			this.brickMasterList.add(brick);
		}

	}
	public SpreadsheetRetrieval() throws MalformedURLException {
		 this.treeURL = new URL("https://spreadsheets.google.com/feeds/worksheets/0AjkI1jvOdpNzdEYwZEc5Zk5nbHIwdlJmSUtMaVJRT3c/public/values");
	}

	public ArrayList<Tree> getTreeMasterList() {
		return treeMasterList;
	}

	public void setTreeMasterList(ArrayList<Tree> treeMasterList) {
		this.treeMasterList = treeMasterList;
	}

	public ArrayList<Brick> getBrickMasterList() {
		return brickMasterList;
	}

	public void setBrickMasterList(ArrayList<Brick> brickMasterList) {
		this.brickMasterList = brickMasterList;
	}

	public ArrayList<Bench> getBenchMasterList() {
		return benchMasterList;
	}

	public void setBenchMasterList(ArrayList<Bench> benchMasterList) {
		this.benchMasterList = benchMasterList;
	}

	public URL getTreeURL() {
		return treeURL;
	}

	public void setTreeURL(URL treeURL) {
		this.treeURL = treeURL;
	}


}