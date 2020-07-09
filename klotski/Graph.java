package klotski;

import java.util.LinkedList;

public class Graph {
	private GraphNode root = null;
	
	Graph(KlotskiBlock x, KlotskiBoard b) {
		root = new GraphNode(x, b, null);
	}
	
	public GraphNode getRoot() {
		return root;
	}
	
	public void setRoot(GraphNode n) {
		root = n;
	}
	
	public class GraphNode {
		KlotskiBlock value;
		KlotskiBoard board;
		GraphNode parent;
		LinkedList<GraphNode> pointers = null;
		
		GraphNode(KlotskiBlock x, KlotskiBoard b, GraphNode n) {
			value = x;
			pointers = new LinkedList<GraphNode>();
			board = b;
			parent = n;
		}
		
		public void addBranch(KlotskiBlock node, KlotskiBoard b) {
			GraphNode n = new GraphNode(node, b, this);
			pointers.add(n);
		}
		
		public void setPointers(LinkedList<GraphNode> list) {
			pointers = list;
		}
		
		public void setBoard(KlotskiBoard b) {
			board = b;
		}
		
		public void setValue(KlotskiBlock t) {
			value = t;
		}
		
		public void setParent(GraphNode n) {
			parent = n;
		}
		
		public GraphNode getParent() {
			return parent;
		}
		
		public KlotskiBlock getValue() {
			return value;
		}
		
		public KlotskiBoard getBoard() {
			return board;
		}
		
		public LinkedList<GraphNode> getEdges() {
			return pointers;
		}
	}
}