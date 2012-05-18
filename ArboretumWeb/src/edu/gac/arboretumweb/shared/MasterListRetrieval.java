package edu.gac.arboretumweb.shared;
import edu.gac.arboretumweb.shared.domain.*;

import java.io.IOException;
import java.util.*;

import com.google.gdata.util.ServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MasterListRetrieval")
public interface MasterListRetrieval extends RemoteService {
	
	
	ArrayList<Tree> getTreeMasterList();
	ArrayList<Brick> getBrickMasterList();
	ArrayList<Bench> getBenchMasterList();
		
}
