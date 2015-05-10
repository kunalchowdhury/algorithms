package com.study.parser.collection;

/**
 * Created by Kunal Chowdhury on 5/3/2015.
 */
public class ObjectGraph {
    private List<Integer>[] adj;
    private boolean[] visited ;
    private final int v;
    private int e;

    public ObjectGraph(int v) {
        this.visited = new boolean[v];
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

    Iterable<Integer> adjacentTo(int v) {
        return adj[v];
    }

    public int[] search(int source){
        LinkedQueue<Integer> q = new LinkedQueue<>();
        boolean done[] = new boolean[v];
        int path[] = new int[v];
        int distance[] = new int[v];
        q.enqueue(source);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : adjacentTo(v)) {
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

    public boolean[] performDepthFirst(int source){
        visited[source] = true;
        for (int w : adjacentTo(source)) {
            if (!visited[w]) performDepthFirst(w);
        }

        return visited;
    }

    public boolean[] performDepthFirst(Iterable<Integer> sources){
        visited = new boolean[v];
        for (int v : sources) {
            if (!visited[v]) performDepthFirst(v);
        }

        return visited;
    }

}
