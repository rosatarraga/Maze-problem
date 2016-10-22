package problems.maze;
import java.util.ArrayList;
import java.util.Collections;
import static problems.maze.MazeAction.*;
import search.Action;
import search.Node;
import search.SearchProblem;
import search.State;

/**
 * Extends SearchProblem and implements the functions which define the maze
 * problem.
 */
public class MazeProblem implements SearchProblem {

    // Penalty factor for each wood wall bitten.
    private static final double PENALTY = 2;
    // Maximum number of bites.
    private static final int MAX_BITES = 3;

    /* Maze */
    private Maze maze;

    /**
     * Constructors
     */
    public MazeProblem(int size, int seed) {
        this.maze = new Maze(size, seed);
    }

    public MazeProblem(Maze maze) {
        this.maze = maze;
    }

    /**
     * Returns an state representing the initial position of the hamster
     */
    @Override
    public State initialState() {
        return new MazeState(0, maze.inputX, 0);
    }

    /**
     * Returns an state representing the goal (position of the cheese)
     */
    @Override
    public State goalState() {
        return new MazeState(maze.size - 1, maze.outputX);
    }

    /**
     * Returns the result of applying an action. If action movement goes to a
     * wall, returns null, as the action should not be allowed.
     */
    @Override
    public State applyAction(State state, Action action) {
        // Casts the parameters
        MazeState mazeState = (MazeState) state;
        MazeAction mazeAction = (MazeAction) action;

        // New state position
        int newPosX = mazeState.x;
        int newPosY = mazeState.y;

        // Calculates the new position. 
        switch (mazeAction) {
            case LEFT:
                newPosX--;
                break;
            case RIGHT:
                newPosX++;
                break;
            case UP:
                newPosY--;
                break;
            case DOWN:
                newPosY++;
                break;
        }

        // Returns null if trying to move out of the maze
        if ((newPosX < 0) || (newPosX > maze.size - 1) || (newPosY < 0) || (newPosY > maze.size - 1)) {
            return null;
        }

        /* TO DO (1) */
        //we have to check if we can move through the left or right and see where you are
        //if is empty, we can move
        if (maze.cells[newPosY][newPosX] == maze.EMPTY) {
            return new MazeState(newPosY, newPosX, mazeState.numBites);
        }

        if (maze.cells[newPosY][newPosX] == maze.WALL) {
            return null;
        }

        if (maze.cells[newPosY][newPosX] == maze.WOOD && mazeState.numBites < MAX_BITES) {
            return new MazeState(newPosY, newPosX, mazeState.numBites + 1);

        }
        if (maze.cells[newPosY][newPosX] == maze.WOOD && mazeState.numBites >= MAX_BITES) {
            return null;

        }

        // Otherwise is a WALL or a WOOD that can't be bitten
        return null;
    }

    /**
     * Returns a list of the actions that can be applied over an state.
     */
    @Override
    public ArrayList<Action> getPossibleActions(State state) {
        // Casts the state
        MazeState mazeState = (MazeState) state;
        ArrayList<Action> possibleActions = new ArrayList<Action>();

        /* TO DO (2) */
        if (mazeState.y - 1 >= 0) {
            if (maze.cells[mazeState.y - 1][mazeState.x] == maze.EMPTY || (maze.cells[mazeState.y - 1][mazeState.x] == maze.WOOD && mazeState.numBites < MAX_BITES)) {
                possibleActions.add(UP);

            }
        }
        if (mazeState.x - 1 >= 0) {
            if (maze.cells[mazeState.y][mazeState.x - 1] == maze.EMPTY || (maze.cells[mazeState.y][mazeState.x - 1] == maze.WOOD && mazeState.numBites < MAX_BITES)) {
                possibleActions.add(LEFT);
            }
        }
        if (mazeState.x + 1 < maze.size) {
            if (maze.cells[mazeState.y][mazeState.x + 1] == maze.EMPTY || (maze.cells[mazeState.y][mazeState.x + 1] == maze.WOOD && mazeState.numBites < MAX_BITES)) {
                possibleActions.add(RIGHT);
            }
        }
        if (mazeState.y + 1 < maze.size) {
            if (maze.cells[mazeState.y + 1][mazeState.x] == maze.EMPTY || (maze.cells[mazeState.y + 1][mazeState.x] == maze.WOOD && mazeState.numBites < MAX_BITES)) {
                possibleActions.add(DOWN);
            }
        }
        // Returns them
        return possibleActions;
    }

    /**
     * Here, the cost depends on the state. If the new cell is wood, the cost
     * corresponds to two times the number of bites so far. Otherwise, it
     * returns 1.
     */
    @Override
    public double cost(State state, Action action) {
        // Casts the parameters
        MazeState mazeState = (MazeState) state;
        MazeAction mazeAction = (MazeAction) action;

        // New state position
        int newPosX = mazeState.x;
        int newPosY = mazeState.y;

        // Calculates the new position. 
        switch (mazeAction) {
            case LEFT:
                newPosX--;
                break;
            case RIGHT:
                newPosX++;
                break;
            case UP:
                newPosY--;
                break;
            case DOWN:
                newPosY++;
                break;
        }

        // Returns infinity when moving out the maze if trying to move out of the maze
        if ((newPosX < 0) || (newPosX > maze.size - 1) || (newPosY < 0) || (newPosY > maze.size - 1)) {
            return Double.POSITIVE_INFINITY;
        }

        // If the new position is empty, returns the cost of one step.
        if (maze.cells[newPosY][newPosX] == maze.EMPTY) {
            return 1.0;
        }

        // If the new position is wood, depends on the bites remaining
        if (maze.cells[newPosY][newPosX] == maze.WOOD) {
            if (mazeState.numBites < MAX_BITES) {
                return (mazeState.getNumBites() + 1) * PENALTY;
            }
        }

        // Otherwise is a WALL or a WOOD that can't be bitten
        return Double.POSITIVE_INFINITY;
    }

    /**
     * Tests if a state is the goal. Only tests the position.
     */
    public boolean testGoal(State state) {
        /* TO DO (3) */
        MazeState goalState = (MazeState) goalState();
        MazeState actualState = (MazeState) state;

        if ((goalState.x == actualState.x) && (goalState.y == actualState.y)) { //checking if their coordinates are equals
            return true;
        }
        return false; //in other case we havent reached the goal
        
    }

    /**
     * Returns the heuristic value of an state.
     */
    @Override
    public double heuristic(State state) {
        /* TO DO (4) */
        MazeState currentState = (MazeState) state;
        MazeState goalState = (MazeState) goalState();
        return Math.abs(currentState.x - goalState.x) + Math.abs(currentState.y - goalState.y);

    }
}
