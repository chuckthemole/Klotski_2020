package klotski;
/**
 * Program name:    KlotskiBoard.java
 * Discussion:      Final
 * Written by:      Charles T
 * Due Date:        
 */

import java.awt.Point;
import klotski.MouseDrag;

/*
KlotskiBoard sets the klotski blocks up on the board
in their initial positions.
*/

public class KlotskiBoard extends Klotski {    
	private static boolean mouseActive = true;
    private KlotskiBlock[] blocks;
    private int[][] board;
    private int[][] boardByType;	// 1 is small block; 2 is vertical block; 3 is horizontal block; 4 is big block;
    private UndoStack undoStack;
    final private Point [][] boardPositions;
    private MouseDrag[] mouse;

    KlotskiBoard() {
    	mouse = new MouseDrag[10]; 
    	undoStack = new UndoStack();
        blocks = new KlotskiBlock[10];
        board = new int[4][5];  
        boardByType = new int[4][5];    
        boardPositions = new Point[4][5];

        /*
         * These are the initial board positions of each block.
         * the int refers to the block index unique identifier.
         *  - 1 refers to an empty space on the board.
         *  board[0][0] is the top left.
         *  board[3][4] is the bottom right
         */
        board[0][0] = 4;
        board[0][1] = 4;
        board[0][2] = 5;
        board[0][3] = 5;
        board[0][4] = 3;
        board[1][0] = 9;
        board[1][1] = 9;
        board[1][2] = 8;
        board[1][3] = 1;
        board[1][4] = -1;      
        board[2][0] = 9;
        board[2][1] = 9;
        board[2][2] = 8;
        board[2][3] = 2;
        board[2][4] = -1;        
        board[3][0] = 6;
        board[3][1] = 6;
        board[3][2] = 7;
        board[3][3] = 7;
        board[3][4] = 0;
        
        boardByType[0][0] = 2;
        boardByType[0][1] = 2;
        boardByType[0][2] = 2;
        boardByType[0][3] = 2;
        boardByType[0][4] = 1;
        boardByType[1][0] = 4;
        boardByType[1][1] = 4;
        boardByType[1][2] = 3;
        boardByType[1][3] = 1;
        boardByType[1][4] = -1;      
        boardByType[2][0] = 4;
        boardByType[2][1] = 4;
        boardByType[2][2] = 3;
        boardByType[2][3] = 1;
        boardByType[2][4] = -1;        
        boardByType[3][0] = 2;
        boardByType[3][1] = 2;
        boardByType[3][2] = 2;
        boardByType[3][3] = 2;
        boardByType[3][4] = 1;
        
        /**
         * Create visible, movable blocks for the board
         */
        //one by one blocks
        blocks[0] = new KlotskiBlock("smallSquare", 300, 400, 0);  
        blocks[1] = new KlotskiBlock("smallSquare", 100, 300, 1);
        blocks[2] = new KlotskiBlock("smallSquare", 200, 300, 2);
        blocks[3] = new KlotskiBlock("smallSquare", 0, 400, 3);
        
        //one by two blocks 
        blocks[4] = new KlotskiBlock("verticalRectangle", 0, 0, 4);          
        blocks[5] = new KlotskiBlock("verticalRectangle", 0, 200, 5);         
        blocks[6] = new KlotskiBlock("verticalRectangle", 300, 0, 6);       
        blocks[7] = new KlotskiBlock("verticalRectangle", 300, 200, 7);  
        
        //two by one block 
        blocks[8] = new KlotskiBlock("horizontalRectangle", 100, 200, 8); 
        
        //two by two block        
        blocks[9] = new KlotskiBlock("bigSquare", 100, 0, 9);         
        
        /*
         * Initialize the positions on the board
         */
        boardPositions[0][0] = new Point(0, 0);
        boardPositions[0][1] = new Point(0, 100);
        boardPositions[0][2] = new Point(0, 200);
        boardPositions[0][3] = new Point(0, 300);
        boardPositions[0][4] = new Point(0, 400);
        boardPositions[1][0] = new Point(100, 0);
        boardPositions[1][1] = new Point(100, 100);
        boardPositions[1][2] = new Point(100, 200);
        boardPositions[1][3] = new Point(100, 300);
        boardPositions[1][4] = new Point(100, 400);      
        boardPositions[2][0] = new Point(200, 0);
        boardPositions[2][1] = new Point(200, 100);
        boardPositions[2][2] = new Point(200, 200);
        boardPositions[2][3] = new Point(200, 300);
        boardPositions[2][4] = new Point(200, 400);        
        boardPositions[3][0] = new Point(300, 0);
        boardPositions[3][1] = new Point(300, 100);
        boardPositions[3][2] = new Point(300, 200);
        boardPositions[3][3] = new Point(300, 300);
        boardPositions[3][4] = new Point(300, 400);
        
        // Sets up mouse listeners for each block on the board.
        if (mouseActive) {
            setMouse();
        }
    }
    
