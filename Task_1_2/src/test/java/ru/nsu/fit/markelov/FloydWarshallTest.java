package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class FloydWarshallTest {
    @Test
    public void test() {
        Graph graph = new Graph();
        graph.addEdge(4, 2, 2);
        graph.addEdge(1, 4, 8);
        graph.addEdge(2, 3, 6);
        graph.addEdge(1, 5, 7);
        graph.addEdge(2, 1, 3);
        graph.addEdge(4, 3, 9);

        FloydWarshall floydWarshall = new FloydWarshall(graph);

        Assert.assertEquals(8, floydWarshall.getShortestPath(4, 3));
        Assert.assertEquals(9, floydWarshall.getShortestPath(1, 3));
    }
}
