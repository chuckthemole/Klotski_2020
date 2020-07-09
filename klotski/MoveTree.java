package klotski;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

public class MoveTree {
private TreeNode root = null;
	
	MoveTree(KlotskiBoard board) {
		root = new TreeNode(null, -1, board, null);
	}
	
	public TreeNode getRoot() {
		return root;
	}
	
	public void setRoot(TreeNode n) {
		root = n;
	}
	
	public void printTree() {
		Queue<TreeNode> q;
		
	}
	
	public class TreeNode {
		Point point;
		int blockIndex;
		KlotskiBoard board;
		TreeNode parent;
		LinkedList<TreeNode> children = null;
		
		TreeNode(Point p, int b, KlotskiBoard brd, TreeNode prt) {
			int i;
			
			point = p;
			blockIndex = b;
			
			board = new KlotskiBoard();
			for(i = 0; i < 10; i++) {
				board.setBlockPosition(i, brd.getBlock(i).getPosition());
			}
			
			parent = prt;
			children = new LinkedList<TreeNode>();
		}
		
		public void addBranch(Point p, int b, KlotskiBoard brd) {
			TreeNode n = new TreeNode(p, b, brd, this);
			if(children == null) {
				children = new LinkedList<TreeNode>();
			}
			children.add(n);
		}
		
		public void setChildren(LinkedList<TreeNode> list) {
			children = list;
		}
		
		public void setBoard(KlotskiBoard b) {
			board = b;
		}
		
		public void setIndex(int i) {
			blockIndex = i;
		}
		
		public void setParent(TreeNode n) {
			parent = n;
		}
		
		public TreeNode getParent() {
			return parent;
		}
		
		public KlotskiBoard getBoard() {
			return board;
		}
		
		public LinkedList<TreeNode> getChildren() {
			return children;
		}
	}
}
