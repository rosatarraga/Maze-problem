package problems.maze;

import search.State;

/**
 *  Represents an state, which corresponds with a cell of the maze.
 */
public class MazeState extends State{
	
	/** An state is a position given by the coordinates (y,x) and the number of bites. */
	protected int y; 					// Coordinate y of the cell.
	protected int x; 					// Coordinate x of the cell.
	protected int numBites = 0;			// Number of wood walls bitten by the hamster.
	
	/** Constructor. Receives the pair of coordinates represented by the state. */
	public MazeState(int y, int x){
		this.y = y;
		this.x = x;
		this.numBites = 0;
	}
	
	/** Constructor. Receives the pair of coordinates represented by the state. */
	public MazeState(int y, int x, int numBites){
		this.y = y;
		this.x = x;
		this.numBites = numBites;		
	}
	
	/** Returns the coordinate y. */
	public int getY(){ 
		return y; 
	}
	
	/** Returns the coordinate x. */
	public int getX(){ 
		return x; 
	}
	
	/** Returns the current number of bytes */
	public int getNumBites(){
		return numBites;
	}
	
	/** 
	 * Checks if two states are similar. The method overrides the one provided by the Object class
	 * and is used by some classes in Java. For instance, the method HashSet.contains makes
	 * use of equals.
	 */
	@Override
	public boolean equals(Object anotherState){
		// If the object passed as parameter is not a state, returns false and
		// reports an error
		if (!(anotherState instanceof MazeState)){
			System.out.println("Trying to compare two objects of different classes.");
			return false;
		}
		// If the two objects have the same class, compares x and y.
		if(x!=((MazeState)anotherState).getX() || y!=((MazeState) anotherState).getY() || numBites!=((MazeState) anotherState).getNumBites()) 
			return false;
		else
			return true;
	}
	
	/** 
	 * Basic hashing function. Overrides the one in Object and is used in classes such
	 * as HashSet.
	 */
	@Override
	public int hashCode(){
		return 10000*y + 100*x + numBites;
	}
	
	/** 
	 * Prints the state.
	 */
	public String toString(){
		return "["+y+","+x+"("+numBites+")"+"]";
	}
	
}
