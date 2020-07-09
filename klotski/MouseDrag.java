package klotski;
/**
 * Program name:    MouseDrag.java
 * Discussion:      Final
 * Written by:      Charles T
 * Due Date:        
 */

import java.awt.MouseInfo;
import java.awt.Point;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.Cursor;


/**
 * 
 * @author chuck
 * 
 * MouseDrag handles each block's mouse event. 
 *
 */

public class MouseDrag {
	private KlotskiBlock block;
    private KlotskiBoard currentBoard;
    private double oldX, oldY;
   
    /**
     * 
     * @param b is an array of all blocks on the klotskiBoard
     * @param i is the index of the current block in block array
     * @param board is a 2D array of all the spaces in the klotskiBoard
     * @param klotskiBoard is the current KlotskiBoard object
     */
    MouseDrag(KlotskiBlock b, KlotskiBoard klotskiBoard) {
    	block = b;
    	Rectangle currentBlock = b.getRec();
    	currentBoard = klotskiBoard;
    	
        b.getRec().setCursor(Cursor.HAND);

    	/*
    	 * Sets the current block to the mouse's position
    	 * Allows player to drag the game objects
    	 */
        b.getRec().setOnMouseDragged(e -> {
        	
        	currentBlock.toFront();
        	double blockXBorder = 400 - b.getRec().getWidth();
        	double blockYBorder = 500 - b.getRec().getHeight();
        	
        	// Right and left border walls
        	if (currentBlock.getX() >= blockXBorder && e.getX() > blockXBorder) {
        		currentBlock.setX(blockXBorder);
        	}
        	else if (currentBlock.getX() <= 0 && e.getX() < 100) {
        		currentBlock.setX(0);
        	}
        	else 
        		currentBlock.setX(e.getX() - (currentBlock.getWidth() / 2));
        	
        	// Top and bottom border walls
        	if (currentBlock.getY() >= blockYBorder && e.getY() > blockYBorder) {
        		currentBlock.setY(blockYBorder);
        	}
        	else if (currentBlock.getY() <= 0 && e.getY() < 100) {
        		currentBlock.setY(0);
        	}
        	else 
        		currentBlock.setY(e.getY() - (currentBlock.getHeight() / 2));  
        	     	
        	e.consume();
        });
        
        /*
         * (1)Store the old position when mouse is pressed.
         */
        b.getRec().setOnMousePressed(e -> {
        	// 1
        	oldX = b.getRec().getX();
        	oldY = b.getRec().getY();
 
        	e.consume();
        });
        
        /*
         * When mouse is released on a game object, check to make sure the space is available.
         */
        b.getRec().setOnMouseReleased(e -> {
        	//Rectangle currentBlock = b.getRec();
        	double x = currentBlock.getX();
        	double y = currentBlock.getY();
        	int flag = -1;
        	Block pushBlock = new Block(block.getPosition(), block.getIndex(), currentBoard.getBoard());
        	printCurrentBoard();
        	
        	System.out.println("Position of currentBlock when mouse released: " + "(" + x + ", " + y + ")");
        	
        	if (block.getType() == "smallSquare") {
            	flag = movingLogicSmallSquare(x, y);         	
        	}
        	else if (block.getType() == "verticalRectangle") {
        		flag = movingLogicVerticalRectangle(x, y);
        	}
        	else if (block.getType() == "horizontalRectangle") {
        		flag = movingLogicHorizontalRectangle(x, y);
        	}
        	else if (block.getType() == "bigSquare") {
        		flag = movingLogicBigSquare(x, y);
        	}
        	else {
        		System.out.println("Error, no block type...");
        	}
        	
        	
        	if (flag == -1) {
        		b.setPosition(b.getPosition());
        		System.out.println("Block does not move...");
        	}
        	else if (flag == 1) {
        		klotskiBoard.getUndoStack().pushUndoStack(pushBlock); 
        		UndoStack.printUndoStack();  
        		Klotski.setMovesText();
        	}
        	else {
        		Klotski.setMovesText();
        	}
     
        	printCurrentBoard();
        	e.consume();
        });
    }
    
    public void setOldCoordinates(double x, double y) {
    	oldX = x;
    	oldY = y;
    }
    
