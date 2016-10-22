package problems.maze;

import problems.maze.Maze;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;


/** This class allows showing a maze */
public class MazeViewPanel extends JPanel{
	
	// Colors
	private static Color backGroundColor= new Color(20,150,20);
	private static Color wallColor = new Color(100,100,100);
	private static Color boundsColor = new Color(200,200,200);
	private static Color woodColor = new Color(120,100,50);

	
	// Images
	public static final Image hamster = Toolkit.getDefaultToolkit().getImage("src/imgs/hamster.png");	
	public static final Image cheese = Toolkit.getDefaultToolkit().getImage("src/imgs/queso.png");	

	// Maze and status
	private Maze maze;					// Maze	
	private int[] posHamster = {0,0};	// 2 element array with x and y coordinates of the hamster
	
	// Some measures of interest
	private int sizePx = 600; 				// Size of the view
	private int cellSizePx;					// Size of each cell	
	private int marginPx;					// Size of the margin
	private int[] posHamsterPx = {0,0};		// Coordinates of the hamster in pixels.
	private int[] posCheesePx = {0,0};  	// Coordinates of the cheese in pixels.
	private double speedPx = 10;	    	// Speed of the hamster (pixels each 0.05s)
	
	// Shown images
	BufferedImage mazeImage;		// Image of the maze
	Image scaledCheese;			    // Scaled cheese
	Image scaledHamster;			// Scaled hamster
	

	/**
	 * Builds the view panel given a maze and its size in pixels
	 */
	public MazeViewPanel(Maze maze, int sizePx){
		// Calculates dimensions
		this.maze = maze;
		this.sizePx = sizePx;
		cellSizePx = (sizePx-40) / maze.size;
		marginPx = (sizePx - cellSizePx * maze.size)/2;
		speedPx = (cellSizePx*4)/20;	// pixels each 1/20 second  (Four cells/second)
		
		// Calculates the positions of both the hamster and the cheese 
		this.posHamster[0] = 0;
		this.posHamster[1] = maze.inputX;
		this.posHamsterPx = posToPx(0,maze.inputX);

		this.posCheesePx = posToPx(maze.size-1, maze.outputX);
		this.posCheesePx[0] = this.posCheesePx[0] + (int)(cellSizePx*0.1);
		this.posCheesePx[1] = this.posCheesePx[1] + (int)(cellSizePx*0.1);
		
		// Scales the images according to the size
		scaledCheese = cheese.getScaledInstance((int)(cellSizePx*0.8), (int)(cellSizePx*0.8), Image.SCALE_SMOOTH);		 	
		scaledHamster = hamster.getScaledInstance((int)(cellSizePx*0.8), (int)(cellSizePx*0.8), Image.SCALE_SMOOTH);
		
		// Generates the background
		generateMazeImage();		
	}
	
	/** Generates the main maze image (Background) */
	private void generateMazeImage(){
		mazeImage=new BufferedImage(sizePx, sizePx, BufferedImage.TYPE_INT_RGB);
		Graphics2D mazeGraphics2D = mazeImage.createGraphics();		
		mazeGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Creates the graphics
		mazeGraphics2D.setColor(backGroundColor);
		mazeGraphics2D.fillRect(0, 0, sizePx, sizePx);	
		
		// Paints the walls
		mazeGraphics2D.setColor(wallColor);
		int posX, posY;
		int[] posWallPx = {0,0};
		for (posX=0;posX<maze.size;posX++){
			for (posY=0;posY<maze.size;posY++)
				if (maze.cells[posY][posX]==2){
					// Notice that for the screen we use x,y instead of y,x
					posWallPx= posToPx(posY,posX);
					mazeGraphics2D.fill3DRect(posWallPx[1], posWallPx[0], cellSizePx, cellSizePx,true);	
				}
		}	
		
		// Painst the wood
		// Paints the walls
		mazeGraphics2D.setColor(woodColor);
		posWallPx[0]=0; posWallPx[1]=0; 
		for (posX=0;posX<maze.size;posX++){
			for (posY=0;posY<maze.size;posY++)
				if (maze.cells[posY][posX]==1){
					// Notice that for the screen we use x,y instead of y,x
					posWallPx= posToPx(posY,posX);
					mazeGraphics2D.fill3DRect(posWallPx[1], posWallPx[0], cellSizePx, cellSizePx,true);	
				}
		}			
		
		// Paints the bounds
		mazeGraphics2D.setColor(boundsColor);
		int posBoundPx = 0;
		for (int pos=0;pos<maze.size;pos++){
			posBoundPx= posToPx(pos);
			// Upper
			if (pos!=maze.inputX)
				mazeGraphics2D.fill3DRect(posBoundPx, marginPx/2, cellSizePx, marginPx/2,true);	
			// Bottom
			if (pos!=maze.outputX)			
				mazeGraphics2D.fill3DRect(posBoundPx, sizePx-marginPx, cellSizePx, marginPx/2,true);
			// Left
			mazeGraphics2D.fill3DRect(marginPx/2, posBoundPx, marginPx/2, cellSizePx,true);
			// Right
			mazeGraphics2D.fill3DRect(sizePx-marginPx, posBoundPx, marginPx/2, cellSizePx,true);			
		}
		
		// Corners
		mazeGraphics2D.fill3DRect(marginPx/2, marginPx/2, marginPx/2, marginPx/2,true);
		mazeGraphics2D.fill3DRect(sizePx-marginPx, marginPx/2, marginPx/2, marginPx/2,true);
		mazeGraphics2D.fill3DRect(marginPx/2, sizePx-marginPx, marginPx/2, marginPx/2,true);
		mazeGraphics2D.fill3DRect(sizePx-marginPx, sizePx-marginPx, marginPx/2, marginPx/2,true);
	}
	
	
	/** Paints the component */
	public void paintComponent(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,	RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.drawImage(mazeImage,0,0,this);	
				
//		posHamsterPx[0] = posHamsterPx[0] + (int)(cellSizePx*0.1); // To center the hamster
//		posHamsterPx[1] = posHamsterPx[1] + (int)(cellSizePx*0.1);
		
	    graphics2D.drawImage(scaledHamster, posHamsterPx[1], posHamsterPx[0], this);
		
	    // Only paints the cheese when the hamster has not reached it.
	    if ((posHamster[1]!=maze.outputX) || (posHamster[0]!=maze.size-1)) 
	    	graphics2D.drawImage(scaledCheese, posCheesePx[1], posCheesePx[0], this);
		
		graphics2D.dispose();		
	}
	
