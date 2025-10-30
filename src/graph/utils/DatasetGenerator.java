package graph.utils;

import java.util.*;
import java.io.*;

/**
 * DatasetGenerator - generates graph datasets of varying sizes/density/types.
 * Outputs JSON to /data/.
 * Student: Nursultan Tursunbaev, SE-2402
 */
public class DatasetGenerator {
    public static void main(String[] args) throws IOException {
        String[] categories = {"Small", "Medium", "Large"};
        int[][] nodeRanges = {{6, 10}, {10, 20}, {20, 50}};
        Random rand = new Random();
        int filesPerCategory = 3;

        for (int i = 0; i < categories.length; i++) {
            for (int v = 0; v < filesPerCategory; v++) {
                int n = nodeRanges[i][0] + rand.nextInt(nodeRanges[i][1] - nodeRanges[i][0] + 1);
                boolean cyclic = (v % 2 == 0); // Alternate cyclic/acyclic
                boolean dense = (v == 2);     // Last variant is dense
                int[][] adj = generateGraph(n, cyclic, dense, rand);
                String fileName = String.format("/Users/nursultantursunbaev/Desktop/Design-and-Analysis-of-Algorithms-Assignment-4/data/%s_%d.json", categories[i].toLowerCase(), v + 1);
                saveGraphAsJson(adj, fileName);
            }
        }
    }

    // Generate random graph as adjacency matrix
    private static int[][] generateGraph(int n, boolean cyclic, boolean dense, Random rand) {
        int[][] adj = new int[n][n];
        double p = dense ? 0.6 : 0.25;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && rand.nextDouble() < p) {
                    // For acyclic: only edges i->j if i < j
                    if (!cyclic && i < j) adj[i][j] = 1 + rand.nextInt(10);
                    else if (cyclic) adj[i][j] = 1 + rand.nextInt(10);
                }
            }
        }
        return adj;
    }

    // Save adjacency matrix as simple JSON
    private static void saveGraphAsJson(int[][] adj, String fileName) throws IOException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println("{");
            out.printf("  \"n\": %d,%n", adj.length);
            out.println("  \"edges\": [");
            boolean first = true;
            for (int i = 0; i < adj.length; i++) for (int j = 0; j < adj[i].length; j++) {
                if (adj[i][j] > 0) {
                    if (!first) out.println(",");
                    out.printf("    {\"from\":%d, \"to\":%d, \"weight\":%d}", i, j, adj[i][j]);
                    first = false;
                }
            }
            out.println("\n  ]\n}");
        }
    }
}
