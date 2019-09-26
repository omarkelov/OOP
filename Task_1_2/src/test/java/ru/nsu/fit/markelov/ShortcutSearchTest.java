package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class ShortcutSearchTest {
    @Test
    public void test1() {
        Graph graph = new Graph(5);
        graph.addEdge(4, 2, 2);
        graph.addEdge(1, 4, 8);
        graph.addEdge(2, 3, 6);
        graph.addEdge(1, 5, 7);
        graph.addEdge(2, 1, 3);
        graph.addEdge(4, 3, 9);

        ShortcutSearch shortcutSearch = new ShortcutSearch(graph);

        Assert.assertEquals(8, shortcutSearch.getFloydWarshallShortcut(4, 3));
        Assert.assertEquals(9, shortcutSearch.getFloydWarshallShortcut(1, 3));
    }

    @Test
    public void test2() {
        Graph graph = new Graph(new int[][] {
                new int[] {0, 3, 0, 8, 7},
                new int[] {3, 0, 6, 2, 0},
                new int[] {0, 6, 0, 9, 0},
                new int[] {8, 2, 9, 0, 0},
                new int[] {7, 0, 0, 0, 0},
        });

        ShortcutSearch shortcutSearch = new ShortcutSearch(graph);

        Assert.assertEquals(8, shortcutSearch.getFloydWarshallShortcut(4, 3));
        Assert.assertEquals(9, shortcutSearch.getFloydWarshallShortcut(1, 3));
        Assert.assertEquals(-1, shortcutSearch.getFloydWarshallShortcut(12345, 3));
    }
}
