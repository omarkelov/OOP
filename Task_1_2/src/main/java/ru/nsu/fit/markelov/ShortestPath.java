package ru.nsu.fit.markelov;

public interface ShortestPath {
    /**
     * Returns the shortest path between starting and ending nodes in Graph.
     *
     * @param  start                    starting node
     * @param  finish                   ending node
     * @return                          the weight of the shortest path. -1 is returned
     *                                  if there is no required path in the graph.
     * @throws IllegalArgumentException if starting or ending node is less than one or
     *                                  higher than maximal node.
     */
    int getShortestPath(int start, int finish);
}
