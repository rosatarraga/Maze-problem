package problems.maze;

import javax.swing.JFrame;

import search.Action;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// View of the Maze
public class MazeView{

	private JFrame mainWindow;					// Main window
	private MazeViewPanel mazeViewPanel;  	    // View panel
	
	/** Constructor */
	public MazeView(Maze maze, int sizePx){
		// Creates the window
		mainWindow = new JFrame("Laberinto");
		mainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Creates the view
		mazeViewPanel = new MazeViewPanel(maze, sizePx);	
		
		// Adds it to the frame and shows it.
		mainWindow.getContentPane().add(mazeViewPanel);
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
	
	/** Applies an action to the view */
	public void step(Action action){
		MazeAction mazeAction = (MazeAction) action;
		switch (mazeAction){
			case RIGHT:
				mazeViewPanel.moveRight();
				break;
			case LEFT:
				mazeViewPanel.moveLeft();
				break;
			case UP:
				mazeViewPanel.moveUp();
				break;
			case DOWN:
				mazeViewPanel.moveDown();
				break;	
		}
	}
	
	/** Closes the window */
    public void close(){
    	mainWindow.dispose();
    }
	
	/** Main function, used for testing. */
	public static void main(String[] args) {
		Maze maze = new Maze(20,4);
		MazeView mazeView = new MazeView(maze, 800);
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {}

//		mazeView.step(MazeAction.RIGHT);
//		mazeView.step(MazeAction.RIGHT);
//		mazeView.step(MazeAction.DOWN);
//		mazeView.step(MazeAction.LEFT);


	}

}
