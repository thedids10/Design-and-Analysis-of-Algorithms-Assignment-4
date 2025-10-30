package graph.metrics;

/**
 * Metrics - Tracks algorithm operation counters and elapsed time.
 * Student: Nursultan Tursunbaev, SE-2402
 */
public interface Metrics {
    void incrementDFS();
    void incrementEdge();
    void incrementPush();
    void incrementPop();
    void incrementRelax();
    void recordStart();
    void recordEnd();
    long getElapsedTime();
    int getDFSCount();
    int getEdgeCount();
    int getPushCount();
    int getPopCount();
    int getRelaxCount();
}
