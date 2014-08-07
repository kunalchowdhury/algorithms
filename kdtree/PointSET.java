import java.util.Iterator;

/**
 * Created by Ireena on 4/3/14.
 */
public class PointSET {
    private SET<Point2D> pointSet ;
    public PointSET(){
        pointSet = new SET<Point2D>();
    }
    public boolean isEmpty(){
        return pointSet.isEmpty();
    }
    public int size(){
        return pointSet.size();
    }
    public void insert(Point2D p){
        pointSet.add(p);
    }
    public boolean contains(Point2D p){
        return pointSet.contains(p);
    }
    public void draw(){
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(.005);
        for(Point2D p : pointSet){
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect){
        SET<Point2D> rangePoints = new SET<Point2D>() ;
        for(Point2D p : pointSet){
            if (rect.contains(p)){
                 rangePoints.add(p);
            }
        }
        return rangePoints;
    }
    public Point2D nearest(Point2D p){
        Point2D rangePoint = null;
        double dist  = Double.POSITIVE_INFINITY;
        for(Point2D p1 : pointSet){
            if(dist > p1.distanceTo(p)){
                  dist = p1.distanceTo(p);
                  rangePoint =  p1;
            }
        }
        return rangePoint;
    }

}