	// Changes the position of the hamster
	public void changeHamsterToPosition(int y, int x){
		posHamster[0]=y;
		posHamster[1]=x;
		posHamsterPx = posToPx(y, x);
		repaint();
	}
	
	// Moves the hamster to a certain position.
	public void moveHamsterToPosition(int y, int x){
		// Calculates the final position
		int[] goalHamsterPx = posToPx(y, x);
//		goalHamsterPx[0] = goalHamsterPx[0] + (int)(cellSizePx*0.1); // To center the hamster
//		goalHamsterPx[1] = goalHamsterPx[1] + (int)(cellSizePx*0.1); 

		// Calculates the distance
		int distY = goalHamsterPx[0]-posHamsterPx[0];
		int distX = goalHamsterPx[1]-posHamsterPx[1];
		
		double dist = Math.sqrt(distX*distX+distY*distY);
		
		// Calculates the number of frames and moves the mouse.
		int numFrames = (int) (dist/speedPx);
		for ( ; numFrames>0; numFrames--){
			posHamsterPx[0]=posHamsterPx[0]+(goalHamsterPx[0]-posHamsterPx[0])/numFrames;
			posHamsterPx[1]=posHamsterPx[1]+(goalHamsterPx[1]-posHamsterPx[1])/numFrames;
			repaint();
			// Waits 0.05 seconds
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
		
		// The hamster must be located in the right position in the last frame.
		posHamsterPx[0] = goalHamsterPx[0];
		posHamsterPx[1] = goalHamsterPx[1];
		posHamster[0]=y;
		posHamster[1]=x;
		repaint();
	}
	
	/** Moves the hamster one position Left*/
	public void moveLeft(){
		if (posHamster[1]>0)
			moveHamsterToPosition(posHamster[0], posHamster[1]-1);
	}
	/** Moves the hamster one position Right*/

	public void moveRight(){
		if (posHamster[1]<maze.size-1)
			moveHamsterToPosition(posHamster[0], posHamster[1]+1);
	}
	
	/** Moves the hamster one position Up*/
	public void moveUp(){
		if (posHamster[0]>0)
			moveHamsterToPosition(posHamster[0]-1, posHamster[1]);
	}
	
	/** Moves the hamster one position Down*/
	public void moveDown(){
		if (posHamster[0]<maze.size-1)
			moveHamsterToPosition(posHamster[0]+1, posHamster[1]);
	}
	
	// Changes a position to pixels
	private int posToPx(int x){
		return (int) (x * cellSizePx + marginPx); 
	}
	// Changes a position to pixels
	private int[] posToPx(int y, int x){
		int[] posPx = {0,0};
		posPx[0] = (int) (y * cellSizePx + marginPx); 
		posPx[1] = (int) (x * cellSizePx + marginPx); 
		return posPx;
	}
	
	/** Returns the dimension of the view */
    public Dimension getPreferredSize() {
        return new Dimension(sizePx, sizePx);
    }	
    
	/** Main function, used for testing. */
	public static void main(String[] args) {
		Maze maze = new Maze(8,4);
		MazeView mazeView = new MazeView(maze, 600);
	}

}
