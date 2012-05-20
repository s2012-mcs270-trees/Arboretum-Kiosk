package edu.gac.arboretumweb.client;

import java.util.*;

import edu.gac.arboretumweb.shared.MasterListRetrieval;
import edu.gac.arboretumweb.shared.MasterListRetrievalAsync;
import edu.gac.arboretumweb.shared.domain.Bench;
import edu.gac.arboretumweb.shared.domain.Brick;
import edu.gac.arboretumweb.shared.domain.Tree;
import edu.gac.arboretumweb.client.SearchParameter;
import edu.gac.arboretumweb.client.SearchParameter.Quadrant;

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
			System.out.println("Local tree list update failed.");
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
			System.out.println("Local bench list update failed.");
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
			System.out.println("Local brick list update failed.");
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
		
		else if (updatedTreeList == null){
			return accumulatorList;
		}
		
		else if(sp.getSearchTypeAsString() == "Common Name"){
			for(Tree loopTree:updatedTreeList){
				if((loopTree).getCommonName().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())){
					accumulatorList.add(loopTree);	
				}	
			}
		}
		else if(sp.getSearchTypeAsString() == "Scientific Name"){
			for(Tree loopTree:updatedTreeList){
				if((loopTree).getScientificName().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())){
						accumulatorList.add(  loopTree);	
				}
			}
		}
		else if(sp.getSearchTypeAsString() == "Donated For"){
			for(Tree loopTree:updatedTreeList){
				if((loopTree).getDonatedFor().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())){
						accumulatorList.add(loopTree);
				}
			}
		}
		
		//Parses the generated AccumulatorList based on the quadrants selected.
		//--------------------------------------------------------------------
		ArrayList<Tree> newAccumList= new ArrayList<Tree>();
		for(Tree loopTree : accumulatorList){
			if(sp.getQuadrants().contains(Quadrant.A)){
				System.out.println("Check");
				if(loopTree.getQuadrant() == Quadrant.A){
					System.out.println("Check1");
					newAccumList.add(loopTree);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.B)){
				System.out.println("Check");
				if(loopTree.getQuadrant() == Quadrant.B){
					System.out.println("Check1");
					newAccumList.add(loopTree);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.C)){
				System.out.println("Check");
				if(loopTree.getQuadrant() == Quadrant.C){
					System.out.println("Check1");
					newAccumList.add(loopTree);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.D)){
				System.out.println("Check");
				if(loopTree.getQuadrant() == Quadrant.D){
					System.out.println("Check1");
					newAccumList.add(loopTree);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.E)){
				System.out.println("Check");
				if(loopTree.getQuadrant() == Quadrant.E){
					System.out.println("Check1");
					newAccumList.add(loopTree);
				}
			}	
		}
		accumulatorList = newAccumList;
		
		
		//Parses the generated list for all undonated objects.
		//----------------------------------------------------
		if(sp.isUndonatedBoxChecked()){
			ArrayList<Tree> newAccumList1= new ArrayList<Tree>();
			for(Tree loopTree : accumulatorList){
				if(loopTree.getDonatedFor()==null){
					newAccumList1.add(loopTree);
				}
				
			}
			accumulatorList=newAccumList1;
		}
		//----------------------------------------------------

		return accumulatorList;
	}
	
	
	//returns a list of benches fitting search parameter	
	public ArrayList<Bench> searchResultsForBenchSearch(SearchParameter sp){
		
		ArrayList<Bench> accumulatorList = new ArrayList<Bench>();
	 
		//Which parameter are we searching by? If common name, loop through looking for common name
		if(sp.getSearchTypeAsString() == null){
			return updatedBenchList;
		}	
		
		else if (updatedBenchList == null){
			return accumulatorList;
		}
		
		else if(sp.getSearchTypeAsString() == "Donated For"){
			for(Bench loopBench:updatedBenchList){
				if(loopBench.getDonatedFor().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())){
						accumulatorList.add(loopBench);
				}
			}
		}	
		

		//Parses the generated AccumulatorList based on the quadrants selected.
		//--------------------------------------------------------------------
		ArrayList<Bench> newAccumList= new ArrayList<Bench>();
		for(Bench loopBench : accumulatorList){
			if(sp.getQuadrants().contains(Quadrant.A)){
				if(loopBench.getQuadrant() == Quadrant.A){
					newAccumList.add(loopBench);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.B)){
				if(loopBench.getQuadrant() == Quadrant.B){
					newAccumList.add(loopBench);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.C)){
				if(loopBench.getQuadrant() == Quadrant.C){
					newAccumList.add(loopBench);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.D)){
				if(loopBench.getQuadrant() == Quadrant.D){
					newAccumList.add(loopBench);
				}
			}
			if(sp.getQuadrants().contains(Quadrant.E)){
				if(loopBench.getQuadrant() == Quadrant.E){
					newAccumList.add(loopBench);
				}
			}	
		}
		accumulatorList = newAccumList;
		//--------------------------------------------------------------------
		
		
		//Parses the generated list for all undonated objects.
		//----------------------------------------------------
		if(sp.isUndonatedBoxChecked()){
			ArrayList<Bench> newAccumList1= new ArrayList<Bench>();
			for(Bench loopBench : accumulatorList){
				if(loopBench.getDonatedFor()==null){
					newAccumList1.add(loopBench);
				}
				
			}
			accumulatorList=newAccumList1;
		}
		//----------------------------------------------------
	 
		return accumulatorList;
	}
	
	// returns a list of bricks fitting a search parameter
	public ArrayList<Brick> searchResultsForBrickSearch(SearchParameter sp){
		
		ArrayList<Brick> accumulatorList = new ArrayList<Brick>();
		 
		if(sp.getSearchTypeAsString() == null){
			return updatedBrickList;
		}	
		
		else if (updatedBrickList == null){
			return accumulatorList;
		}
		
		else if(sp.getSearchTypeAsString() == "Donated For"){
			for(Brick loopBrick:updatedBrickList){
				if((loopBrick).getDonatedFor().toLowerCase().contains(sp.getKeywordQuery().toLowerCase())){
						accumulatorList.add(loopBrick);
				}
			}
		}		

		//Parses the generated list for all undonated objects.
		//----------------------------------------------------
		if(sp.isUndonatedBoxChecked()){
			ArrayList<Brick> newAccumList= new ArrayList<Brick>();
			for(Brick loopBrick : accumulatorList){
				if(loopBrick.getDonatedFor()==null){
					newAccumList.add(loopBrick);
				}
				
			}
			accumulatorList=newAccumList;
		}
		//----------------------------------------------------
		
		return accumulatorList;
	}	
}