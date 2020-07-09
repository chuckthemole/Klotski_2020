package klotski;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import klotski.MoveTree.TreeNode;

import klotski.Graph.GraphNode;

public class Solver {
	private int moves;
	private KlotskiBoard board;
	Graph graph;
	MoveTree tree;
	//private Queue q;

	Solver(KlotskiBoard b) {
		board = new KlotskiBoard();
		for(int i = 0; i < 10; i++) {
			board.setBlockPosition(i, b.getBlock(i).getPosition());
		}
		createTree();
		//createGraph();
	}
	
	public void createTree() {
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		MoveTree t;
		int i, j, l, x, y, loopCount;
		KlotskiBoard tempBoard;
		KlotskiBoard b;
		
		// Set up root of tree
		b = new KlotskiBoard();
		for(i = 0; i < 10; i++) {
			b.getMouse()[i].setPositionOfBlock(board.getBlock(i).getPosition().getX(), board.getBlock(i).getPosition().getY());
			//b.setBlockPosition(i, board.getBlock(i).getPosition());
		}
		t = new MoveTree(b);
		
		// Maybe just tree = t ???
		tree = new MoveTree(b);
		tree.setRoot(t.getRoot());
		
		q.add(t.getRoot());
		
		loopCount = 0;
		while (loopCount != 3) {// || !q.isEmpty() || (t.getRoot().getBoard().getBlocks()[9].getPosition().getX() == 100 && 
				//t.getRoot().getBoard().getBlocks()[9].getPosition().getY() == 300)) {
			//b = t.getRoot().getBoard();
			b = new KlotskiBoard();
			for(i = 0; i < 10; i++) {
				b.getMouse()[i].setPositionOfBlock(t.getRoot().getBoard().getBlock(i).getPosition().getX(), t.getRoot().getBoard().getBlock(i).getPosition().getY());
				//b.setBlockPosition(i, t.getRoot().getBoard().getBlock(i).getPosition());
			}
			tempBoard = new KlotskiBoard();
			for(i= 0; i < 10; i++) {
				tempBoard.getMouse()[i].setPositionOfBlock(b.getBlock(i).getPosition().getX(), b.getBlock(i).getPosition().getY());
				//tempBoard.setBlockPosition(i, b.getBlock(i).getPosition());
			}
			for (i = 0; i < 10; i++) {	
				for (j = 0; j < 4; j++) {
					
					if (j == 0) {
						x = (int) tempBoard.getBlocks()[i].getPosition().getX()  + 100;
						y = (int) tempBoard.getBlocks()[i].getPosition().getY();
					}
					else if (j == 1) {
						x = (int) tempBoard.getBlocks()[i].getPosition().getX()  - 100;
						y = (int) tempBoard.getBlocks()[i].getPosition().getY();
					}
					else if (j == 2) {
						x = (int) tempBoard.getBlocks()[i].getPosition().getX();
						y = (int) tempBoard.getBlocks()[i].getPosition().getY() + 100;
					}
					else {
						x = (int) tempBoard.getBlocks()[i].getPosition().getX();
						y = (int) tempBoard.getBlocks()[i].getPosition().getY() - 100;
					}
					
					try {
						Point p = new Point(x, y);
						if (isLegalMove(tempBoard.getBlock(i), p, tempBoard)) {
							tempBoard.setBlockPosition(i, p);
							tempBoard.getMouse()[i].printCurrentBoard();
							t.getRoot().addBranch(p, i, tempBoard);
							q.add(t.getRoot().getChildren().getLast());
						
							// Reset tempBoard
							tempBoard = new KlotskiBoard();
							for(l = 0; l < 10; l++) {
								tempBoard.getMouse()[i].setPositionOfBlock(b.getBlock(i).getPosition().getX(), b.getBlock(i).getPosition().getY());
								//tempBoard.setBlockPosition(i, b.getBlock(i).getPosition());
							}
						}
					}
					catch (Exception e) {
						System.out.println("Out of bounds!" + e);
					}
					
				}
			}
			System.out.println("Size of Queue: " + q.size());
			loopCount++;
			System.out.println("Loop Count: " + loopCount);
			t.setRoot(q.remove());
			//System.out.println("Big block coordinates: (" + t.getRoot().getBoard().getBlocks()[9].getPosition().getX() + ", " +
			//		t.getRoot().getBoard().getBlocks()[9].getPosition().getY()+ ")");
			System.out.println("Block[0] coordinates: (" + t.getRoot().getBoard().getBlock(9).getPosition().getX() + ", " +
					t.getRoot().getBoard().getBlock(9).getPosition().getY() + ")");
		}
		System.out.println("Finished creating tree...");	
	}
	
