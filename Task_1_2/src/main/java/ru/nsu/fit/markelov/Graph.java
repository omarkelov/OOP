package ru.nsu.fit.markelov;

import java.util.ArrayList;

public class Graph {

    private ArrayList<Edge> edges;
    private boolean directed;
    private int maxNodeIndex;

    /**
     * Creates an empty undirected Graph (with no edges).
     */
    public Graph() {
        this(false);
    }

    /**
     * Creates an empty directed or undirected Graph (with no edges).
     *
     * @param directed a flag to determine whether a Graph is directed
     */
    public Graph(boolean directed) {
        edges = new ArrayList<>();
        this.directed = directed;
        maxNodeIndex = 0;
    }

    /**
     * Adds an edge to a Graph.
     * <p>
     * If Graph is undirected, reversed edge is also added.
     * <p>
     * An IllegalArgumentException is thrown if there is no such
     * node in the graph.
     *
     * @param  fromNode                 source node of edge
     * @param  toNode                   sink node of edge
     * @param  weight                   a weight of edge
     * @throws IllegalArgumentException if node number is less than one.
     */
    public void addEdge(int fromNode, int toNode, int weight) {
        verifyNode(fromNode, "source");
        verifyNode(toNode, "sink");

        edges.add(new Edge(fromNode, toNode, weight));
        if (!directed) {
            edges.add(new Edge(toNode, fromNode, weight));
        }

        maxNodeIndex = Math.max(maxNodeIndex, Math.max(fromNode, toNode));
    }

    /**
     * @return edges as Iterable interface
     * @see    Iterable
     */
    public Iterable<Edge> getEdges() {
        return edges;
    }

    /**
     * @return maximal node index in this graph
     */
    public int getMaxNodeIndex() {
        return maxNodeIndex;
    }

    private void verifyNode(int node, String type) {
        if (node < 1) {
            throw new IllegalArgumentException("'" + node + "' is invalid value for " + type + " node parameter");
        }
    }
}