    public void setPositionOfBlock(double x, double y) {	
    	int flag = -1;
    	//Block pushBlock = new Block(block.getPosition(), block.getIndex(), currentBoard.getBoard());
    	    	
    	if (block.getType() == "smallSquare") {
        	flag = movingLogicSmallSquare(x, y);         	
    	}
    	else if (block.getType() == "verticalRectangle") {
    		flag = movingLogicVerticalRectangle(x, y);
    	}
    	else if (block.getType() == "horizontalRectangle") {
    		flag = movingLogicHorizontalRectangle(x, y);
    	}
    	else if (block.getType() == "bigSquare") {
    		flag = movingLogicBigSquare(x, y);
    	}
    	else {
    		System.out.println("Error, no block type...");
    	}
    	
    	
    	if (flag == -1) {
    		block.setPosition(block.getPosition());
    		System.out.println("Block does not move...");
    	}
    	else if (flag == 1) {
    		//b.getUndoStack().pushUndoStack(pushBlock); 
    		//UndoStack.printUndoStack();  
    		//Klotski.setMovesText();
    	}
    	else {
    		//Klotski.setMovesText();
    	}
 
    	printCurrentBoard();
    }

    /**
     * Moving Logic for smallSquare blocks
     * 
     * @param x is the center x coordinate of the block
     * @param y is the center y coordinate of the block
     * @return flag 1 if block moves else return -1
     */
    public int movingLogicSmallSquare(double x, double y) {
    	int i, j;
    	int flag = -1;
    	
    	Point location = new Point((int) x, (int) y);
    	int locationX = (int) x;
    	int locationY = (int) y;
		Point newLocation = currentBoard.getBoardPositions()[0][0];
		int newLocationX = 0;
		int newLocationY = 0;
		double minDistance = location.distance(newLocation);
		
		// Find the closest point to the block's drop point.
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 5; j++) {
    			if (location.distance(currentBoard.getBoardPositions()[i][j]) < minDistance &&
    					currentBoard.getBoardPositions()[i][j] != currentBoard.getBoardPositions()[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(currentBoard.getBoardPositions()[i][j]);
    				newLocation = currentBoard.getBoardPositions()[i][j];
    				newLocationX = i;
    				newLocationY = j;
    			}
			}
		}
		
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if (currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY)) {
				block.setPosition(newLocation);
	    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = block.getIndex();
	    		flag = 1;
			}
		}
		return flag;
    }
    
    public boolean isLegalMove(KlotskiBlock kblock, Point moveHere) {
    	boolean flag1 = false;
    	boolean flag2 = false;
    	
		// Check to make sure new location is empty and there is a clear path to that space.
    	if (kblock.getType() == "smallSquare") {
    		if (currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1) {
    			flag1 = true;
    		}
    	}
    	else if (kblock.getType() == "verticleRectangle") {
    		if ((currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
    				 currentBoard.getBoard()[(int) moveHere.getX() / 100][((int) moveHere.getY() /100 + 1)] == block.getIndex()) ||
    				(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == block.getIndex() &&
    				 currentBoard.getBoard()[(int) moveHere.getX() / 100][((int) moveHere.getY() /100 + 1)] == -1) ||
    				(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
    				 currentBoard.getBoard()[(int) moveHere.getX() / 100][((int) moveHere.getY() /100 + 1)] == -1)		 
    					) {
    			flag1 = true;
    		}
    	}
    	else if (kblock.getType() == "horizontleRectangle") {
    		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
    		if ((currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == block.getIndex()) ||
    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == block.getIndex() &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == -1) ||
    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == -1)		 
    				) {
    			flag1 = true;
    		} 		
    	}
    	else if (kblock.getType() == "bigSquare") {
    		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
    		if ((currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == block.getIndex() &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == block.getIndex()) ||
    				
    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == block.getIndex() &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == block.getIndex() &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == -1) ||
    			
    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == block.getIndex() &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == block.getIndex()) ||
    			
    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == block.getIndex() &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == block.getIndex() &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == -1 &&
    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == -1)
    				) {
    	    		flag1 = true;
    		}
    	}
    	else
    		System.out.println("No Type...");
    	
		if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), (int) moveHere.getX(), (int) moveHere.getY())) {
			flag2 = true;
		}
		
		if (flag1 == true && flag2 == true) {
			return true;
		}
		else {
			return false;
		}
    }
    
    /**
     * Moving Logic for verticalRectangle blocks
     * 
     * @param x is the center x coordinate of the block
     * @param y is the center y coordinate of the block
     * @return flag 1 if block moves else return -1
     */
    public int movingLogicBigSquare(double x, double y) {
    	int i, j;
    	int flag = -1;
    	Point location = new Point((int) x, (int) y);
    	int locationX = (int) x;
    	int locationY = (int) y;
		Point newLocation = currentBoard.getBoardPositions()[0][0];
		int newLocationX = 0;
		int newLocationY = 0;
		double minDistance = location.distance(newLocation);
    	
		// Find the closest point to the block's drop point.
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			if (location.distance(currentBoard.getBoardPositions()[i][j]) < minDistance &&
    					currentBoard.getBoardPositions()[i][j] != currentBoard.getBoardPositions()[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(currentBoard.getBoardPositions()[i][j]);
    				newLocation = currentBoard.getBoardPositions()[i][j];
    				newLocationX = i;
    				newLocationY = j;
    			}
    		}
    	}
    	
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if ((currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == block.getIndex() &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == block.getIndex()) ||
				
			(currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == block.getIndex() &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == block.getIndex() &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == -1) ||
			
			(currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == block.getIndex() &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == block.getIndex()) ||
			
			(currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == block.getIndex() &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == block.getIndex() &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == -1)
				) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			//if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY)) {
				block.setPosition(newLocation);
	    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		currentBoard.getBoard()[(int) ((oldX / 100) + 1)][(int) (oldY / 100)] = -1;
	    		currentBoard.getBoard()[(int) (oldX / 100)][(int) ((oldY / 100) + 1)] = -1;
	    		currentBoard.getBoard()[(int) ((oldX / 100) + 1)][(int) ((oldY / 100) + 1)] = -1;
	    		currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = block.getIndex();
	    		currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] = block.getIndex();
	    		currentBoard.getBoard()[(int) newLocation.getX() / 100][((int) newLocation.getY() /100) + 1] = block.getIndex();
	    		currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] = block.getIndex();
	    		flag = 1;
			//}
		}
		
		return flag;  	
    }
    
    /**
     * Moving Logic for verticalRectangle blocks
     * 
     * @param x is the center x coordinate of the block
     * @param y is the center y coordinate of the block
     * @return flag 1 if block moves else return -1
     */
    public int movingLogicVerticalRectangle(double x, double y) {
     	int i, j;
    	int flag = -1;
    	Point location = new Point((int) x, (int) y);
    	int locationX = (int) x;
    	int locationY = (int) y;
		Point newLocation = currentBoard.getBoardPositions()[0][0];
		int newLocationX = 0;
		int newLocationY = 0;
		double minDistance = location.distance(newLocation);
    	
		// Find the closest point to the block's drop point.
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			if (location.distance(currentBoard.getBoardPositions()[i][j]) < minDistance &&
    					currentBoard.getBoardPositions()[i][j] != currentBoard.getBoardPositions()[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(currentBoard.getBoardPositions()[i][j]);
    				newLocation = currentBoard.getBoardPositions()[i][j];
    				newLocationX = i;
    				newLocationY = j;
    			}
    		}
    	}
    	
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if ((currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[(int) newLocation.getX() / 100][((int) newLocation.getY() /100 + 1)] == block.getIndex()) ||
			(currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == block.getIndex() &&
			 currentBoard.getBoard()[(int) newLocation.getX() / 100][((int) newLocation.getY() /100 + 1)] == -1) ||
			(currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[(int) newLocation.getX() / 100][((int) newLocation.getY() /100 + 1)] == -1)		 
				) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY)) {
				block.setPosition(newLocation);
	    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100) + 1] = -1;
	    		currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = block.getIndex();
	    		currentBoard.getBoard()[(int) newLocation.getX() / 100][((int) newLocation.getY() /100) + 1] = block.getIndex();
	    		flag = 1;
			}
		}
		
		return flag;
    }

    /**
     * Moving Logic for horizontalRectangle blocks
     * 
     * @param x is the center x coordinate of the block
     * @param y is the center y coordinate of the block
     * @return flag 1 if block moves else return -1
     */
    public int movingLogicHorizontalRectangle(double x, double y) {
    	int i, j;
    	int flag = -1;
    	Point location = new Point((int) x, (int) y);
    	int locationX = (int) x;
    	int locationY = (int) y;
		Point newLocation = currentBoard.getBoardPositions()[0][0];
		int newLocationX = 0;
		int newLocationY = 0;
		double minDistance = location.distance(newLocation);
    	
		// Find the closest point to the block's drop point.
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			if (location.distance(currentBoard.getBoardPositions()[i][j]) < minDistance &&
    					currentBoard.getBoardPositions()[i][j] != currentBoard.getBoardPositions()[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(currentBoard.getBoardPositions()[i][j]);
    				newLocation = currentBoard.getBoardPositions()[i][j];
    				newLocationX = i;
    				newLocationY = j;
    			}
    		}
    	}
    	
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if ((currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == block.getIndex()) ||
			(currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == block.getIndex() &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1) ||
			(currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1)		 
				) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY)) {
				block.setPosition(newLocation);
	    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		currentBoard.getBoard()[(int) ((oldX / 100) + 1)][(int) (oldY / 100)] = -1;
	    		currentBoard.getBoard()[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = block.getIndex();
	    		currentBoard.getBoard()[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] = block.getIndex();
	    		flag = 1;
			}
		}
		
		return flag;
    }
    
    /**
     * Makes it so blocks cannot skip over other blocks. There must be a clear path for the block to move to a space.
     * @param pathX is x-coordinate of the path a block can take.
     * @param pathY is y-coordinate of the path a block can take.
     * @param destinationX is the x-coordinate of the destination the block is traveling.
     * @param destinationY is the y-coordinate of the destination the block is traveling.
     * @return a boolean value that tells if the move is legal.
     */
    public boolean clearPathMovingLogic(int pathX, int pathY, int destinationX, int destinationY) {
    	boolean flag1 = false;
    	boolean flag2 = false;

    	if (pathX == destinationX && pathY == destinationY) 
    		return true;
    	else if (pathX == destinationX && pathY < destinationY) {
    		if (currentBoard.getBoard()[pathX][pathY + 1] == -1 || currentBoard.getBoard()[pathX][pathY + 1] == block.getIndex()) 
    			return clearPathMovingLogic(pathX, pathY + 1, destinationX, destinationY);
    		else 
    			return false;
    	}
    	else if (pathX == destinationX && pathY > destinationY) {
    		if (currentBoard.getBoard()[pathX][pathY - 1] == -1 || currentBoard.getBoard()[pathX][pathY - 1] == -1) 
    			return clearPathMovingLogic(pathX, pathY - 1, destinationX, destinationY);
    		else 
    			return false;
    	}
    	else if (pathX < destinationX && pathY < destinationY) {
    		if (currentBoard.getBoard()[pathX][pathY + 1] == -1 || currentBoard.getBoard()[pathX][pathY + 1] == block.getIndex()) 
    			flag1 = clearPathMovingLogic(pathX, pathY + 1, destinationX, destinationY);
    		else 
    			flag1 = false;
    		
    		if (currentBoard.getBoard()[pathX + 1][pathY] == -1 || currentBoard.getBoard()[pathX + 1][pathY] == block.getIndex()) 
    			flag2 = clearPathMovingLogic(pathX + 1, pathY, destinationX, destinationY);
    		else 
    			flag2 = false;
    		
    		if (flag1 == false && flag2 == false) 
    			return false; 
    		else
    			return true;
    	}
    	else if (pathY == destinationY && pathX < destinationX) {
    		if (currentBoard.getBoard()[pathX + 1][pathY] == -1 || currentBoard.getBoard()[pathX + 1][pathY] == block.getIndex()) 
    			return clearPathMovingLogic(pathX + 1, pathY, destinationX, destinationY);
    		else 
    			return false;
    	}
    	else if (pathY == destinationY && pathX > destinationX) {
    		if (currentBoard.getBoard()[pathX - 1][pathY] == -1 || currentBoard.getBoard()[pathX - 1][pathY] == block.getIndex()) 
    			return clearPathMovingLogic(pathX - 1, pathY, destinationX, destinationY);
    		else 
    			return false;
    	}
    	else if (pathX < destinationX && pathY > destinationY) {
    		if (currentBoard.getBoard()[pathX][pathY - 1] == -1 || currentBoard.getBoard()[pathX][pathY - 1] == block.getIndex()) 
    			flag1 = clearPathMovingLogic(pathX, pathY - 1, destinationX, destinationY);
    		else 
    			flag1 = false;
    		
    		if (currentBoard.getBoard()[pathX + 1][pathY] == -1 || currentBoard.getBoard()[pathX + 1][pathY] == block.getIndex()) 
    			flag2 = clearPathMovingLogic(pathX + 1, pathY, destinationX, destinationY);
    		else 
    			flag2 = false;
    		
    		if (flag1 == false && flag2 == false) 
    			return false; 
    		else
    			return true;
    	}
    	else if (pathX > destinationX && pathY > destinationY) {
    		if (currentBoard.getBoard()[pathX][pathY - 1] == -1 || currentBoard.getBoard()[pathX][pathY - 1] == block.getIndex()) 
    			flag1 = clearPathMovingLogic(pathX, pathY - 1, destinationX, destinationY);
    		else 
    			flag1 = false;
    		
    		if (currentBoard.getBoard()[pathX - 1][pathY] == -1 || currentBoard.getBoard()[pathX - 1][pathY] == block.getIndex()) 
    			flag2 = clearPathMovingLogic(pathX - 1, pathY, destinationX, destinationY);
    		else 
    			flag2 = false;
    		
    		if (flag1 == false && flag2 == false) 
    			return false; 
    		else
    			return true;
    	}
    	else if (pathX > destinationX && pathY < destinationY) {
    		if (currentBoard.getBoard()[pathX][pathY + 1] == -1 || currentBoard.getBoard()[pathX][pathY + 1] == block.getIndex()) 
    			flag1 = clearPathMovingLogic(pathX, pathY + 1, destinationX, destinationY);
    		else 
    			flag1 = false;
    		
    		if (currentBoard.getBoard()[pathX - 1][pathY] == -1 || currentBoard.getBoard()[pathX - 1][pathY] == block.getIndex()) 
    			flag2 = clearPathMovingLogic(pathX - 1, pathY, destinationX, destinationY);
    		else 
    			flag2 = false;
    		
    		if (flag1 == false && flag2 == false) 
    			return false; 
    		else
    			return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public boolean movingLogicAllBlocks(double x, double y) {
    	boolean flag = true;
    	int newPositionX = (int) (x / 100);
    	int newPositionY = (int) (y / 100);
    	int oldPositionX = (int) (oldX / 100);
    	int oldPositionY = (int) (oldY / 100);

    	if (Math.abs(newPositionX - oldPositionX) > 1 || Math.abs(newPositionY - oldPositionY) > 1 ||
    	   (Math.abs(newPositionX - oldPositionX) == 1 && Math.abs(newPositionY - oldPositionY) == 1)
    		) {
    		flag = false;
    	}
    	
    	return flag;
    }
    
    public void printCurrentBoard() {
    	int i, j;
    	System.out.println("\nCurrent Board positions: \n");
    	
    	for(i = 0; i < 5; i++) {
    		System.out.print("		");
    		for (j = 0; j < 4; j++) {
    			if (j == 0) {
    				System.out.print("\n");
    			}
    	    	System.out.print(" " + currentBoard.getBoard()[j][i] + " ");
    		}
    	}
    }
    
    public KlotskiBlock getCurrentBlock() {
        return block;
    }
    
    public double getOldX() {
        return oldX;
    }
    
    public double getOldY() {
        return oldY;
    }
    
    public void setCurrentBlockPosition(Point p) {
    	block.setPosition(p);
    }
    
    public void setCurrentBlockPosition(int x, int y) {
    	block.setPosition(new Point(x, y));
    }
    
    public void setOldX(int x) {
        oldX = x;
    }
    
    public void setOldY(int y) {
        oldY = y;
    }
}