	public void createGraph() {
		LinkedList<KlotskiBlock> list = null;
		KlotskiBoard temp;
		int i, j;
		Graph g = null;
		Queue<GraphNode> q = new LinkedList<GraphNode>();
		
		// Set up root of graph
		g = new Graph(null, board);	
		graph = g;
		
		//try {
			System.out.println("1");
			q.add(g.getRoot());
			System.out.println("2");
			// Add to the graph until the queue is empty or big block is in final position.
			while(!q.isEmpty() || 
					g.getRoot().getBoard().getBlocks()[9].getPosition().getX() == 100 && 
					g.getRoot().getBoard().getBlocks()[9].getPosition().getY() == 300) {
				System.out.println("3");

				// Get movable positions for each block
				for (i = 0; i < 10; i++) {	
					list = getMovablePositions(g.getRoot().getBoard().getBlocks()[i], g.getRoot().getBoard()); // Positions for the ith block
					System.out.println("4");
					for (j = 0; j < list.size(); j++) {
						temp = g.getRoot().getBoard();
						temp.setBlockPosition(list.get(j).getIndex(), list.get(j).getPosition());
						g.getRoot().addBranch(list.get(j), temp);
						q.add(g.getRoot().getEdges().getLast()); // need to find index!
					}
					System.out.println("5");
				}
				g.setRoot(q.remove());
				System.out.println("6");
			//}
			System.out.println("************************Graph Completed*****************************");
		}
		//catch (Exception e) {
		//	System.out.println("Exception in createGraph(): " + e);
		//}
	}
	
	public LinkedList<KlotskiBlock> getMovablePositions(KlotskiBlock b, KlotskiBoard kb) {
		int i;
		Point p = null;
		LinkedList<KlotskiBlock> list = null;
		KlotskiBlock blk = new KlotskiBlock(b.getType(), b.getPosition(), b.getIndex());
		
		System.out.println("In getMovablePositions()...");
		
		for(i = 0; i < 4; i++) {			
			if (i == 0) {
				if(b.getPosition().getX() < 300) {
					p = new Point((int) b.getPosition().getX() + 100, (int) b.getPosition().getY()); 
				}
			}
			else if (i == 1) {
				if (b.getPosition().getY() < 400) {
					p = new Point((int) b.getPosition().getX(), (int) b.getPosition().getY() - 100); 
				}
			}
			else if (i == 2) {
				if (b.getPosition().getX() > 0) {
					p = new Point((int) b.getPosition().getX() - 100, (int) b.getPosition().getY()); 
				}
			}
			else {
				if (b.getPosition().getY() > 0) {
					p = new Point((int) b.getPosition().getX(), (int) b.getPosition().getY() + 100); 
				}
			}
			
			if (p != null) {
				if (isLegalMove(b, p, kb)) {
					if (list == null) {
						list = new LinkedList<KlotskiBlock>();
					}
					System.out.println("     TestTestTest");
					blk = new KlotskiBlock(b.getType(), b.getPosition(), b.getIndex());
					blk.setPosition(p);
					list.add(blk);
					blk = null;
					
				}
			}
			System.out.println("        " + i);
		}
		System.out.println("Leaving getMovablePositions()...");

		return list;
	}
	
	public boolean isLegalMove(KlotskiBlock kblock, Point moveHere, KlotskiBoard currentBoard) {
    	boolean flag1 = false;
    	boolean flag2 = false;
    	
    	try {
			// Check to make sure new location is empty and there is a clear path to that space.
	    	if (kblock.getType() == "smallSquare") {
	    		if (currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1) {
	    			flag1 = true;
	    		}
	    	}
	    	else if (kblock.getType() == "verticalRectangle") {
	    		if ((currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
	    				 currentBoard.getBoard()[(int) moveHere.getX() / 100][((int) moveHere.getY() /100 + 1)] == kblock.getIndex()) ||
	    				(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == kblock.getIndex() &&
	    				 currentBoard.getBoard()[(int) moveHere.getX() / 100][((int) moveHere.getY() /100 + 1)] == -1) ||
	    				(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
	    				 currentBoard.getBoard()[(int) moveHere.getX() / 100][((int) moveHere.getY() /100 + 1)] == -1)		 
	    					) {
	    			flag1 = true;
	    		}
	    	}
	    	else if (kblock.getType() == "horizontalRectangle") {
	    		// Check to make sure new location is empty and there is a clear path to that space, if so update board.
	    		if ((currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == kblock.getIndex()) ||
	    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == kblock.getIndex() &&
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
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == kblock.getIndex() &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == -1 &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == kblock.getIndex()) ||
	    				
	    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == kblock.getIndex() &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == -1 &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == kblock.getIndex() &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == -1) ||
	    			
	    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == -1 &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == -1 &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == kblock.getIndex() &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == kblock.getIndex()) ||
	    			
	    			(currentBoard.getBoard()[(int) moveHere.getX() / 100][(int) moveHere.getY() /100] == kblock.getIndex() &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100)][((int) moveHere.getY() /100) + 1] == kblock.getIndex() &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][(int) moveHere.getY() /100] == -1 &&
	    			 currentBoard.getBoard()[((int) moveHere.getX() / 100) + 1][((int) moveHere.getY() /100) + 1] == -1)
	    				) {
	    	    		flag1 = true;
	    		}
	    	}
	    	else
	    		System.out.println("No Type...");
    	}
    	catch (Exception ex) {
    		System.out.println("Exception" + ex);
    	}
    	
		if (flag1 == true) {
			return true;
		}
		else {
			return false;
		}
    }
	
	/**
	 * BFS algorithm
	 * @return
	 */
	public Stack solve() {
		KlotskiBoard b = board;
		Queue<KlotskiBoard> q = null;
		KlotskiBoard temp = null;
		Stack s = new Stack();
		
		q.add(b);
		
		while (!q.isEmpty()) {
			temp = q.remove();
			if (temp.getBlocks()[9].getPosition().getX() == 100 && temp.getBlocks()[9].getPosition().getY() == 300) {
				
			}
		}
		/*
		 * Need to finish!
		 */
		
		return s;
	}
}
