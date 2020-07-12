package klotski;

import java.awt.Point;
import java.util.Stack;
import javafx.scene.text.Text;

public class UndoStack {
	private Stack<Block> stack = null;
	private int stackSize;
	private Block block;
	private Text changeText;
	
	UndoStack() {
		stack = new Stack<Block>();
		stackSize = 0;
        changeText = new Text("Number of moves: " + stackSize);
	}
	
	UndoStack(Point p, Point newPoint, int index, int[][] board) {
		if (stack == null) {
			stack = new Stack<Block>();
			stackSize = 0;
		}
		block = new Block(p, newPoint, index, board);
		stack.push(block);
		stackSize++;
        changeText = new Text("Number of moves: " + stackSize);
	}
	
	public class Block {
		private Point newPosition;
		private Point position;
		private int blockIndex;
		private int[][] boardPositions;
		
		Block(Point p, Point newPoint, int index, int[][] board) {
			int i, j;
			
			newPosition = newPoint;
			position = p;
			blockIndex = index;
			boardPositions = new int[board.length][board[0].length];

			for (i = 0; i < board.length; i++) {
				for (j = 0; j < board[0].length; j++) {
					boardPositions[i][j] = board[i][j];
				}
			}
		}
		
		public int getIndex() {
			return blockIndex;
		}
		
		public Point getPosition() {
			return position;
		}
		
		public Point getNewPosition() {
			return newPosition;
		}
		
		public int[][] getBoardPositions() {
			return boardPositions;
		}
	}
	
    public void printUndoStack() {
    	System.out.println("\n\nUndo stack size of " + stackSize +": ");
		for (int i = 0; i < stack.size(); i++) {
			System.out.println("  " + stack.elementAt(i).getIndex());
		}
    }
	
	public int getIndex() {
		return this.getIndex();
	}
	
	public Point getPosition() {
		return this.getPosition();
	}
	
	public int[][] getBoardPositions() {
		return this.getBoardPositions();
	}
	
	public int getStackSize() {
		return stackSize;
	}
	
	public Text getStackSizeAsText() {
		return changeText;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Block popUndoStack() {
		if (stackSize > 0)
			stackSize--;
        changeText = new Text("Number of moves: " + stackSize);
		return stack.pop();
	}
	
	public Block peekUndoStack() {
		return stack.peek();
	}
	
	public void pushUndoStack(Block b) {
		stackSize++;
		stack.push(b);
        changeText = new Text("Number of moves: " + stackSize);
	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public void pushUndoStack(Point point, Point newPoint, int index, int[][] b) {
		int i, j;
		int [][] board;
		
    	board = new int[4][5];
    	for (i = 0; i < 4; i++) {
    		for (j = 0; j < 5; j++) {
    			board[i][j] = b[i][j];
    		}
    	}
		
		Block blk = new Block(point, newPoint, index, board);
		stackSize++;
		stack.push(blk);
        changeText = new Text("Number of moves: " + stackSize);
	}
}
