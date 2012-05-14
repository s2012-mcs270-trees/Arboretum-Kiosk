import java.io.IOException;
import java.util.ArrayList;

import com.google.gdata.util.ServiceException;

import edu.gac.arboretumweb.server.*;
import edu.gac.arboretumweb.shared.domain.*;

public class TestRetrieval {

	/**
	 * @param args
	 * @throws ServiceException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, ServiceException {
		
		ArrayList<Tree> treeList = new ArrayList<Tree>();
		ArrayList<Brick> brickList = new ArrayList<Brick>();
		ArrayList<Bench> benchList = new ArrayList<Bench>();
		
		SpreadsheetRetrieval sheets = new SpreadsheetRetrieval();
		
		sheets.updateMasterLists();
		
		treeList = sheets.getTreeMasterList();
		brickList = sheets.getBrickMasterList();
		benchList = sheets.getBenchMasterList();
		
		for (Brick brick : brickList) {
			System.out.print(brick.getSize() + " " + brick.getDonatedFor() + " " + brick.getYearDonated());
			System.out.println();
		}
		
		for (Bench bench : benchList) {
			System.out.print(bench.getBenchType() + " " + bench.getDonatedFor() + " " + bench.getYearDonated() + " " + bench.getLongitude() + " " + bench.getLatitude() );
			System.out.println();
		}
		
		for (Tree tree : treeList) {
			System.out.print(tree.getCommonName() + " " + tree.getScientificName() + " " + tree.getYearPlanted() + " " + tree.getLongitude() + " " + tree.getLatitude());
			System.out.println();
		}
	}

}
