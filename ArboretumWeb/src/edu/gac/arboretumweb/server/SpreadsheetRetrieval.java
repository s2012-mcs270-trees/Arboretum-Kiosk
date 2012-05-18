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
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.gac.arboretumweb.shared.MasterListRetrieval;
import edu.gac.arboretumweb.shared.domain.*;

public class SpreadsheetRetrieval extends RemoteServiceServlet implements
MasterListRetrieval {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5367523128487203951L;
	private ArrayList<Tree> treeMasterList;
	private ArrayList<Brick> brickMasterList;
	private ArrayList<Bench> benchMasterList;
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
					String yearPlanted = entry.getCustomElements().getValue(tag);
					tree.setYearPlanted(yearPlanted);
				} else if ("health".equals(tag)) {
					String health = entry.getCustomElements().getValue(tag);
					tree.setHealth(health);
				} else if ("longitude".equals(tag)) {
					String stringLongitude = entry.getCustomElements().getValue(tag);
					if (stringLongitude == null) {
						tree.setLongitude(0);
					} else {
						try {
							Double longitude = new Double(stringLongitude);
							tree.setLongitude(longitude.doubleValue());
						} catch (NumberFormatException e) {
							//Do nothing, will get set to zero by default
						}
					}
				} else if ("latitude".equals(tag)) {
					String stringLatitude = entry.getCustomElements().getValue(tag);
					if (stringLatitude == null) {
						tree.setLatitude(0);
					}else {
						try {
							Double latitude = new Double(stringLatitude);
							tree.setLatitude(latitude.doubleValue());
						} catch (NumberFormatException e) {
							//Do nothing, will get set to zero by default
						}
					}} else if ("diameter".equals(tag)) {
						String diameter = entry.getCustomElements().getValue(tag);
						tree.setDiameter(diameter);
					} else if ("yeardonated".equals(tag)) {
						String yearDonated = entry.getCustomElements().getValue(tag);
						tree.setYearDonated(yearDonated);
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
					String yearDonated = entry.getCustomElements().getValue(tag);
					bench.setYearDonated(yearDonated);
				} else if ("longitude".equals(tag)) {
					String stringLongitude = entry.getCustomElements().getValue(tag);
					if (stringLongitude == null) {
						bench.setLongitude(0);
					} else{
						try {
							Double longitude = new Double(stringLongitude);
							bench.setLongitude(longitude.doubleValue());
						} catch (NumberFormatException e) {

						}
					}
				} else if ("latitude".equals(tag)) {
					String stringLatitude = entry.getCustomElements().getValue(tag);
					if (stringLatitude == null) {
						bench.setLatitude(0);
					} else{
						try {
							Double latitude = new Double(stringLatitude);
							bench.setLatitude(latitude.doubleValue());
						} catch (NumberFormatException e) {

						}
					}
				}else if ("donatedfor".equals(tag)) {
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
					String yearDonated = entry.getCustomElements().getValue(tag);						
					brick.setYearDonated(yearDonated);
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
	public SpreadsheetRetrieval() throws MalformedURLException{
		this.treeURL = new URL("https://spreadsheets.google.com/feeds/worksheets/0AjkI1jvOdpNzdEYwZEc5Zk5nbHIwdlJmSUtMaVJRT3c/public/values");
//		Tree sampTree = new Tree();
//		sampTree.setCommonName("oak");
//		treeMasterList.add(sampTree);
		try {
			this.updateMasterLists();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Timer(){
			@Override
			public void run() {
				try {
					updateMasterLists();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.scheduleRepeating(86400000);
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