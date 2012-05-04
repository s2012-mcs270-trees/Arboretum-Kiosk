

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import edu.gac.arboretumweb.shared.domain.Tree;
import edu.gac.arboretumweb.server.*;

public class TestGetTrees {

	/**
	 * @param args
	 * @throws ServiceException 
	 * @throws IOException 
	 * @throws AuthenticationException 
	 */
	public static void main(String[] args) throws AuthenticationException, IOException, ServiceException {
		ArrayList<Tree> treeList = new ArrayList<Tree>();
		
		URL treeURL = new URL("https://spreadsheets.google.com/feeds/worksheets/0AjkI1jvOdpNzdEYwZEc5Zk5nbHIwdlJmSUtMaVJRT3c/public/values");
		
		//TreeFinderV2 treeFinder = new TreeFinderV2(treeURL);
		//treeFinder.getTrees(treeList);
		
		for (Tree tree : treeList) {
			System.out.print(tree.getCommonName() + " " + tree.getScientificName() + " " + tree.getYearPlanted());
			System.out.println();
		}

	}

}
