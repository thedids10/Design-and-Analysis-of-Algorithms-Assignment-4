package graph.metrics;

/**
 * MetricsImpl - Implementation of Metrics interface
 * Student: Nursultan Tursunbaev, SE-2402
 */
public class MetricsImpl implements Metrics {
    private int dfs, edge, push, pop, relax;
    private long start, end;
    public void incrementDFS() { dfs++; }
    public void incrementEdge() { edge++; }
    public void incrementPush() { push++; }
    public void incrementPop() { pop++; }
    public void incrementRelax() { relax++; }
    public void recordStart() { start = System.nanoTime(); }
    public void recordEnd() { end = System.nanoTime(); }
    public long getElapsedTime() { return end - start; }
    public int getDFSCount() { return dfs; }
    public int getEdgeCount() { return edge; }
    public int getPushCount() { return push; }
    public int getPopCount() { return pop; }
    public int getRelaxCount() { return relax; }
}
