package klotski;

import java.awt.Point;

public class Block {
	private Point position;
	private int blockIndex;
	private int[][] boardPositions;
	
	Block(Point p, int index, int[][] board) {
		int i, j;
		
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
	
	public int[][] getBoardPositions() {
		return boardPositions;
	}
}
