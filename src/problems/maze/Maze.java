package problems.maze;

import java.util.ArrayList;
import java.util.Random;

/** This class allows representing and generating a maze. */
public class Maze{

	/* Types of cells */
	public static final int EMPTY = 0;
	public static final int WOOD = 1;
	public static final int WALL = 2;
		
	/* Size, must be an odd number */
	protected int size = 11;
	
	/* Cells of the maze */
	protected int[][] cells;
	
	/* Input and output positions */
	protected int inputX;
	protected int outputX;
	
	/** Creates a maze with seed equals 0 */
	public Maze(int size){
		this.size = size;
		this.cells = new int[size][size];
		generate(0);
	}
	
	/** Creates a maze with a given seed */
	public Maze(int size, int seed){
		this.size = size;
		this.cells = new int[size][size];
		generate(seed);
	}
	
	/** Transforms the maze into a string. */
	public String toString(){
		char[] cellType = {' ', '+', '*'};
		String mazeStr = new String();
		int posX, posY;
		for(posY=0;posY<size;posY++){
			for(posX=0;posX<size;posX++)
				mazeStr+=cellType[cells[posY][posX]];
			mazeStr+='\n';
		}
		return mazeStr;	
	}
	
	/** (PROVISIONAL) Generates the maze with Prim's method. */
	private void generate(int seed){
		Random random = new Random();
		random.setSeed(seed);
		
		// Everything is a wall at the beginning
		for(int posY=0;posY<size;posY++)
			for(int posX=0;posX<size;posX++)
				cells[posY][posX]=WALL;
				
		// Generation of the maze (uses a one dimensional array)
		int numCells = size * size;
		ArrayList<Integer> list = new ArrayList<Integer>();
		// Selects a random cell
		int selCell = random.nextInt(numCells);
		int cellX = selCell%size;
		int cellY = selCell/size;
		// Removes the wall
		cells[cellY][cellX] = EMPTY;
		// Adds its neighbors (their indexes) to the auxiliar list.
		if (cellX>0)
			list.add(selCell-1);
		if (cellX<size-1)
			list.add(selCell+1);
		if (cellY>0)
			list.add(selCell-size);
		if (cellY<size-1)
			list.add(selCell+size);
		
		// While there are non processed nodes
		while (list.size()>0){
			// Selects one of them randomly.
			int selCellIndex = random.nextInt(list.size());
			selCell = list.get(selCellIndex);
			list.remove(selCellIndex);
			
			// Counts the number of neighbors of the selected node
			// which have already been explored.
			int numExplored = 0;
			cellX = selCell%size;
			cellY = selCell/size;
			if (cellX>0)
				if (cells[cellY][cellX-1]==EMPTY)
					numExplored++;
			if (cellX<size-1)
				if (cells[cellY][cellX+1]==EMPTY)
					numExplored++;
			if (cellY>0)
				if (cells[cellY-1][cellX]==EMPTY)
					numExplored++;
			if (cellY<size-1)
				if (cells[cellY+1][cellX]==EMPTY)
					numExplored++;			
		
			// If the number of neighbors explored is smaller than two, adds them and
			// removes the wall.
			if (numExplored<2){
				cells[cellY][cellX] = EMPTY;
				if (cellX>0)
					list.add(selCell-1);
				if (cellX<size-1)
					list.add(selCell+1);
				if (cellY>0)
					list.add(selCell-size);
				if (cellY<size-1)
					list.add(selCell+size);
			}
		}
		
		// At this point there is only one path. No walls can be added, otherwise
		// the path could be closed.
		
		
		// As the number of bites is limited, WOOD can only added
		// in closed cells (substituting the walls) to guarantee solutions.
		// Moreover, it opens some paths.
		for(int posY=0;posY<size;posY++)
			for(int posX=0;posX<size;posX++)
				if (cells[posY][posX]==WALL)
					if (random.nextDouble()<0.25)
						cells[posY][posX]=WOOD;
					else if (random.nextDouble()<0.1)
						cells[posY][posX] = EMPTY;
		
		// Sets the input and output 
		inputX = size/2;
		cells[0][inputX]=EMPTY;
		outputX = size-1;
		cells[size-1][outputX]=EMPTY;
	}
	
	
	/** Main function, used for testing. */
	public static void main(String[] args){
		Maze maze = new Maze(8,4);
		System.out.println(maze);
	}
}