    public void undo() {
    	try {
    		this.setBlockPosition(undoStack.peekUndoStack().getIndex(), undoStack.peekUndoStack().getPosition());
    		this.setBoard(undoStack.popUndoStack().getBoardPositions());
        	Klotski.setMovesText();
    	}
    	catch (Exception e) {
    		System.out.println("Stack is empty..." + e);
    	}
    }
    
    public void disableMouse() {
    	mouseActive = false;
    }
    
    public void enableMouse() {
    	mouseActive = true;
    }
    
    public boolean isMouseActive() {
    	return mouseActive;
    }
    
    public MouseDrag[] getMouse() {
    	return mouse;
    }
    
    public void setMouse() {
    	int i;
        for (i = 9; i >= 0; i--) {
            mouse[i] = new MouseDrag(blocks[i], this); 
        }  
    }
    
    public KlotskiBlock[] getBlocks() {
        return blocks;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public int[][] getBoardByType() {
        return boardByType;
    }
    
    public Point[][] getBoardPositions() {
    	return boardPositions;
    }

    public UndoStack getUndoStack() {
    	return undoStack;
    }
    
    public void setBoard(int[][] b) {
    	int i, j;
    	
    	for (i = 0; i < 4; i++) {
    		for (j = 0; j < 5; j++) {
    			board[i][j] = b[i][j];
    		}
    	}
    }
    
    public void setBoardByType(int[][] b) {
    	int i, j;
    	
    	for (i = 0; i < 4; i++) {
    		for (j = 0; j < 5; j++) {
    			boardByType[i][j] = b[i][j];
    		}
    	}
    }
    
    public KlotskiBoard copy() {
    	KlotskiBoard newBoard = new KlotskiBoard();
    	UndoStack temp = new UndoStack();
    	int i;
    	
    	while (!this.getUndoStack().isEmpty()) {
	    	temp.pushUndoStack(this.getUndoStack().popUndoStack());
    	}
    	
    	while (!temp.isEmpty()) {
    		//newBoard.setBoard(this.getBoard());
    		//newBoard.setBlockPosition(temp.peekUndoStack().getIndex(), temp.peekUndoStack().getPosition());
    		newBoard.getUndoStack().pushUndoStack(temp.peekUndoStack());
    		this.getUndoStack().pushUndoStack(temp.popUndoStack());
    	}
    	
    	newBoard.setBoard(this.getBoard());
    	newBoard.setBoardByType(this.getBoardByType());
    	for (i = 0; i < 10; i++) {
    		newBoard.setBlockPosition(i, this.getBlocks()[i].getPosition());
    	}
    	
    	return newBoard;
    } 
    
    public boolean equals(KlotskiBoard compareBoard) {
    	int i, j;
    	boolean isEqual = true;
    	
    	for (i = 0; i < 5; i++) {
    		for (j = 0; j < 4; j++) {
    			if (this.getBoard()[j][i] != compareBoard.getBoard()[j][i]) {
    				j = 4;
    				i = 5;
    				isEqual = false;
    			}
    		}
    	}
    	
    	return isEqual;
    }
    
    /**
     * Moves block to location p. This method does not update the board.
     * 
     * @param blockIndex is the index of the block to be moved.
     * @param p is the point the block is moving to.
     */
    public void setBlockPosition(int blockIndex, Point p) {
    	blocks[blockIndex].setPosition(p);
    }

    public int setBlockPosition(KlotskiBlock b, Point p) {
    	double oldX = b.getPosition().getX();
    	double oldY = b.getPosition().getY();
    	double x = p.getX();
    	double y = p.getY();
    	int flag = -1;
    	int i, j;

    	if (p.getX() <= 300 && p.getX() >= 0 && p.getY() <= 450 && p.getY() >= 0) {
	    	// Push items if flag == 1
	    	Point oldPoint = b.getPosition();
	    	int [][] oldBoard;
	    	oldBoard = new int[4][5];

	    	for (i = 0; i < 4; i++) {
	    		for (j = 0; j < 5; j++) {
	    			oldBoard[i][j] = board[i][j];
	    		}
	    	}
	    	    	
	    	//System.out.println("Position of currentBlock when mouse released: " + "(" + x + ", " + y + ")");
	    	
	    	if (b.getType() == "smallSquare") {
	        	flag = movingLogicSmallSquare(x, y, oldX, oldY, b.getIndex(), b);         	
	    	}
	    	else if (b.getType() == "verticalRectangle") {
	    		flag = movingLogicVerticalRectangle(x, y, oldX, oldY, b.getIndex(), b);
	    	}
	    	else if (b.getType() == "horizontalRectangle") {
	    		flag = movingLogicHorizontalRectangle(x, y, oldX, oldY, b.getIndex(), b);
	    	}
	    	else if (b.getType() == "bigSquare") {
	    		flag = movingLogicBigSquare(x, y, oldX, oldY, b.getIndex(), b);
	    	}
	    	else {
	    		System.out.println("Error, no block type...");
	    	}
	    	
	    	
	    	if (flag == -1) {
	    		b.setPosition(b.getPosition());
	    		//System.out.println("\n\nBlock " + b.getIndex() + " of type " + b.getType() + " does not move...");
	    	}
	    	else if (flag == 1) {
	    		undoStack.pushUndoStack(oldPoint, b.getPosition(), b.getIndex(), oldBoard);
	    		//undoStack.printUndoStack();
	    		setMovesText();
	        	//printCurrentBoard();
	    	}
	    	else {
	    		System.out.println("In winning position!!!!!!");
	    		setMovesText();
	        	//printCurrentBoard();
	    	}
	    	
	    	//printCurrentBoardByType();
    	}
    	
    	return flag;
    }
    
    public int movingLogicSmallSquare(double x, double y, double oldX, double oldY, int index, KlotskiBlock b) {
    	int i, j;
    	int flag = -1;
    	
    	Point location = new Point((int) x, (int) y);
		Point newLocation = boardPositions[0][0];
		int newLocationX = 0;
		int newLocationY = 0;
		double minDistance = location.distance(newLocation);
		
		// Find the closest point to the block's drop point.
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 5; j++) {
    			if (location.distance(boardPositions[i][j]) < minDistance &&
    					boardPositions[i][j] != boardPositions[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(boardPositions[i][j]);
    				newLocation = boardPositions[i][j];
    				newLocationX = i;
    				newLocationY = j;
    			}
			}
		}
		
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if (board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY, index)) {
				b.setPosition(newLocation);
	    		board[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = index;
	    		boardByType[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		boardByType[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = 1;
	    		flag = 1;
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
    public int movingLogicBigSquare(double x, double y, double oldX, double oldY, int index, KlotskiBlock b) {
    	int i, j;
    	int flag = -1;
    	Point location = new Point((int) x, (int) y);
		Point newLocation = boardPositions[0][0];
		double minDistance = location.distance(newLocation);
    	
		// Find the closest point to the block's drop point.
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			if (location.distance(boardPositions[i][j]) < minDistance &&
    					boardPositions[i][j] != boardPositions[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(boardPositions[i][j]);
    				newLocation = boardPositions[i][j];
    			}
    		}
    	}
    	
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if ((board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 board[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == index &&
			 board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1 &&
			 board[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == index) ||
				
			(board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == index &&
			 board[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == -1 &&
			 board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == index &&
			 board[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == -1) ||
			
			(board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 board[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == -1 &&
			 board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == index &&
			 board[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == index) ||
			
			(board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == index &&
			 board[((int) newLocation.getX() / 100)][((int) newLocation.getY() /100) + 1] == index &&
			 board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1 &&
			 board[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] == -1)
				) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			//if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY, index)) {
				b.setPosition(newLocation);
	    		board[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		board[(int) ((oldX / 100) + 1)][(int) (oldY / 100)] = -1;
	    		board[(int) (oldX / 100)][(int) ((oldY / 100) + 1)] = -1;
	    		board[(int) ((oldX / 100) + 1)][(int) ((oldY / 100) + 1)] = -1;
	    		board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = index;
	    		board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] = index;
	    		board[(int) newLocation.getX() / 100][((int) newLocation.getY() /100) + 1] = index;
	    		board[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] = index;
	    		
	    		boardByType[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		boardByType[(int) ((oldX / 100) + 1)][(int) (oldY / 100)] = -1;
	    		boardByType[(int) (oldX / 100)][(int) ((oldY / 100) + 1)] = -1;
	    		boardByType[(int) ((oldX / 100) + 1)][(int) ((oldY / 100) + 1)] = -1;
	    		boardByType[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = 4;
	    		boardByType[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] = 4;
	    		boardByType[(int) newLocation.getX() / 100][((int) newLocation.getY() /100) + 1] = 4;
	    		boardByType[((int) newLocation.getX() / 100) + 1][((int) newLocation.getY() /100) + 1] = 4;
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
    public int movingLogicVerticalRectangle(double x, double y, double oldX, double oldY, int index, KlotskiBlock b) {
     	int i, j;
    	int flag = -1;
    	Point location = new Point((int) x, (int) y);
		Point newLocation = boardPositions[0][0];
		int newLocationX = 0;
		int newLocationY = 0;
		double minDistance = location.distance(newLocation);
    	
		// Find the closest point to the block's drop point.
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			if (location.distance(boardPositions[i][j]) < minDistance &&
    					boardPositions[i][j] != boardPositions[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(boardPositions[i][j]);
    				newLocation = boardPositions[i][j];
    				newLocationX = i;
    				newLocationY = j;
    			}
    		}
    	}
    	
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if ((board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 board[(int) newLocation.getX() / 100][((int) newLocation.getY() /100 + 1)] == index) ||
			(board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == index &&
			 board[(int) newLocation.getX() / 100][((int) newLocation.getY() /100 + 1)] == -1) ||
			(board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 board[(int) newLocation.getX() / 100][((int) newLocation.getY() /100 + 1)] == -1)		 
				) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY, index)) {
				b.setPosition(newLocation);
	    		board[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		board[(int) (oldX / 100)][(int) (oldY / 100) + 1] = -1;
	    		board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = index;
	    		board[(int) newLocation.getX() / 100][((int) newLocation.getY() /100) + 1] = index;
	    		
	    		boardByType[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		boardByType[(int) (oldX / 100)][(int) (oldY / 100) + 1] = -1;
	    		boardByType[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = 2;
	    		boardByType[(int) newLocation.getX() / 100][((int) newLocation.getY() /100) + 1] = 2;
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
    public int movingLogicHorizontalRectangle(double x, double y, double oldX, double oldY, int index, KlotskiBlock b) {
    	int i, j;
    	int flag = -1;
    	Point location = new Point((int) x, (int) y);
		Point newLocation = boardPositions[0][0];
		int newLocationX = 0;
		int newLocationY = 0;
		double minDistance = location.distance(newLocation);
    	
		// Find the closest point to the block's drop point.
    	for(i = 0; i < 4; i++) {
    		for(j = 0; j < 5; j++) {
    			if (location.distance(boardPositions[i][j]) < minDistance &&
    					boardPositions[i][j] != boardPositions[(int) oldX / 100][(int) oldY / 100]) {
    				minDistance = location.distance(boardPositions[i][j]);
    				newLocation = boardPositions[i][j];
    				newLocationX = i;
    				newLocationY = j;
    			}
    		}
    	}
    	
		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
		if ((board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == index) ||
			(board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == index &&
			 board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1) ||
			(board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] == -1 &&
			 board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] == -1)		 
				) {
			//System.out.println("Clear Path: " + clearPathMovingLogic(locationX, locationY, newLocationX, newLocationY));
			if (clearPathMovingLogic((int) (oldX / 100), (int) (oldY / 100), newLocationX, newLocationY, index)) {
				b.setPosition(newLocation);
	    		board[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		board[(int) ((oldX / 100) + 1)][(int) (oldY / 100)] = -1;
	    		board[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = index;
	    		board[((int) newLocation.getX() / 100) + 1][(int) newLocation.getY() /100] = index;
	    		
	    		boardByType[(int) (oldX / 100)][(int) (oldY / 100)] = -1;
	    		boardByType[(int) (oldX / 100) + 1][(int) (oldY / 100)] = -1;
	    		boardByType[(int) newLocation.getX() / 100][(int) newLocation.getY() /100] = 3;
	    		boardByType[(int) newLocation.getX() / 100 + 1][((int) newLocation.getY() /100)] = 3;
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
    public boolean clearPathMovingLogic(int pathX, int pathY, int destinationX, int destinationY, int index) {
    	boolean flag1 = false;
    	boolean flag2 = false;

    	if (pathX == destinationX && pathY == destinationY) 
    		return true;
    	else if (pathX == destinationX && pathY < destinationY) {
    		if (board[pathX][pathY + 1] == -1 || board[pathX][pathY + 1] == index) 
    			return clearPathMovingLogic(pathX, pathY + 1, destinationX, destinationY, index);
    		else 
    			return false;
    	}
    	else if (pathX == destinationX && pathY > destinationY) {
    		if (board[pathX][pathY - 1] == -1 || board[pathX][pathY - 1] == -1) 
    			return clearPathMovingLogic(pathX, pathY - 1, destinationX, destinationY, index);
    		else 
    			return false;
    	}
    	else if (pathX < destinationX && pathY < destinationY) {
    		if (board[pathX][pathY + 1] == -1 || board[pathX][pathY + 1] == index) 
    			flag1 = clearPathMovingLogic(pathX, pathY + 1, destinationX, destinationY, index);
    		else 
    			flag1 = false;
    		
    		if (board[pathX + 1][pathY] == -1 || board[pathX + 1][pathY] == index) 
    			flag2 = clearPathMovingLogic(pathX + 1, pathY, destinationX, destinationY, index);
    		else 
    			flag2 = false;
    		
    		if (flag1 == false && flag2 == false) 
    			return false; 
    		else
    			return true;
    	}
    	else if (pathY == destinationY && pathX < destinationX) {
    		if (board[pathX + 1][pathY] == -1 || board[pathX + 1][pathY] == index) 
    			return clearPathMovingLogic(pathX + 1, pathY, destinationX, destinationY, index);
    		else 
    			return false;
    	}
    	else if (pathY == destinationY && pathX > destinationX) {
    		if (board[pathX - 1][pathY] == -1 || board[pathX - 1][pathY] == index) 
    			return clearPathMovingLogic(pathX - 1, pathY, destinationX, destinationY, index);
    		else 
    			return false;
    	}
    	else if (pathX < destinationX && pathY > destinationY) {
    		if (board[pathX][pathY - 1] == -1 || board[pathX][pathY - 1] == index) 
    			flag1 = clearPathMovingLogic(pathX, pathY - 1, destinationX, destinationY, index);
    		else 
    			flag1 = false;
    		
    		if (board[pathX + 1][pathY] == -1 || board[pathX + 1][pathY] == index) 
    			flag2 = clearPathMovingLogic(pathX + 1, pathY, destinationX, destinationY, index);
    		else 
    			flag2 = false;
    		
    		if (flag1 == false && flag2 == false) 
    			return false; 
    		else
    			return true;
    	}
    	else if (pathX > destinationX && pathY > destinationY) {
    		if (board[pathX][pathY - 1] == -1 || board[pathX][pathY - 1] == index) 
    			flag1 = clearPathMovingLogic(pathX, pathY - 1, destinationX, destinationY, index);
    		else 
    			flag1 = false;
    		
    		if (board[pathX - 1][pathY] == -1 || board[pathX - 1][pathY] == index) 
    			flag2 = clearPathMovingLogic(pathX - 1, pathY, destinationX, destinationY, index);
    		else 
    			flag2 = false;
    		
    		if (flag1 == false && flag2 == false) 
    			return false; 
    		else
    			return true;
    	}
    	else if (pathX > destinationX && pathY < destinationY) {
    		if (board[pathX][pathY + 1] == -1 || board[pathX][pathY + 1] == index) 
    			flag1 = clearPathMovingLogic(pathX, pathY + 1, destinationX, destinationY, index);
    		else 
    			flag1 = false;
    		
    		if (board[pathX - 1][pathY] == -1 || board[pathX - 1][pathY] == index) 
    			flag2 = clearPathMovingLogic(pathX - 1, pathY, destinationX, destinationY, index);
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
    
    public void printCurrentBoard() {
    	int i, j;
    	//System.out.println("\nCurrent Board positions: \n");
    	System.out.println("\n");
    	for(i = 0; i < 5; i++) {
    		System.out.print("		");
    		for (j = 0; j < 4; j++) {
    			if (j == 0) {
    				System.out.print("\n");
    			}
    	    	System.out.print(" " + board[j][i] + " ");
    		}
    	}
    }
    
    public void printCurrentBoardByType() {
    	int i, j;
    	//System.out.println("\nCurrent Board positions: \n");
    	System.out.println("\n");
    	for(i = 0; i < 5; i++) {
    		System.out.print("		");
    		for (j = 0; j < 4; j++) {
    			if (j == 0) {
    				System.out.print("\n");
    			}
    	    	System.out.print(" " + boardByType[j][i] + " ");
    		}
    	}
    }
}
