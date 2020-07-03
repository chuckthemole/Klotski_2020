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
        	double x = currentBlock.getX() + (currentBlock.getWidth() / 2);
        	double y = currentBlock.getY() + (currentBlock.getHeight() / 2);
        	int flag = -1;
        	//KlotskiBlock pushBlock = block;
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
        	else {
        		klotskiBoard.getUndoStack().pushUndoStack(pushBlock); 
        		klotskiBoard.getUndoStack().printUndoStack();  
        		Klotski.setMovesText();
        	}
     
        	printCurrentBoard();
        	e.consume();
        });
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
    	
    	/*
    	 * 
    	 * To Do's
    	 * The block should only move in clear path. Should not jump blocks
    	 */
    	
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			try {
	    			//  Position (i, j)
	            	if(x <= (100 + (i*100)) && y <= (100 +(j*100)) && x >= (i*100) && y >= (j*100)) {
	            		if(block.getType() == "smallSquare" && currentBoard.getBoard()[i][j] == -1) {
	            			// Set the block in empty position
	            			block.setPosition(currentBoard.getBoardPositions()[i][j]);
	                		
	                		// Update board
	                		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	                		currentBoard.getBoard()[i][j] = block.getIndex();
	                		i = 5;
	                		flag = 1;
	                		return flag;
	            		}
	            	}
    			}
    			catch(Exception e) {
    				System.out.println("*********** Error  " + e + "  Error*************");
    			}
    		}
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
    public int movingLogicBigSquare(double x, double y) {
    	int i, j;
    	int flag = -1;
    	
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			
    			try {
	    			//  Position (i, j)
	            	if(x <= (200 + (i*100)) && y <= (200 + (j*100)) && x >= (i*100) && y >= (j*100)) {
	            		if(block.getType() == "bigSquare") {
	            			if (
	            					( currentBoard.getBoard()[i][j] == -1 && currentBoard.getBoard()[i][j + 1] == block.getIndex() &&
	            					  currentBoard.getBoard()[i + 1][j] == -1 && currentBoard.getBoard()[i + 1][j + 1] == block.getIndex() ) || 
	            					( currentBoard.getBoard()[i][j] == block.getIndex() && currentBoard.getBoard()[i][j + 1] == -1 &&
	            					  currentBoard.getBoard()[i + 1][j] == block.getIndex() && currentBoard.getBoard()[i + 1][j + 1] == -1 ) ||
	            					( currentBoard.getBoard()[i][j] == -1 && currentBoard.getBoard()[i][j + 1] == -1 &&
	            					  currentBoard.getBoard()[i + 1][j] == block.getIndex() && currentBoard.getBoard()[i + 1][j + 1] == block.getIndex() ) ||
	            					( currentBoard.getBoard()[i][j] == block.getIndex() && currentBoard.getBoard()[i][j + 1] == block.getIndex() &&
	                  				  currentBoard.getBoard()[i + 1][j] == -1 && currentBoard.getBoard()[i + 1][j + 1] == -1 )
	            				) {
	                			// Set the block in empty position
	                			block.setPosition(currentBoard.getBoardPositions()[i][j]);
	                    		
	                    		// Update board
	                    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	                    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100) + 1] = -1;
	                    		currentBoard.getBoard()[(int) (oldX / 100) + 1][(int) (oldY / 100)] = -1;
	                    		currentBoard.getBoard()[(int) (oldX / 100) + 1][(int) (oldY / 100) + 1] = -1;                    		
	                    		currentBoard.getBoard()[i][j] = block.getIndex();
	                    		currentBoard.getBoard()[i][j + 1] = block.getIndex();
	                    		currentBoard.getBoard()[i + 1][j] = block.getIndex();
	                    		currentBoard.getBoard()[i + 1][j + 1] = block.getIndex();
	                    		i = 5;
	                    		flag = 1;
	                    		return flag;
	            			}
	            		}
	            	}
    			}
    			catch(Exception e) {
    				System.out.println("*********** Error  " + e + "  Error*************");
    			}
    		}
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
    	
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			
    			try {
	    			//  Position (i, j)
	            	if(x <= (100 + (i*100)) && y <= (200 + (j*100)) && x >= (i*100) && y >= (j*100)) {
	            		if(block.getType() == "verticalRectangle") {
	            			if (
	            					(currentBoard.getBoard()[i][j] == -1 && currentBoard.getBoard()[i][j + 1] == block.getIndex()) || 
	            					(currentBoard.getBoard()[i][j] == block.getIndex() && currentBoard.getBoard()[i][j + 1] == -1) ||
	            					(currentBoard.getBoard()[i][j] == -1 && currentBoard.getBoard()[i][j + 1] == -1)
	            				) {
	                			// Set the block in empty position
	                			block.setPosition(currentBoard.getBoardPositions()[i][j]);
	                    		
	                    		// Update board
	                    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	                    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100) + 1] = -1;
	                    		currentBoard.getBoard()[i][j] = block.getIndex();
	                    		currentBoard.getBoard()[i][j + 1] = block.getIndex();
	                    		i = 5;
	                    		flag = 1;
	                    		return flag;
	            			}
	            		}
	            	}
    			}
    			catch(Exception e) {
    				System.out.println("*********** Error  " + e + "  Error*************");
    			}
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
    	
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			
    			try {
	    			//  Position (i, j)
	            	if(x <= (200 + (i*100)) && y <= (100 + (j*100)) && x >= (i*100) && y >= (j*100)) {
	            		if(block.getType() == "horizontalRectangle") {
	            			if (
	            					(currentBoard.getBoard()[i][j] == -1 && currentBoard.getBoard()[i + 1][j] == block.getIndex()) || 
	            					(currentBoard.getBoard()[i][j] == block.getIndex() && currentBoard.getBoard()[i + 1][j] == -1) ||
	            					(currentBoard.getBoard()[i][j] == -1 && currentBoard.getBoard()[i + 1][j] == -1)
	            				) {
	                			// Set the block in empty position
	                			block.setPosition(currentBoard.getBoardPositions()[i][j]);
	                    		
	                    		// Update board
	                    		currentBoard.getBoard()[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	                    		currentBoard.getBoard()[(int) (oldX / 100) + 1][(int) (oldY / 100)] = -1;
	                    		currentBoard.getBoard()[i][j] = block.getIndex();
	                    		currentBoard.getBoard()[i + 1][j] = block.getIndex();
	                    		i = 5;
	                    		flag = 1;
	                    		return flag;
	            			}
	            		}
	            	}
    			}
    			catch(Exception e) {
    				System.out.println("*********** Error  " + e + "  Error*************");
    			}
    		}
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