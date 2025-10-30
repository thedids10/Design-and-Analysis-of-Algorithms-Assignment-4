package graph;

import java.util.Scanner;
import java.util.List;
import graph.scc.SCCSolver;
import graph.topo.TopoSort;
import graph.dagsp.DAGShortestPath;
import graph.metrics.Metrics;
import graph.utils.DatasetGenerator;
import graph.utils.GraphIO;

/**
 * Main - Entry point for Assignment 4 demo execution.
 * Student: Nursultan Tursunbaev, SE-2402
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Smart City / Campus Scheduling Assignment");
        System.out.println("1: Run SCC Task");
        System.out.println("2: Run Topological Sort");
        System.out.println("3: Run Shortest/Longest Path in DAG");
        System.out.println("4: Generate Datasets");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        try {
            switch (choice) {
                case 1: {
                    System.out.println("SCC Task selected: using tasks.json");
                    int[][] adj = GraphIO.loadAdjMatrix("tasks.json");
                    SCCSolver sccSolver = new SCCSolver();
                    List<List<Integer>> sccs = sccSolver.findSCCs(adj);
                    sccSolver.printResults();
                    sccSolver.buildCondensationGraph(adj, sccs); // печатает соответствие
                    break;
                }
                case 2: {
                    System.out.println("Topological Sort: Condensation of tasks.json");
                    int[][] adj = GraphIO.loadAdjMatrix("tasks.json");
                    SCCSolver sccSolver = new SCCSolver();
                    List<List<Integer>> sccs = sccSolver.findSCCs(adj);
                    int[][] dag = sccSolver.buildCondensationGraph(adj, sccs);
                    TopoSort topo = new TopoSort();
                    int[] order = topo.kahnSort(dag);
                    topo.printOrder("Kahn order of SCCs", order);
                    System.out.printf("Kahn: push %d, pop %d, time %d ns\n", topo.getMetrics().getPushCount(),topo.getMetrics().getPopCount(),topo.getMetrics().getElapsedTime());
                    int[] dfsOrder = topo.dfsSort(dag);
                    topo.printOrder("DFS topo order of SCCs", dfsOrder);
                    break;
                }
                case 3: {
                    System.out.println("DAG Shortest/Longest Path from tasks.json");
                    int[][] adj = GraphIO.loadAdjMatrix("tasks.json");
                    SCCSolver sccSolver = new SCCSolver();
                    List<List<Integer>> sccs = sccSolver.findSCCs(adj);
                    int[][] dag = sccSolver.buildCondensationGraph(adj, sccs);
                    TopoSort topo = new TopoSort();
                    int[] order = topo.kahnSort(dag);

                    System.out.print("Enter source SCC index [0-" + (dag.length-1) + "]: ");
                    int source = scanner.nextInt();
                    DAGShortestPath dagSP = new DAGShortestPath();
                    int[] dist = dagSP.shortestPath(dag, source, order);
                    dagSP.printResult("SCC shortest from SCC " + source, dist, source);

                    int[] ldist = dagSP.longestPath(dag, source, order);
                    dagSP.printResult("SCC longest from SCC " + source, ldist, source);

                    int target = -1, maxL = Integer.MIN_VALUE;
                    for (int i=0;i<ldist.length;i++) if (ldist[i]>maxL) {maxL=ldist[i]; target=i;}
                    int[] lpath = dagSP.reconstructPath(dag,source,target,ldist,order,true);
                    System.out.print("Critical path: "); for (int v:lpath) System.out.print(v+" "); System.out.println(" (len = "+maxL+")");
                    System.out.printf("Shortest: relax %d, time %d ns\n", dagSP.getMetrics().getRelaxCount(), dagSP.getMetrics().getElapsedTime());
                    break;
                }
                case 4: {
                    System.out.println("Generating datasets in /data ...");
                    DatasetGenerator.main(new String[0]);
                    System.out.println("Datasets generated.");
                    break;
                }
                default:
                    System.out.println("Invalid choice");
            }
        } catch(Exception e) {
            System.err.println("Error: "+e.getMessage());
            e.printStackTrace();
        }
        scanner.close();
    }
}
