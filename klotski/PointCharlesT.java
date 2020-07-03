package klotski;
/**
 * Program name:    PointCharlesT.java
 * Discussion:      Final
 * Written by:      Charles T
 * Due Date:        
 */

/*
PointCharlesT is a class point with corrdinates (fx, fy)
where fx and fy are members of the class FractionCharlesT
*/

class PointCharlesT {
    private FractionCharlesT fx;
    private FractionCharlesT fy;
    
    //Constructors
    public PointCharlesT() {
        fx = new FractionCharlesT();
        fy = new FractionCharlesT();
    }
    
    public PointCharlesT(int nx, int dx, int ny, int dy) {   
        super();
        
        fx = new FractionCharlesT(nx, dx);
        fy = new FractionCharlesT(ny, dy);
    }
    
    public PointCharlesT(FractionCharlesT x, FractionCharlesT y) {
        fx = x;
        fy = y;
    }
    
    public PointCharlesT(int x, int y) {
        fx = new FractionCharlesT(x);
        fy = new FractionCharlesT(y);
    }
    
    public PointCharlesT(PointCharlesT p) {
        fx = p.fx;
        fy = p.fy;
    }
    
    //Member Methods
    public void print(PointCharlesT p) {
        System.out.print(p);
    }
    
    @Override
    public String toString() {
        return "        Point: (" + fx.getNum() + 
                "/" + fx.getDenom() + ", " +
                fy.getNum() + "/" + fy.getDenom() + ")";
    }
    
    public FractionCharlesT getX() {
        return fx;
    }
    
    public FractionCharlesT getY() {
        return fy;
    }
    
    public void setX(FractionCharlesT x) {
        fx = x; 
    }
    
    public void setY(FractionCharlesT y) {
        fy = y;
    }
    
    public void setXY(FractionCharlesT x, FractionCharlesT y) {
        fx = x;
        fy = y;
    }
    
    public void setXY(int x, int y) {
        fx = new FractionCharlesT(x);
        fx = new FractionCharlesT(y);
    }
    
    public void moveBy(int delta) {
        fx.updateBy(delta);
        fy.updateBy(delta);
    }
    
    public void moveBy(FractionCharlesT delta) {
        fx.updateBy(delta);
        fy.updateBy(delta);
    }
    
    public void moveTo(int location) {
        fx.update(location);
        fy.update(location);
    }
    
    public void moveTo(FractionCharlesT location) {
        fx.update(location);
        fy.update(location);
    }
    
    public void flipByX(PointCharlesT p) {
        FractionCharlesT f;
        
        f = p.getY();
        f.setNum(-f.getNum());
        setY(f);
        print(p);
    }
    
    public void flipByY(PointCharlesT p) {
        FractionCharlesT f;
        
        f = p.getX();
        f.setNum(-f.getNum());
        setX(f);
        print(p);
    }
    
    public void flipByOrigin(PointCharlesT p) {
        FractionCharlesT x;
        
        x = p.getX();
        p.setX(p.getY());
        p.setY(x);
        print(p);
    }
}