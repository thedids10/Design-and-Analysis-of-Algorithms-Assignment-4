package graph.topo;

import java.util.*;
import graph.metrics.Metrics;
import graph.metrics.MetricsImpl;

/**
 * TopoSort - Topological sorting on condensed DAG (Kahn & DFS).
 * Student: Nursultan Tursunbaev, SE-2402
 */
public class TopoSort {
    private Metrics metrics = new MetricsImpl();

    public int[] kahnSort(int[][] dag) {
        int n = dag.length;
        int[] inDeg = new int[n];
        for (int[] row : dag) for (int v = 0; v < n; v++) if (row[v]>0) inDeg[v]++;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) if (inDeg[i]==0) {q.add(i); metrics.incrementPush();}
        List<Integer> order = new ArrayList<>();
        metrics.recordStart();
        while (!q.isEmpty()) {
            int u = q.poll();
            metrics.incrementPop();
            order.add(u);
            for (int v = 0; v < n; v++) if (dag[u][v]>0 && --inDeg[v]==0) {q.add(v); metrics.incrementPush();}
        }
        metrics.recordEnd();
        if (order.size()!=n) System.out.println("Graph not DAG, topological order not complete");
        return order.stream().mapToInt(i->i).toArray();
    }

    public int[] dfsSort(int[][] dag) {
        int n = dag.length;
        boolean[] vis = new boolean[n];
        List<Integer> order = new ArrayList<>();
        metrics.recordStart();
        for (int i = 0; i < n; i++) if (!vis[i]) dfs(i, dag, vis, order);
        metrics.recordEnd();
        Collections.reverse(order);
        return order.stream().mapToInt(i->i).toArray();
    }
    private void dfs(int u, int[][] dag, boolean[] vis, List<Integer> order) {
        vis[u]=true;
        for (int v=0; v<dag[u].length;v++) if (dag[u][v]>0 && !vis[v]) dfs(v,dag,vis,order);
        order.add(u);
    }
    public Metrics getMetrics(){return metrics;}
    public void printOrder(String label, int[] order){
        System.out.print(label+":" );
        for (int v : order) System.out.print(" " + v);
        System.out.println();
    }
}
