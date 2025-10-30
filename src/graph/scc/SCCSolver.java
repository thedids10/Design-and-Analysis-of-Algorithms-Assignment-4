package graph.scc;

import java.util.*;
import graph.metrics.Metrics;
import graph.metrics.MetricsImpl;

/**
 * SCCSolver - Tarjan's algorithm for SCC detection & condensation graph.
 * Student: Nursultan Tursunbaev, SE-2402
 */
public class SCCSolver {
    private List<List<Integer>> sccs = new ArrayList<>();
    private int[] ids, low, stackMember;
    private int id;
    private Deque<Integer> stack;
    private Metrics metrics = new MetricsImpl();

    /**
     * Runs Tarjan's SCC algorithm on the given adjacency matrix.
     */
    public List<List<Integer>> findSCCs(int[][] adj) {
        int n = adj.length;
        ids = new int[n]; Arrays.fill(ids, -1);
        low = new int[n];
        stackMember = new int[n];
        stack = new ArrayDeque<>();
        id = 0;
        metrics.recordStart();
        for (int i = 0; i < n; i++) {
            if (ids[i] == -1) dfs(i, adj);
        }
        metrics.recordEnd();
        return sccs;
    }

    private void dfs(int at, int[][] adj) {
        metrics.incrementDFS();
        ids[at] = low[at] = id++;
        stack.push(at); stackMember[at] = 1;
        for (int to = 0; to < adj.length; to++) if (adj[at][to] > 0) {
            metrics.incrementEdge();
            if (ids[to] == -1) {
                dfs(to, adj);
                low[at] = Math.min(low[at], low[to]);
            } else if (stackMember[to] == 1) {
                low[at] = Math.min(low[at], ids[to]);
            }
        }
        if (ids[at] == low[at]) {
            List<Integer> comp = new ArrayList<>();
            while (!stack.isEmpty()) {
                int node = stack.pop(); stackMember[node] = 0;
                comp.add(node);
                if (node == at) break;
            }
            sccs.add(comp);
        }
    }

    /** Returns SCCs found after findSCCs */
    public List<List<Integer>> getSCCs() { return sccs; }

    /**
     * Builds the condensation graph (DAG of SCCs) from SCCs.
     * Returns condensation as adjacency matrix and prints ID map.
     */
    public int[][] buildCondensationGraph(int[][] adj, List<List<Integer>> sccs) {
        int n = adj.length;
        int[] compId = new int[n];
        for (int i = 0; i < sccs.size(); i++) for (int v : sccs.get(i)) compId[v]=i;
        int m = sccs.size();
        int[][] dag = new int[m][m];
        for (int u = 0; u < n; u++) for (int v = 0; v < n; v++) {
            if (adj[u][v] > 0 && compId[u]!=compId[v]) dag[compId[u]][compId[v]] = 1;
        }
        System.out.print("Condensation SCC Map (orig node -> component): ");
        System.out.println(Arrays.toString(compId));
        return dag;
    }

    public Metrics getMetrics(){return metrics;}
    public void printResults() {
        System.out.println("Found " + sccs.size() + " strongly connected component(s):");
        for (int i = 0; i < sccs.size(); i++) {
            System.out.println("SCC " + (i + 1) + " (size=" + sccs.get(i).size() + "): " + sccs.get(i));
        }
        System.out.printf("DFS calls: %d, Edges: %d, Time(ns): %d\n",
            metrics.getDFSCount(),metrics.getEdgeCount(),metrics.getElapsedTime());
    }
}
