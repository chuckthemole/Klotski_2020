package klotski;
/**
 * Program name:    RectangleCharlesT.java
 * Discussion:      Final
 * Written by:      Charles T
 * Due Date:        
 */

import javafx.scene.shape.Rectangle;

/*
RectangleCharlesT is a class that defines a rectangle
with length width and an upper left coordinate.
*/

class RectangleCharlesT {
    private Rectangle rec;
    private FractionCharlesT len;
    private FractionCharlesT wid;
    private PointCharlesT upLeft;  
    
    // Default Constructor
    RectangleCharlesT() {
        len = new FractionCharlesT(1);
        wid = new FractionCharlesT(1);
        rec = new Rectangle();
    }
   
    /**
     * Constructor
     * 
     * @param l - length
     * @param w - width
     */
    public RectangleCharlesT(int l, int w) {
        l = (l < 0) ? -l : l;
        w = (w < 0) ? -w : w;
        len = new FractionCharlesT(l);
        wid = new FractionCharlesT(w);
    }
    
    /**
     * Constructor
     * 
     * @param l - length
     * @param w - width
     * @param upperLeft - upper left point of rectangle
     */
    public RectangleCharlesT(FractionCharlesT l, FractionCharlesT w, PointCharlesT upperLeft) {
        int i;
        
        i = (l.getNum() < 0) ? -l.getNum() : l.getNum();
        l.setNum(i);
        i = (w.getNum() < 0) ? -w.getNum() : w.getNum();
        w.setNum(i);
        len = l;
        wid = w;
        upLeft = upperLeft;
    }
    
    /**
     * Constructor
     * 
     * @param x - upper left x coordinate
     * @param y - upper left y coordinate
     * @param l - length
     * @param w - width
     */
    public RectangleCharlesT(int x, int y, int l, int w) {
        l = (l < 0) ? -l : l;
        w = (w < 0) ? -w : w;
        len = new FractionCharlesT(l);
        wid = new FractionCharlesT(w);
        upLeft = new PointCharlesT(x, y);
        rec = new Rectangle(x, y, l, w);
    }
    
    /**
     * Constructor
     * 
     * @param r - RectangleCharlesT
     */
    public RectangleCharlesT(RectangleCharlesT r) {
        len = new FractionCharlesT(r.len);
        wid = new FractionCharlesT(r.wid);
        upLeft = new PointCharlesT(r.upLeft);
    }
      
    //Member Methods
    public void print(RectangleCharlesT r) {
        System.out.print(r);
    }

    @Override
    public String toString() {
        return "\n    length \n" + len + "\n    width \n" + wid + 
                "\n    lower left \n" + upLeft;
    }

    public FractionCharlesT computeArea() {
        return new FractionCharlesT(len.multiply(wid));
    }
    
    public FractionCharlesT computePerimeter() {
        return new FractionCharlesT(len.multiply(2)
                .add(wid.multiply(2)));
    }
    
    public FractionCharlesT getLen() {
        return new FractionCharlesT(len);
    }
    
    public FractionCharlesT getWid() {
        return new FractionCharlesT(wid);
    }
    
    public PointCharlesT getUpLeft() {
        return new PointCharlesT(upLeft);
    }
    
    public Rectangle getRec() {
        return rec;
    }
    
    public void setLen(FractionCharlesT l) {
        int i;
        
        i = (l.getNum() < 0) ? -l.getNum() : l.getNum();
        l.setNum(i);
        len = l;
    }
    
    public void setWid(FractionCharlesT w) {
        int i;
        
        i = (w.getNum() < 0) ? -w.getNum() : w.getNum();
        w.setNum(i);
        len = w;
    }
    
    public void setUpLeft(PointCharlesT p) {
        upLeft = p;
        rec.setX(p.getX().getNum() / p.getY().getDenom());
        rec.setY(p.getY().getNum() / p.getY().getDenom());
    }
    
    public void setUpLeft(int x, int y) {
    	PointCharlesT p = new PointCharlesT(x, y);
        upLeft = p;
        rec.setX(x);
        rec.setY(y);
    }
}