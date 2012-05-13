package edu.gac.arboretumweb.client;

import java.util.*;

import edu.gac.arboretumweb.shared.MasterListRetrieval;
import edu.gac.arboretumweb.shared.MasterListRetrievalAsync;
import edu.gac.arboretumweb.shared.domain.Bench;
import edu.gac.arboretumweb.shared.domain.Brick;
import edu.gac.arboretumweb.shared.domain.Tree;
import edu.gac.arboretumweb.client.SearchParameter;
import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class Searcher {
	private ArrayList<Bench> updatedBenchList;
	private ArrayList<Brick> updatedBrickList;
	private ArrayList<Tree>  updatedTreeList;
	private final MasterListRetrievalAsync masterListRetrieval= GWT.create(MasterListRetrieval.class);
	
	//A Callback to update the local list of trees
	private AsyncCallback<ArrayList<Tree>> treeUpdater = new AsyncCallback<ArrayList<Tree>>(){
		@Override
		public void onFailure(Throwable caught) {	
			System.out.println("NErrrrrrrrrr! MAH BERKs!");
		}

		@Override
		public void onSuccess(ArrayList<Tree> result) {
			updatedTreeList = result;	
		}
		
	};
	
	// A callback to update the local list of benches
	private AsyncCallback<ArrayList<Bench>> benchUpdater = new AsyncCallback<ArrayList<Bench>>(){

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub	
			System.out.println("Poo delly");
		}

		@Override
		public void onSuccess(ArrayList<Bench> result) {
			updatedBenchList=result;	
		}
		
	};
	
	//A callback to update the local list of Bricks
	private AsyncCallback<ArrayList<Brick>> brickUpdater = new AsyncCallback<ArrayList<Brick>>(){

		@Override
		public void onFailure(Throwable caught) {
			//Then, well... shit
			System.out.println("Shit's fucked, yo");
		}

		@Override
		public void onSuccess(ArrayList<Brick> result) {
			// TODO Auto-generated method stub
			updatedBrickList=result;
		}
		
	};
	
	public void updateLocalMasterLists(){
		masterListRetrieval.getTreeMasterList(treeUpdater);
		masterListRetrieval.getBenchMasterList(benchUpdater);
		masterListRetrieval.getBrickMasterList(brickUpdater);
	}
	
	//returns a list of trees fitting a search parameter
	public ArrayList<Tree> searchResultsForTreeSearch(SearchParameter sp){
		ArrayList<Tree> accumulatorList = new ArrayList<Tree>();
		
		if(sp.getSearchTypeAsString() == null){
			return updatedTreeList;
		}
		else if(sp.getSearchTypeAsString() == "Common Name"){
			for(Tree loopTree:updatedTreeList){
				if((loopTree).getCommonName().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())   ){
					accumulatorList.add(loopTree);
					
				}	
				
			}
		}
		else if(sp.getSearchTypeAsString() == "Scientific Name")
		{
			for(Tree loopTree:updatedTreeList)
			{
				if((loopTree).getScientificName().toLowerCase().contains(sp.getKeywordQuery().toLowerCase()))
				{
						accumulatorList.add(  loopTree);	
				}
				
			}
		}
		else if(sp.getSearchTypeAsString() == "Donated For")
		{
			for(Tree loopTree:updatedTreeList)
			{
				if((loopTree).getDonatedFor().toLowerCase().contains(sp.getKeywordQuery().toLowerCase()))
				{
						accumulatorList.add(loopTree);
				}
			}
		}
		
		if(sp.isUndonatedBoxChecked()){
			ArrayList<Tree> newAccumList= new ArrayList<Tree>();
			for(Tree loopTree : accumulatorList){
				if(loopTree.getDonatedFor()==null){
					newAccumList.add(loopTree);
				}
				
			}
			accumulatorList=newAccumList;
		}
		
		return accumulatorList;
	}
	
	//returns a list of benches fitting search parameter	
	public ArrayList<Bench> searchResultsForBenchSearch(SearchParameter sp){
		
		ArrayList<Bench> accumulatorList = new ArrayList<Bench>();
	 
		//Which parameter are we searching by? If common name, loop through looking for common name
		if(sp.getSearchTypeAsString() == null){
			return updatedBenchList;
		}	
		
		else if(sp.getSearchTypeAsString() == "Donated For"){
			for(Bench loopBench:updatedBenchList){
				if((loopBench).getDonatedFor().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())){
						accumulatorList.add(loopBench);
					
				}
				
			}
		}	
		
		if(sp.isUndonatedBoxChecked()){
			ArrayList<Bench> newAccumList= new ArrayList<Bench>();
			for(Bench loopBench : accumulatorList){
				if(loopBench.getDonatedFor()==null){
					newAccumList.add(loopBench);
				}
				
			}
			accumulatorList=newAccumList;
		}
	 
		return accumulatorList;
	}
	
	// returns a list of bricks fitting a search parameter
	public ArrayList<Brick> searchResultsForBrickSearch(SearchParameter sp){
		
		ArrayList<Brick> accumulatorList = new ArrayList<Brick>();
		 
		if(sp.getSearchTypeAsString() == null){
			return updatedBrickList;
		}	
		
		else if(sp.getSearchTypeAsString() == "Donated For"){
			for(Brick loopBrick:updatedBrickList){
				if((loopBrick).getDonatedFor().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())){
						accumulatorList.add(loopBrick);
				}
			}
		}
		
		if(sp.isUndonatedBoxChecked()){
			ArrayList<Brick> newAccumList= new ArrayList<Brick>();
			for(Brick loopBrick : accumulatorList){
				if(loopBrick.getDonatedFor()==null){
					newAccumList.add(loopBrick);
				}
				
			}
			accumulatorList=newAccumList;
		}
		
		return accumulatorList;
	}	
}