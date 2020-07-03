package klotski;

import java.awt.Point;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * KlotskiBlock class creates a rectangle block with a type and position.
 * 
 * @author chuck
 *
 */
public class KlotskiBlock {
	private Rectangle block;
	private String blockType;
	private Point position;
	private int index;
	
	KlotskiBlock() {	
	}
	
	/**
	 * 
	 * @param type is the block type
	 * @param p is the blocks upper left point
	 * @param index is the unique identifier of the block
	 */
	KlotskiBlock(String type, Point p, int i) {
		blockType = type;
		position = p;
		index = i;
		
		if (type == "smallSquare") {
			block = new Rectangle((int) p.getX(), (int) p.getY(), 100, 100);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.BLUE);
		}
		else if (type == "bigSquare") {
			block = new Rectangle((int) p.getX(), (int) p.getY(), 200, 200);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.DARKRED);
		}
		else if (type == "verticalRectangle") {
			block = new Rectangle((int) p.getX(), (int) p.getY(), 100, 200);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.RED);
		}
		else if (type == "horizontalRectangle") {
			block = new Rectangle((int) p.getX(), (int) p.getY(), 200, 100);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.RED);
		}
		else if (type == "backgroundBlock") {
			block = new Rectangle((int) p.getX(), (int) p.getY(), 100, 100);
		    block.setStroke(Color.TRANSPARENT);
		    block.setFill(Color.TRANSPARENT);
		}
		else
			System.out.println("No such block of type: " + type);
	}
	
	/**
	 * 
	 * @param type is the block type
	 * @param x is the blocks upper left x-coordinate
	 * @param x is the blocks upper left y-coordinate
	 * @param i is the unique identifier of the block
	 */
	KlotskiBlock(String type, int x, int y, int i) {
		blockType = type;
		position = new Point(x, y);
		index = i;
		
		if (type == "smallSquare") {
			block = new Rectangle(x, y, 100, 100);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.BLUE);
		}
		else if (type == "bigSquare") {
			block = new Rectangle(x, y, 200, 200);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.DARKGREEN);
		}
		else if (type == "verticalRectangle") {
			block = new Rectangle(x, y, 100, 200);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.RED);
		}
		else if (type == "horizontalRectangle") {
			block = new Rectangle(x, y, 200, 100);
		    block.setStroke(Color.BLACK);
		    block.setFill(Color.RED);
		}
		else if (type == "backgroundBlock") {
			block = new Rectangle(x, y, 100, 100);
		    block.setStroke(Color.TRANSPARENT);
		    block.setFill(Color.TRANSPARENT);
		}
		else
			System.out.println("No such block of type: " + type);
	}
	
	public int getIndex() {
		return index;
	}
	
	public Rectangle getRec() {
		return block;
	}
	
	public String getType() {
		return blockType;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point p) {
		position = p;
		this.getRec().setX(p.getX());
		this.getRec().setY(p.getY());
	}
	
	public void setPosition(int x, int y) {
		position = new Point(x, y);
		this.getRec().setX(x);
		this.getRec().setY(y);
	}
}
