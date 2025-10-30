package graph.topo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TopoSortTest {
    @Test
    public void testSimpleDAG() {
        // 0->1->2
        int[][] dag = {
            {0,1,0}, {0,0,1}, {0,0,0}
        };
        TopoSort ts = new TopoSort();
        int[] order = ts.kahnSort(dag);
        assertEquals(3, order.length);
        assertTrue(order[0]==0 && order[2]==2);
    }
    @Test
    public void testDisconnectedDAG() {
        // 0->1, 2 isolated
        int[][] dag = {
            {0,1,0}, {0,0,0}, {0,0,0}
        };
        TopoSort ts = new TopoSort();
        int[] order = ts.kahnSort(dag);
        assertEquals(3, order.length);
    }
    @Test
    public void testCycleReturnsPartialOrder() {
        // 0->1->2->0 (cycle)
        int[][] dag = {
            {0,1,0}, {0,0,1}, {1,0,0}
        };
        TopoSort ts = new TopoSort();
        int[] order = ts.kahnSort(dag);
        assertTrue(order.length < 3); // topological sort impossible on cyclic
    }
}
