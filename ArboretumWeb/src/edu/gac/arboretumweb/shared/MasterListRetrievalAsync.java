package edu.gac.arboretumweb.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.gac.arboretumweb.shared.domain.Bench;
import edu.gac.arboretumweb.shared.domain.Brick;
import edu.gac.arboretumweb.shared.domain.Tree;

public interface MasterListRetrievalAsync {
	
	void getBenchMasterList(AsyncCallback<ArrayList<Bench>> callback);

	void getBrickMasterList(AsyncCallback<ArrayList<Brick>> callback);

	void getTreeMasterList(AsyncCallback<ArrayList<Tree>> callback);
	

}
