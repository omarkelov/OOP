package ru.nsu.fit.markelov;

public interface FloydWarshall {
    int UNREACHABLE_WEIGHT = 1000000000;

    /**
     * Calculates the shortcut between starting and ending nodes in Graph
     * using Floyd-Warshall algorithm.
     * <p>
     * Should be used with a Graph that uses adjacency matrix for storing the weights of edges.
     *
     * @param  start  starting node
     * @param  finish ending node
     * @return        the weight of a shortcut. -1 is returned if there is no required path in the graph.
     */
    int getFloydWarshallShortcut(int start, int finish);
}
