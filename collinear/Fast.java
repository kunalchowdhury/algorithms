import java.util.*;


/**
 * Created by Ireena on 3/27/14.
 */
public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        Point[] points = null ;
        Point[] master = null;
        int i = 0;
        StdDraw.setXscale(0, 32768) ;
        StdDraw.setYscale(0, 32768);
        while(in.hasNextLine()){
            String[] arr = in.readLine().trim().split("\\s+");
            if(arr.length == 1){
               if(!arr[0].isEmpty()){
                points = new Point[Integer.parseInt(arr[0].toString())] ;
                master = new Point[Integer.parseInt(arr[0].toString())] ;
               }
            }else{
                final int x = Integer.parseInt(arr[0].trim());
                final int y = Integer.parseInt(arr[1].trim());
                Point p =          new Point(x, y);
                points[i] =  p;
                master[i++] = p;
                p.draw();
            }

        }
        if(i < 4 ){
            return;
        }else{
            Queue[] result = process(points, master, i);
            for (int j = 0; j < i; j++) {
                if(result[j] != null){
                    int sz =   result[j].size();
                    Point[] arr = new Point[sz];
                    for (int k = 0; k < sz   ; k++) {
                        arr[k]= (Point)result[j].dequeue();
                    }
                    Arrays.sort(arr);
                    StringBuilder sb = new StringBuilder();
                    for (int k = 0; k < arr.length - 1 ; k++) {
                         sb.append(arr[k]).append(" -> ");
                    }
                    sb.append(arr[arr.length -1]);
                    arr[0].drawTo(arr[arr.length - 1]);
                    StdOut.println(sb.toString())  ;
                }
            }
        }
    }
    @SuppressWarnings("unchecked")
    private static Queue[] process(Point[] points, Point[] master, int i) {
        Arrays.sort(master);
        //Stack[][] printStacks = new Stack[i][i];
        Queue[] printQueue = new Queue[i];
        // n^2logn +n^2
        int count = 0;
        for (int j = 0; j < master.length ; j++) {                //n
            Stack<Point> stack = new Stack<Point>();
            stack.push(master[j]);
            Arrays.sort(points, master[j].SLOPE_ORDER);   // nlogn
            stack.push(points[1]);
            for (int k = 2; k < points.length; k++) {                //n
                Point top = stack.pop();
                printQueue[count] = new Queue();
                printQueue[count].enqueue(master[j]);
                printQueue[count].enqueue(top);

                Point minPoint = top; // we will calculate the minimum in the intermediate stack
                while ((top != master[j]) && master[j].SLOPE_ORDER.compare(top, points[k]) != 0){
                       top = stack.pop();
                       if(top.compareTo(minPoint) == -1){
                             minPoint = top;
                       }
                       if(top  != master[j]){
                           printQueue[count].enqueue(top);
                        }
                   }
                   if(minPoint != master[j] || printQueue[count].size() < 4){
                       printQueue[count] = null; // nothing to check here as the starting point is not the min
                   } else {
                       count++;
                   }
                   stack.push(top);
                   stack.push(points[k]);
            }
            if(stack.size() >= 4){
                int sz = stack.size();
                Point[] stackPoints = new Point[sz];
                Point minPoint = stack.peek();
                for (int k = 0; k < sz ; k++) {
                    Point p = stack.pop();
                    if(p.compareTo(minPoint) == -1){
                        minPoint = p;
                    }
                    stackPoints[sz-k-1] = p;
                }
                if(minPoint == stackPoints[0]){
                    printQueue[count]= new Queue();
                    for (int k = 0; k < stackPoints.length ; k++) {
                        printQueue[count].enqueue(stackPoints[k]);
                    }
                    count++;
                }

            }
        }
        return printQueue;
    }
}
