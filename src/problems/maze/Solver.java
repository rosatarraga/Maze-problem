package problems.maze;

import java.util.ArrayList;

import search.*;
import search.algorithms.AlgorithmLoader;


public class Solver {

	public static void main(String[] args){
		// Reads the arguments
		int mazeSize = Integer.parseInt(args[0]); 		// Size of the maze
		int seed = Integer.parseInt(args[1]);     		// Seed used to generate the maze
		String algorithmName = args[2];			  		// Name of the search algorithm
		int sizeWindow=600;							  	// Size of the window. If 0, nothing is shown.
		if (args.length>3)
			sizeWindow = Integer.parseInt(args[3]);
			
		// Creates the maze search problem
		Maze maze = new Maze(mazeSize,seed);
		SearchProblem problem = new MazeProblem(maze);
		
		// Instantiates the search algorithm
		SearchAlgorithm algorithm = AlgorithmLoader.getAlgorithm(algorithmName);
		algorithm.setProblem(problem);
		
		// Carries out the search.
		algorithm.search();
		
		// Reads the solution
		ArrayList<Action> solution = algorithm.result();
		
		// Shows the results
		System.out.println("Results for the algorithm: "+algorithmName);
		System.out.println("\tSize of the maze: "+mazeSize);
		System.out.println("\tCost:"+algorithm.getTotalCost());
		System.out.println("\tExpanded nodes: "+algorithm.getExpandedNodes());		
		System.out.println("\tTime (milliseconds): "+algorithm.getSearchTime());	

		// Visualizes the results if necessary
		if (sizeWindow>0){
			MazeView view = new MazeView(maze, sizeWindow);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
		
			for (Action action: solution){
				view.step(action);
			}
			
			// Closes the window after 5 seconds
			try {
					Thread.sleep(5000);
				} 	catch (InterruptedException e) {}
			view.close();			
		}
		
		
			
	}
}
