package search;

import java.util.ArrayList;

/** 
 * This class must be extended by all classes which implement a search 
 * algorithm. Descending classes must implement two methods, the one which
 * searches for the path, and the one returning the result.
 */
public abstract class SearchAlgorithm{
	
	/* Formulation of the problem. */
	protected SearchProblem problem;
	
    /* Sequence of actions which will store the solution. */
    protected ArrayList<Action> actionSequence;	
    
    /* Stores the cost */
    protected double totalCost;
    
	/* Stores the number of nodes expanded during the search.*/
	protected long expandedNodes;     

	/* Stores the time needed to finish the search */
	protected long searchTime;
	
    /**
     * Carries out the search and returns the the result.
     */    
    public abstract void search();   
		
	
	/** 
	 * Sets the search problem.
	 */
	public void setProblem(SearchProblem problem){
		this.problem = problem;
	}

	/** 
	 * Returns the cost of the solution.
	 */
	public double getTotalCost(){
		return totalCost;
	}
	
	/**
	 * Returns the number of expanded nodes.
	 */
    public long getExpandedNodes(){
    	return expandedNodes;
    }
    
    /** 
     * Returns the search time.
     */
    public long getSearchTime(){
    	return searchTime;
    }
    
    /** 
     * Returns the solution to the problem. 
     */
    public ArrayList<Action> result(){
    	return actionSequence;
    }       
     
    // Some methods useful for the implementation.
    
	/** 
	 * Checks if the node contains the initial state.
	 */
	public boolean isInitialNode(Node node){
		return problem.initialState().equals(node.getState());
	}
    
	/**
	 *  Return the successors of a given node. It is necessary if besides the states, some other 
	 *  information, such as actions, costs, etc., is needed.
	 *  This function corresponds to EXPAND seen in class. 
     * @param node
     * @return 
	 */
	public ArrayList<Node> getSuccessors(Node node){
		ArrayList<Node> successors = new ArrayList<Node>();
		// Obtains the possible actions
		ArrayList<Action> actions = problem.getPossibleActions(node.getState());
		// For each action. 
		for (Action action: actions){
			// Generates the state.
			State newState = problem.applyAction(node.getState(), action);
			// Creates the node and fixes the action used
			Node newNode = new Node(newState);
			// Set the parent.
			newNode.setParent(node);
			// Fixes the action used to create the new node.
			newNode.setAction(action);
			// Calculates the cost.
			double costAction = problem.cost(node.getState(), action);
			newNode.setCost(node.getCost()+ costAction);
			// Adds the heuristic
			newNode.setHeuristic(problem.heuristic(newState));
			// Updates its depth.
			newNode.setDepth(node.getDepth()+1);
			//Adds it to the list.
			successors.add(newNode);
		}
		expandedNodes++;
		return successors;
	}
}