package search.algorithms;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

import search.*;

/** 
 * Extends the abstract class SearchAlgorithm and implements a template algorithm.
 */
public class TemplateAlgorithm extends SearchAlgorithm{
	

    // Search

    /** 
     * Implements a template search algorithm.
     */
	@Override
	public void search() {
		// Inits performance variables
		totalCost = 0;
		expandedNodes = 0;
		searchTime = System.currentTimeMillis();
		
		// Creates a new sequence of actions
		actionSequence= new ArrayList<Action>();
		
		// Creates the necessary structures
		//open = new ...
		//closed = new ...;
				
		// Auxiliary variables.
		Node chosen;
		ArrayList <Node> successors;
		        
		// Creates the root of the tree
		chosen = new Node(problem.initialState());       
		
		/**
		 * YOUR CODE HERE
		 */
		
        // Main cycle
        do {
        	// If there are no open nodes left, returns.
        	//if(open.size()==0) 
        	//	return;
        	// Otherwise, takes open node 
        	// chosen =  open....	
        	// Expands the node.
        	//successors = getSuccessors(chosen);
        	// ...
        }while(!problem.testGoal(chosen.getState()));
		
        // Calculates the search time.
		searchTime = System.currentTimeMillis()-searchTime;

        // As the list of actions has been obtained upwards, reverses it.
    	Collections.reverse(actionSequence);        
    }

}
