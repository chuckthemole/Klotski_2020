package klotski;

import java.awt.Point;

public class BlockMove {
	private int block;
	private Point point;
	
	BlockMove(int b, Point p) {
		block = b;
		point = new Point((int) p.getX(), (int) p.getY());
	}
	
	public int getBlock() {
		return block;
	}
	
	public Point getPosition() {
		return point;
	}
}
