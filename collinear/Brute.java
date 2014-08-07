import java.util.Arrays;

/**
 * Created by Ireena on 3/26/14.
 */
public class Brute {
    private static class PointVO{
        private Point[] points ;
        private int startIndex ;
        PointVO(Point[] points, int startIndex) {
            this.points = points;
            this.startIndex = startIndex;

        }
        PointVO union(Point point){
            Point[] temp = new Point[points.length + 1];
            for (int i = startIndex; i < points.length ; i++) {
                temp[i] = points[i];
            }
            temp[points.length] = point;
            return new PointVO(temp, 0);
        }
        private PointVO subPointVO(int index){
            return new PointVO(points,startIndex + index);
        }
        private Point pointAt(int index ){
            return points[startIndex + index] ;
        }

        private int length(){
            return (points.length - startIndex) ;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(int i = startIndex ; i < points.length ; i++){
                sb.append(points[i].toString());
            }
            return sb.toString();
        }
    }
    private static void sortAndPrint(PointVO original, PointVO result, int k ){
        if(k == 0){
            Point[] pointArr = result.points;
            int slopeComp = pointArr[0].SLOPE_ORDER.compare(pointArr[1],pointArr[2]);
            if(slopeComp == 0){
                int secondSlopeComp = pointArr[0].SLOPE_ORDER.compare(pointArr[1],pointArr[3]);
                if(secondSlopeComp == 0){
                    StdDraw.setXscale(0, 32768);
                    StdDraw.setYscale(0, 32768);
                    Arrays.sort(pointArr);
                    pointArr[0].drawTo(pointArr[3]);

                    Arrays.sort(pointArr);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 3 ; i++) {
                        sb.append(pointArr[i]).append(" -> ");
                    }
                    sb.append(pointArr[3]);
                    System.out.println(sb.toString());
                }

            }


        }else{
            for(int i=0 ; i < original.length(); i++){
                sortAndPrint(original.subPointVO(i+1),result.union(original.pointAt(i)),k-1);
            }
        }
    }

    public static void main(String[] args) {
          In in = new In(args[0]);
          Point[] points = null ;
          int i = 0;
          StdDraw.setXscale(0, 32768) ;
          StdDraw.setYscale(0, 32768);
          while(in.hasNextLine()){
              String[] arr = in.readLine().trim().split("\\s+");
              if(arr.length == 1){
                  if(!arr[0].isEmpty()){
                    points = new Point[Integer.parseInt(arr[0].toString())] ;
                  }
              }else{
                  final int x = Integer.parseInt(arr[0].trim());
                  final int y = Integer.parseInt(arr[1].trim());
                  Point p =          new Point(x, y);
                  points[i++] =  p;
                  p.draw();
              }

          }
        if(i == 1){
            points[0].draw();
        }else if(i == 2){
            points[0].drawTo(points[1]);
        }else if(i == 3){
            if(points[0].SLOPE_ORDER.compare(points[1],points[2]) == 0){
                points[0].drawTo(points[1]);
                points[0].drawTo(points[2]);
            }

        }else{
         sortAndPrint(new PointVO(points,0),new PointVO(new Point[0],0),4);
        }

    }
}
