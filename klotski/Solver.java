package klotski;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class Solver {
	private KlotskiBoard mainBoard;
	private static KlotskiBoardList boardList;
	private static Queue<KlotskiBoard> queue; 
	private KlotskiBoard[] pathBoard;
	private BlockMove[] b = new BlockMove[118];
	private int index;

	Solver(KlotskiBoard bk) {
		boardList = new KlotskiBoardList();
		mainBoard = bk.copy();
		pathFinder();
	}
	
	public void pathFinder() {
		boolean stop = false;
		int i, j;
		KlotskiBoard bk;
		queue = new LinkedList<KlotskiBoard>();
		int flag;
		Point p;
		queue.add(mainBoard.copy());
		boardList.insert(mainBoard);	
		
		setUpPathArray();
		
		while (!stop) {
			mainBoard = (queue.poll()).copy();		
			for (i = 0; i < pathBoard.length; i++) {
				System.out.println(i + "\n");
				if (mainBoard.equals(pathBoard[i])) { // check mainBoard against pathBoard****************Need to fix this part				
					System.out.println("Break 1");
					index = i;
					i = pathBoard.length;
					stop = true;
					System.out.print("********************************Index = " + index + "*************************");
				}
			}
	
			if (mainBoard.getBlocks()[9].getPosition().getX() == 100 && mainBoard.getBlocks()[9].getPosition().getY() == 300) {
				// To Do's
				System.out.println("Break 2");
				break;
			}
				
			for (i = 0; i < 10; i++) {
				for (j = 0; j < 4; j++) {
					bk = mainBoard.copy();
					
					if (j == 1) {
						p = new Point((int) bk.getBlocks()[i].getPosition().getX() + 100, (int) bk.getBlocks()[i].getPosition().getY());
					}
					else if (j == 2) {
						p = new Point((int) bk.getBlocks()[i].getPosition().getX() - 100, (int) bk.getBlocks()[i].getPosition().getY());
					}
					else if (j == 3) {
						p = new Point((int) bk.getBlocks()[i].getPosition().getX(), (int) bk.getBlocks()[i].getPosition().getY() - 100);
					}
					else {
						p = new Point((int) bk.getBlocks()[i].getPosition().getX(), (int) bk.getBlocks()[i].getPosition().getY() + 100);
					}
					
					try {
						flag = bk.setBlockPosition(bk.getBlocks()[i], p);
					}
					catch (Exception e) {
						System.out.println(e);
						flag = -1;
					}				
					
					if (!boardList.contains(bk)) {
						if (flag != -1) { 
							if (i == 9) {
								queue.add(bk.copy());	
								for (int l = 0; l < queue.size() - 1; l++) {						
									queue.add(queue.remove());
								}
							}
							else
								queue.add(bk.copy());
							
							boardList.insert(bk);
							System.out.println("\nPrevious Board: ");
							mainBoard.printCurrentBoard();
						}
					}
				}			
			}
		}
		System.out.println("\n\n\nFinished creating graph...");	
	}
	
	public int getIndex() {
		return index;
	}
	
	public KlotskiBoard[] getpathBoard() {
		return pathBoard;
	}
	
	public BlockMove[] getBlockMoveArray() {
		return b;
	}
	
	public void createGraph() {
		int i, j;
		double bigBlockPosition = 0;
		KlotskiBoard b;
		Queue<KlotskiBoard> q = new LinkedList<KlotskiBoard>();
		int flag;
		Point p;
		//KlotskiBoardList boardList = new KlotskiBoardList();
		boolean bigBlockMovedDown = false;
		q.add(mainBoard.copy());
		boardList.insert(mainBoard);
		
		
		// while queue is not empty or 
		while (!q.isEmpty()) {
			mainBoard = (q.poll()).copy();		
			if (mainBoard.getBlocks()[9].getPosition() == mainBoard.getBoardPositions()[1][3]) {
				// To Do's
				System.out.println("Break 1");
				break;
			}	
			if (mainBoard.getBlocks()[9].getPosition().getX() == 100 && mainBoard.getBlocks()[9].getPosition().getY() == 300) {
				// To Do's
				System.out.println("Break 2");
				break;
			}
		
			for (i = 0; i < 10; i++) {
				for (j = 0; j < 4; j++) {
					b = mainBoard.copy();
					bigBlockMovedDown = false;
					
					if (j == 1) {
						p = new Point((int) b.getBlocks()[i].getPosition().getX() + 100, (int) b.getBlocks()[i].getPosition().getY());
					}
					else if (j == 2) {
						p = new Point((int) b.getBlocks()[i].getPosition().getX() - 100, (int) b.getBlocks()[i].getPosition().getY());
					}
					else if (j == 3) {
						p = new Point((int) b.getBlocks()[i].getPosition().getX(), (int) b.getBlocks()[i].getPosition().getY() - 100);
					}
					else {
						p = new Point((int) b.getBlocks()[i].getPosition().getX(), (int) b.getBlocks()[i].getPosition().getY() + 100);
						if (i == 9) {
							bigBlockMovedDown = true;
							bigBlockPosition += 100;
						}
					}
					
					try {
						flag = b.setBlockPosition(b.getBlocks()[i], p);
					}
					catch (Exception e) {
						System.out.println(e);
						flag = -1;
					}				
					
					if (!boardList.contains(b)) {
						if (flag != -1) { 
							if (i == 9) {
								q.add(b.copy());	
								for (int l = 0; l < q.size() - 1; l++) {						
									q.add(q.remove());
								}
							}
							else
								q.add(b.copy());
							
							boardList.insert(b);
							System.out.println("\nPrevious Board: ");
							mainBoard.printCurrentBoard();
						}
					}
				}			
			}
		}
		System.out.println("\n\n\nFinished creating graph...");		 
	}
	
	public KlotskiBoard getBoard() {
		return mainBoard;
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
	
	public void setUpPathArray() {
		b[0] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 400));
    	b[1] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[2] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 300));
    	b[3] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 200));
    	b[4] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 200));
    	b[5] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 300));
    	b[6] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(0, 400));
    	b[7] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 300));
    	b[8] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(100, 200));
    	b[9] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(0, 200));
    	b[10] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[11] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 300));
    	b[12] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(300, 200));
    	b[13] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 200));
    	b[14] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(200, 300));
    	b[15] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[16] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 400));
    	b[17] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(0, 300));
    	b[18] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 200));
    	b[19] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 200));
    	b[20] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[21] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 200));
    	b[22] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(200, 200));
    	b[23] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 400));
    	b[24] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 400));
    	b[25] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 200));
    	b[26] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(300, 400));
    	b[27] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 400));
    	b[28] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(0, 400));
    	b[29] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 300));
    	b[30] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(0, 200));
    	b[31] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 200));
    	b[32] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(200, 200));
    	b[33] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 100));
    	b[34] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 200));
    	b[35] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(200, 0));
    	b[36] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 0));
    	b[37] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(0, 100));
    	b[38] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 200));
    	b[39] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(0, 0));
    	b[40] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 100));
    	b[41] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 200));
    	b[42] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 100));
    	b[43] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 200));
    	b[44] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 0));
    	b[45] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 100));
    	b[46] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 0));
    	b[47] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 200));
    	b[48] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 300));
    	b[49] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 400));
    	b[50] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 200));
    	b[51] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 300));
    	b[52] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(100, 400));
    	b[53] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 300));
    	b[54] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 400));
    	b[55] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(100, 300));
    	b[56] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 200));
    	b[57] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 200));
    	b[58] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 100));
    	b[59] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 0));
    	b[60] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(0, 0));
    	b[61] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 0));
    	b[62] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 0));
    	b[63] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 100));
    	b[64] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 0));
    	b[65] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 200));
    	b[66] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 100));
    	b[67] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 300));
    	b[68] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 300));
    	b[69] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[70] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 200));
    	b[71] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 100));
    	b[72] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 0));
    	b[73] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(200, 0));
    	b[74] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 100));
    	b[75] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 0));
    	b[76] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(200, 200));
    	b[77] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 200));
    	b[78] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 300));
    	b[79] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 100));
    	b[80] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 0));
    	b[81] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(0, 0));
    	b[82] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 200));
    	b[83] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(0, 400));
    	b[84] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(100, 400));
    	b[85] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 200));
    	b[86] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 100));
    	b[87] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 200));
    	b[88] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(300, 0));
    	b[89] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 100));
    	b[90] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 0));
    	b[91] = new BlockMove(mainBoard.getBlocks()[5].getIndex(), new Point(100, 0));
    	b[92] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 100));
    	b[93] = new BlockMove(mainBoard.getBlocks()[4].getIndex(), new Point(0, 0));
    	b[94] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(0, 200));
    	b[95] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[96] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 300));
    	b[97] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 100));
    	b[98] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(200, 200));
    	b[99] = new BlockMove(mainBoard.getBlocks()[6].getIndex(), new Point(200, 0));
    	b[100] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 100));
    	b[101] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(300, 300));
    	b[102] = new BlockMove(mainBoard.getBlocks()[7].getIndex(), new Point(300, 0));
    	b[103] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(300, 200));
    	b[104] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 300));
    	b[105] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(200, 400));
    	b[106] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(300, 400));
    	b[107] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(100, 400));
    	b[108] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(200, 400));
    	b[109] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(0, 300));
    	b[110] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(100, 200));
    	b[111] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(200, 200));
    	b[112] = new BlockMove(mainBoard.getBlocks()[3].getIndex(), new Point(0, 200));
    	b[113] = new BlockMove(mainBoard.getBlocks()[2].getIndex(), new Point(100, 200));
    	b[114] = new BlockMove(mainBoard.getBlocks()[8].getIndex(), new Point(200, 200));
    	b[115] = new BlockMove(mainBoard.getBlocks()[0].getIndex(), new Point(300, 300));
    	b[116] = new BlockMove(mainBoard.getBlocks()[1].getIndex(), new Point(300, 400));
    	b[117] = new BlockMove(mainBoard.getBlocks()[9].getIndex(), new Point(100, 300));
		
    	pathBoard = new KlotskiBoard[118];
    	int i;
		pathBoard[0] = new KlotskiBoard();
    	pathBoard[0].setBlockPosition(pathBoard[0].getBlocks()[b[0].getBlock()], b[0].getPosition());
    	for (i = 1; i < b.length; i++) {
    		pathBoard[i] = pathBoard[i - 1].copy();
        	pathBoard[i].setBlockPosition(pathBoard[i].getBlocks()[b[i].getBlock()], b[i].getPosition());	
        	pathBoard[i].printCurrentBoard();
    	}   	
	}
}
