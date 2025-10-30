package graph.dagsp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DAGShortestPathTest {
    @Test
    public void testShortestAndLongestPath() {
        // 0 -> 1 (1), 1 -> 2 (2), 0 -> 2 (5)
        int[][] dag = {
            {0, 1, 5},
            {0, 0, 2},
            {0, 0, 0}
        };
        DAGShortestPath d = new DAGShortestPath();
        int[] topo = {0, 1, 2};
        int[] sp = d.shortestPath(dag, 0, topo);
        assertEquals(0, sp[0]);
        assertEquals(1, sp[1]);
        assertEquals(3, sp[2]);
        int[] lp = d.longestPath(dag, 0, topo);
        assertEquals(0, lp[0]);
        assertEquals(1, lp[1]);
        assertEquals(5, lp[2]);
    }
    @Test
    public void testPathReconstruction() {
        int[][] dag = {
            {0,1,0},
            {0,0,1},
            {0,0,0},
        };
        DAGShortestPath d = new DAGShortestPath();
        int[] topo = {0,1,2};
        int[] sp = d.shortestPath(dag,0,topo);
        int[] path = d.reconstructPath(dag,0,2,sp,topo,false);
        assertArrayEquals(new int[]{0,1,2}, path);
    }
}
