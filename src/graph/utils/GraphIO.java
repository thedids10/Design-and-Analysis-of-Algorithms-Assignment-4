package graph.utils;

import java.io.*;
import java.util.*;

/**
 * GraphIO - Utility to parse tasks.json and similar graph files.
 * Student: Nursultan Tursunbaev, SE-2402
 */
public class GraphIO {
    /** Loads an adjacency matrix from given tasks.json-style file */
    public static int[][] loadAdjMatrix(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            String json = sb.toString();
            int n = Integer.parseInt(json.replaceAll(".*\"n\"\s*:\s*(\\d+).*", "$1"));
            int[][] adj = new int[n][n];
            // Parse edges array
            String[] edges = json.split("\\[", 2)[1].split("\\]", 2)[0].split("\\},");
            for (String e : edges) {
                if (!e.contains("\"u\"")) continue;
                int u = Integer.parseInt(e.replaceAll(".*\"u\"\s*:\s*(\\d+).*", "$1"));
                int v = Integer.parseInt(e.replaceAll(".*\"v\"\s*:\s*(\\d+).*", "$1"));
                int w = Integer.parseInt(e.replaceAll(".*\"w\"\s*:\s*(\\d+).*", "$1"));
                adj[u][v] = w;
            }
            return adj;
        }
    }
    // You can extend this for source, weight model, etc.
}
