package com.study.parser.collection;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
public class ObjectGraph {
    private final int v;
    private int e;
    private List<Integer>[] adj;

    public ObjectGraph(int v) {
        this.v = v;
        this.e= 0;
        adj = (List<Integer>[]) new List[v];
        for (int i = 0; i < v; i++) {
                adj[i] = new List<Integer>() ;
        }
    }

    int vertices() {
        return v;
    }

    int edges(){
        return e;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        e++;
    }

    Iterable<Integer> adjacent(int v) {
        return adj[v];
    }

    public int[] search(int source){
        LinkedQueue<Integer> q = new LinkedQueue<Integer>();
        boolean done[] = new boolean[v];
        int path[] = new int[v];
        int distance[] = new int[v];
        q.enqueue(source);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : adjacent(v)) {
                if (!done[w]) {
                    path[w] = v;
                    distance[w] = distance[v] + 1;
                    done[w] = true;
                    q.enqueue(w);
                }
            }
        }
        return path;
    }

}
