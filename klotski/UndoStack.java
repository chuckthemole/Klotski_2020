package klotski;

import java.awt.Point;
import java.util.Stack;
import javafx.scene.text.Text;

public class UndoStack {
	private static Stack<Block> stack = null;
	private static int stackSize;
	private Block block;
	private Text changeText;
	
	UndoStack() {
		stack = new Stack<Block>();
		stackSize = 0;
        changeText = new Text("Number of moves: " + stackSize);
	}
	
	UndoStack(Point p, int index, int[][] board) {
		if (stack == null) {
			stack = new Stack<Block>();
			stackSize = 0;
		}
		block = new Block(p, index, board);
		stack.push(block);
		stackSize++;
        changeText = new Text("Number of moves: " + stackSize);
	}
	
    public static void printUndoStack() {
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
	
	public void pushUndoStack(Block b) {
		stackSize++;
		stack.push(b);
        changeText = new Text("Number of moves: " + stackSize);
	}
}
