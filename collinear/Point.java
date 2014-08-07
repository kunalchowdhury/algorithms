import java.util.Comparator;

/**
 * Created by Ireena on 3/26/14.
 */
public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        public int compare(Point p1, Point p2) {
            int x0 = Point.this.x;
            int y0 = Point.this.y;

            int x1 = p1.x ;
            int y1 = p1.y;

            int x2 = p2.x;
            int y2 = p2.y;

            double slope01 = Point.this.slopeTo(p1);
            double slope02 = Point.this.slopeTo(p2);

            int result = (slope01 < slope02 )? -1 : (slope01 > slope02) ?1 : 0;
            return result;
        }
    };


    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
            /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
            /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
            /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
            double d = Double.NaN;
            int x0 = this.x;
            int y0 = this.y;

            int x1 = that.x;
            int y1 = that.y;

            if(x0 == x1 && y0 == y1){
                d = Double.NEGATIVE_INFINITY ;
            }else if(y0 == y1 ){
               d = 0;
            }else if(x0 == x1){
               d= Double.POSITIVE_INFINITY;
            }else{
               d = (double)(y1-y0)/(x1-x0);
            }
            return d;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
          int retVal = (this.y < that.y) ? -1 : (this.y > that.y) ? 1 : (this.x < that.x) ? -1 : (this.x > that.x) ? 1: 0;
          return retVal;
    }



    // return string representation of this point
    public String toString() {
            /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        System.out.println(new Point(19000, 10000).SLOPE_ORDER.compare(new Point(18000, 10000),new Point(1234, 5678)));

       // System.out.println(new Point(3,10).slopeTo(new Point(10, 19))) ;
    }
}