import java.util.Iterator;

/**
 * Created by Ireena on 4/9/14.
 */
public class KdTree {
    private int size;
    private Node root;
    private SET<Point2D> set;

    public KdTree() {
        this.set = new SET<Point2D>();
    }

    private static class Node{
        private Node left ;
        private Node right ;
        private Point2D item ;
        private RectHV rectHV;

        private Node(Point2D item) {
            this.item = item;
        }

        public void setItem(Point2D item) {
            this.item = item;
        }

        public void setRectHV(RectHV rectHV) {
            this.rectHV = rectHV;
        }
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size ;
    }
    public void insert(Point2D p){
        if((p == null) || set.contains(p)){
            return;
        }
        if(isEmpty()){
            root = new Node(p);
            root.setRectHV(new RectHV(0, 0, 1, 1));
            set.add(root.item);
            size++;
            return;
        }

        insertRecursive(root, p, 0);
        set.add(p);
    }

    private Node insertRecursive(Node current, Point2D p, int level){
        if(current ==null){
            current = new Node(p);
            size++;
            return current;
        }else if(current.item.equals(p)){
            return current;
        }
        boolean useXCoord = (level % 2) == 0;
        level++;
        if(useXCoord){
            if(current.item.x() > p.x()){
                current.left =  insertRecursive(current.left, p, level);
                if(current.left.rectHV == null){
                    associateRectangle(current.left,current, true,false);
                }
            }else if(current.item.x() <= p.x()){
                current.right = insertRecursive(current.right, p, level);
                if(current.right.rectHV == null){
                    associateRectangle(current.right, current, true, true);
                }
            }
        }else{
            if(current.item.y() > p.y()){
                current.left = insertRecursive(current.left, p, level);
                if(current.left.rectHV == null){
                    associateRectangle(current.left,current, false,false);
                }
            }else if(current.item.y() <= p.y()){
                current.right = insertRecursive(current.right, p, level);
                if(current.right.rectHV == null){
                    associateRectangle(current.right,current, false,true);
                }
            }
        }
        return current;
    }
    private void associateRectangle(Node current, Node parent, boolean useXCoord, boolean isRight){
        RectHV rectHV = parent.rectHV;
        Point2D node = parent.item;
        if(useXCoord){
            if(isRight){
                current.rectHV = new RectHV(node.x(), rectHV.ymin(),parent.rectHV.xmax(),parent.rectHV.ymax());
            }else{
                current.rectHV = new RectHV(rectHV.xmin(),rectHV.ymin(), node.x(),rectHV.ymax());
            }
        } else{
            if(isRight){
                current.rectHV = new RectHV(rectHV.xmin(), node.y(),rectHV.xmax(),rectHV.ymax());
            }else{
                current.rectHV = new RectHV(rectHV.xmin(),rectHV.ymin(),rectHV.xmax(), node.y());
            }
        }
    }

    public boolean contains(Point2D p){
        return !(isEmpty() || (p == null)) && search(root, p, 0);

    }

    private boolean search(Node node, Point2D p, int level) {
        if(node == null){
            return false ;
        }else if(node.item.equals(p)){
            return true;
        }
        boolean useXCoord = (level % 2) ==0;
        level++;
        if(useXCoord){
            if(node.item.x() > p.x()){
                return search(node.left, p, level);
            }else if(node.item.x() <= p.x()){
                return search(node.right, p, level);
            }
        }else {
            if(node.item.y() > p.y()){
                return search(node.left, p, level);
            }else if(node.item.y() <= p.y()){
                return search(node.right, p, level);
            }
        }
        return false;
    }
    public void draw(){
       drawRecursive(root, 0);
    }
    private void drawRecursive(Node node,int level){
        if(node == null){
            return;
        }
        drawPoint(node);
        StdDraw.setPenRadius();
        boolean useX = (level % 2) ==0;
        RectHV rect = node.rectHV ;
        if(useX){
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.item.x(), rect.ymin(),node.item.x(), rect.ymax() );
        }else{
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), node.item.y(),rect.xmax(), node.item.y() );
        }
        level++;
        drawRecursive(node.left, level);
        drawRecursive(node.right, level);

    }
    private void drawPoint(Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.item.draw();
    }

    public Iterable<Point2D> range(RectHV rect){
        final SET<Point2D> set = new SET<Point2D>();
        collectPoints(set, root , rect);
        return new Iterable<Point2D>() {
            public Iterator<Point2D> iterator() {
                return set.iterator();
            }
        };
    }

    private void collectPoints(SET<Point2D> set, Node node, RectHV rect) {
        if(node == null){
            return;
        }
        if(node.left!= null && node.right!=null
                && !node.left.rectHV.intersects(rect)
                && !node.right.rectHV.intersects(rect)){
            return;
        }
        if(rect.contains(node.item)){
            set.add(node.item);
        }

        if(node.left !=null ){
            collectPoints(set, node.left, rect);
        }
        if(node.right !=null ){
            collectPoints(set, node.right, rect);
        }
    }

    public Point2D nearest(Point2D p){
        if(root == null){
            return null;
        }
        Node finalNode = new Node(root.item);
        recurseAndFind(root, p, finalNode);
        return finalNode.item ;

    }

    private void recurseAndFind(Node node, Point2D p, Node finalNode) {
        if(node ==null){
            return;
        }

        double bestDistance =  finalNode.item.distanceTo(p);
        double v = node.rectHV.distanceTo(p);
        if( v > bestDistance){
            return;
        }
        if(bestDistance > node.item.distanceTo(p)) {
            finalNode.setItem(node.item);
        }

        if(node.left == null && node.right == null){
            return;
        }

        if(node.left == null){
            recurseAndFind(node.right,p,finalNode);
        }else if(node.right == null){
            recurseAndFind(node.left,p,finalNode);
        } else if(node.left.rectHV.distanceTo(p) > node.right.rectHV.distanceTo(p)){
            recurseAndFind(node.right,p,finalNode);
            recurseAndFind(node.left,p,finalNode);
        } else {
            recurseAndFind(node.left,p,finalNode);
            recurseAndFind(node.right,p,finalNode);


        }
    }

}
