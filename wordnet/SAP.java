import java.util.*;

/**
 * Created by Ireena on 5/14/14.
 */
public class SAP {
    private class Key<T>{
         T src;
         T dest;

        Key(T src, T dest) {
            this.src = src;
            this.dest = dest;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (!dest.equals(key.dest)) return false;
            if (!src.equals(key.src)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = src.hashCode();
            result = 31 * result + dest.hashCode();
            return result;
        }
    }


    private final Digraph G ;

    private Map<Key<Integer>, Integer> ancestorMap = new HashMap<Key<Integer>, Integer>();

    private Map<Key<Integer>, Integer> lengthMap = new HashMap<Key<Integer>, Integer>();

    private Map<Key<Iterable<Integer>>, Integer> ancestorIterMap = new HashMap<Key<Iterable<Integer>>, Integer>();

    private Map<Key<Iterable<Integer>>, Integer> lengthIterMap = new HashMap<Key<Iterable<Integer>>, Integer>();


    public SAP(Digraph G){
        this.G = G ;
    }
    public int length(int v, int w){
        int[] vals = init(new Key<Integer>(v,w));
        return vals[1] ;
    }


    public int ancestor(int v, int w){
        int[] vals = init(new Key<Integer>(v,w));
        return vals[0] ;

    }

    private int[] fun(BreadthFirstDirectedPaths bfs1, BreadthFirstDirectedPaths bfs2){
        int[] vals = new int[2] ;
        int commonAncestor = -1;
        int length = Integer.MAX_VALUE;
        for (int i = 0; i < G.V() ; i++) {
              if(bfs1.hasPathTo(i) && bfs2.hasPathTo(i)){
                  int currDist =  bfs1.distTo(i) + bfs2.distTo(i);
                  if(currDist < length){
                      length = currDist;
                      commonAncestor = i;
                  }
              }
        }
        if(commonAncestor == -1){
            length = -1;
        }

        vals[0] = commonAncestor;
        vals[1] = length;
        return vals ;
    }

    private int[] init(Key k){
        BreadthFirstDirectedPaths bfs1 = null;
        BreadthFirstDirectedPaths bfs2 = null;
        if(k.src instanceof Integer){
            if((Integer)k.src < 0 || (Integer)k.src > G.V() -1){
                    throw new ArrayIndexOutOfBoundsException();
            }

            if((Integer)k.dest < 0 || (Integer)k.dest > G.V() -1){
                throw new ArrayIndexOutOfBoundsException();
            }

            if(ancestorMap.containsKey(k) && lengthMap.containsKey(k)){
                return new int[]{ancestorMap.get(k), lengthMap.get(k)};
            }
            bfs1 = new BreadthFirstDirectedPaths(G, (Integer) k.src);
            bfs2 = new BreadthFirstDirectedPaths(G, (Integer)k.dest);
        } else if(k.src instanceof Iterable){
             for(Integer i : (Iterable<Integer>) k.src){
                    if(i < 0 || i > G.V() -1){
                        throw new ArrayIndexOutOfBoundsException();
                    }
             }

            for(Integer i : (Iterable<Integer>) k.dest){
                    if(i < 0 || i > G.V() -1){
                        throw new ArrayIndexOutOfBoundsException();
                    }
            }


            if(ancestorIterMap.containsKey(k) && lengthIterMap.containsKey(k)){
                return new int[]{ancestorIterMap.get(k), lengthIterMap.get(k)};
            }
            bfs1 = new BreadthFirstDirectedPaths(G, (Iterable<Integer>) k.src);
            bfs2 = new BreadthFirstDirectedPaths(G, (Iterable<Integer>)k.dest);
        }
        int[] vals = fun(bfs1, bfs2) ;

        if(k.src instanceof Integer){
            ancestorMap.put(k,vals[0]);
            lengthMap.put(k,vals[1]);
        } else if(k.src instanceof Iterable){
            ancestorIterMap.put(k, vals[0]);
            lengthIterMap.put(k, vals[1]);
        }
        return vals ;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w){
        int[] vals = init(new Key<Iterable<Integer>>(v,w));
        return vals[1] ;

    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        int[] vals = init(new Key<Iterable<Integer>>(v,w));
        return vals[0] ;

    }



    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        System.out.println(sap.length(12, 12));
        /*while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }*/
        /*List<Integer> s = new ArrayList<Integer>();

        s.add(9);
        s.add(8);
       *//* s.add(4);
        s.add(7);*//*


        List<Integer> s1 = new ArrayList<Integer>();

        s1.add(9);
        s1.add(8);


        System.out.println(sap.length(s, s1));*/


    }



}
