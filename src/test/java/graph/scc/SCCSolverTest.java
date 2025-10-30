package graph.scc;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SCCSolverTest {
    @Test
    public void testSimpleCycle() {
        // 0->1->2->0
        int[][] adj = {
            {0,1,0}, {0,0,1}, {1,0,0}
        };
        SCCSolver scc = new SCCSolver();
        List<List<Integer>> result = scc.findSCCs(adj);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains(0) && result.get(0).contains(1) && result.get(0).contains(2));
    }

    @Test
    public void testDAG() {
        // 0->1->2, 2->3
        int[][] adj = {
            {0,1,0,0}, {0,0,1,0}, {0,0,0,1}, {0,0,0,0}
        };
        SCCSolver scc = new SCCSolver();
        List<List<Integer>> res = scc.findSCCs(adj);
        assertEquals(4, res.size());
        for (List<Integer> c : res) assertEquals(1,c.size());
    }

    @Test
    public void testIsolatedNode() {
        // 0->1, 2 isolated
        int[][] adj = {
            {0,1,0}, {0,0,0}, {0,0,0}
        };
        SCCSolver scc = new SCCSolver();
        List<List<Integer>> res = scc.findSCCs(adj);
        assertEquals(3, res.size());
        boolean has2 = false;
        for (List<Integer> c : res) if (c.size()==1 && c.contains(2)) has2=true;
        assertTrue(has2);
    }

    @Test
    public void testEmptyGraph() {
        int[][] adj = {};
        SCCSolver scc = new SCCSolver();
        List<List<Integer>> res = scc.findSCCs(adj);
        assertEquals(0, res.size());
    }

    @Test
    public void testSingleVertex() {
        int[][] adj = {{0}};
        SCCSolver scc = new SCCSolver();
        List<List<Integer>> res = scc.findSCCs(adj);
        assertEquals(1, res.size());
        assertEquals(1, res.get(0).size());
        assertEquals(0, res.get(0).get(0));
    }
}
