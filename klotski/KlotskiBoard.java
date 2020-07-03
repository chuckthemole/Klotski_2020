package klotski;
/**
 * Program name:    KlotskiBoard.java
 * Discussion:      Final
 * Written by:      Charles T
 * Due Date:        
 */

import java.awt.Point;
import java.util.Stack;
import javafx.geometry.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import klotski.MouseDrag;

/*
KlotskiBoard sets the klotski blocks up on the board
in their initial positions.
*/

public class KlotskiBoard extends Klotski {           
    private Pane pane = new Pane();
    private KlotskiButtons buttons = new KlotskiButtons();
    private MouseDrag[] mouse = new MouseDrag[10]; 
    private static KlotskiBlock[] blocks;
    private int[][] board;
    final private Point [][] boardPositions;

    KlotskiBoard() {
        blocks = new KlotskiBlock[10];
        board = new int[4][5];    
        boardPositions = new Point[4][5];
        int i;
  
        Pane p = new Pane();
        p.setPrefSize(400,100);
        p.relocate(0, 500);
        p.setBackground(new Background(new BackgroundFill(
                Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
        p.getChildren().add(buttons.getHBox());
        buttons.getHBox().relocate(60, 15);
        pane.getChildren().add(p);
        
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
        
        /**
         * Create visible, movable blocks for the board
         */
        //one by one blocks
        blocks[0] = new KlotskiBlock("smallSquare", 300, 400, 0);  
        pane.getChildren().add(blocks[0].getRec());
        blocks[1] = new KlotskiBlock("smallSquare", 100, 300, 1);
        pane.getChildren().add(blocks[1].getRec());
        blocks[2] = new KlotskiBlock("smallSquare", 200, 300, 2);
        pane.getChildren().add(blocks[2].getRec());
        blocks[3] = new KlotskiBlock("smallSquare", 0, 400, 3);
        pane.getChildren().add(blocks[3].getRec());
        
        //one by two blocks 
        blocks[4] = new KlotskiBlock("verticalRectangle", 0, 0, 4);          
        pane.getChildren().add(blocks[4].getRec());
        blocks[5] = new KlotskiBlock("verticalRectangle", 0, 200, 5);         
        pane.getChildren().add(blocks[5].getRec());   
        blocks[6] = new KlotskiBlock("verticalRectangle", 300, 0, 6);       
        pane.getChildren().add(blocks[6].getRec());
        blocks[7] = new KlotskiBlock("verticalRectangle", 300, 200, 7);  
        pane.getChildren().add(blocks[7].getRec());
        
        //two by one block 
        blocks[8] = new KlotskiBlock("horizontalRectangle", 100, 200, 8); 
        pane.getChildren().add(blocks[8].getRec());
        
        //two by two block        
        blocks[9] = new KlotskiBlock("bigSquare", 100, 0, 9);         
        pane.getChildren().add(blocks[9].getRec());
        
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
        for (i = 9; i >= 0; i--) {
            mouse[i] = new MouseDrag(blocks[i], this); 
        }     
    }
    
    public Pane getPane() {
        return pane;
    }
    
    public KlotskiButtons getButtons() {
        return buttons;
    }
    
    public MouseDrag[] getMouse() {
        return mouse;
    }
    
    public KlotskiBlock[] getBlocks() {
        return blocks;
    }
    
    public int[][] getBoard() {
        return board;
    }
    
    public Point[][] getBoardPositions() {
    	return boardPositions;
    }
    
    public KlotskiBlock getBlock(int blockIndex) {
    	return mouse[blockIndex].getCurrentBlock();
    }
    
    public void setBoard(int[][] b) {
    	board = b;
    	mouse[0].printCurrentBoard();
    }
    
    public void setBlockPosition(int blockIndex, Point p) {
    	mouse[blockIndex].setCurrentBlockPosition(p);
    }
    

}
