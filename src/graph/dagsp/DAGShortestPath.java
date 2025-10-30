package graph.dagsp;

import java.util.*;
import graph.metrics.Metrics;
import graph.metrics.MetricsImpl;

/**
 * DAGShortestPath - shortest/longest path on DAG. Edge weights.
 * Student: Nursultan Tursunbaev, SE-2402
 */
public class DAGShortestPath {
    private Metrics metrics = new MetricsImpl();

    public int[] shortestPath(int[][] dag, int source, int[] topo) {
        int n = dag.length;
        int[] dist = new int[n];
        Arrays.fill(dist,Integer.MAX_VALUE/2); dist[source]=0;
        metrics.recordStart();
        for (int u : topo) for (int v = 0; v < n; v++) if (dag[u][v]>0) {
            if (dist[u]+dag[u][v]<dist[v]) {dist[v]=dist[u]+dag[u][v];metrics.incrementRelax();}
        }
        metrics.recordEnd();
        return dist;
    }

    public int[] longestPath(int[][] dag, int source, int[] topo) {
        int n = dag.length;
        int[] dist = new int[n];
        Arrays.fill(dist,Integer.MIN_VALUE/2); dist[source]=0;
        metrics.recordStart();
        for (int u : topo) for (int v = 0; v < n; v++) if (dag[u][v]>0) {
            if (dist[u]+dag[u][v]>dist[v]) {dist[v]=dist[u]+dag[u][v];metrics.incrementRelax();}
        }
        metrics.recordEnd();
        return dist;
    }
    public Metrics getMetrics(){return metrics;}
    public void printResult(String label, int[] dist, int source) {
        System.out.println(label+":");
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Node "+i+" from "+source+": "+dist[i]);
        }
    }
    public int[] reconstructPath(int[][] dag, int source, int target, int[] dist, int[] topo, boolean longest) {
        int n = dag.length;
        int[] prev = new int[n]; Arrays.fill(prev,-1);
        prev[source] = source;
        if (longest){
            Arrays.fill(prev,-1); prev[source]=source;
            for(int u : topo) for(int v=0;v<n;v++) if(dag[u][v]>0 && dist[u]+dag[u][v]==dist[v]) prev[v]=u;
        } else {
            for(int u : topo) for(int v=0;v<n;v++) if(dag[u][v]>0 && dist[u]+dag[u][v]==dist[v] && prev[v]==-1) prev[v]=u;
        }
        List<Integer> path = new ArrayList<>();
        for(int at=target; prev[at]!=-1 && at!=source; at=prev[at]) path.add(at);
        if(prev[target]!=-1) { path.add(source); Collections.reverse(path); }
        return path.stream().mapToInt(i->i).toArray();
    }
}
