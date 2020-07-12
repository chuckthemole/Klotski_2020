package klotski;

import java.util.LinkedList;

public class Graph {
	private GraphNode root = null;
	
	Graph(KlotskiBoard b) {
		root = new GraphNode(b);
	}
	
	public GraphNode getRoot() {
		return root;
	}
	
	public void setRoot(GraphNode n) {
		root = n;
	}
	
	public class GraphNode {
		KlotskiBoard board;
		LinkedList<GraphNode> children = null;
		
		GraphNode(KlotskiBoard b) {
			children = new LinkedList<GraphNode>();
			board = b;
		}
		
		public void addChild(KlotskiBoard b) {
			GraphNode n = new GraphNode(b);
			children.add(n);
		}
		
		public void setBoard(KlotskiBoard b) {
			board = b.copy();
		}

		public KlotskiBoard getBoard() {
			return board;
		}
		
		public LinkedList<GraphNode> getChildren() {
			return children;
		}
	}
}