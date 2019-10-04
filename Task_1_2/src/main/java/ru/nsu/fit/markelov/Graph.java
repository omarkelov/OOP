package ru.nsu.fit.markelov;

import java.util.ArrayList;

public class Graph {

    private ArrayList<Edge> mEdges;
    private boolean mDirected;
    private int mMaxNodeIndex;

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
        mEdges = new ArrayList<>();
        mDirected = directed;
        mMaxNodeIndex = 0;
    }

    /**
     * Adds an edge to a Graph.
     * <p>
     * If Graph is undirected, reversed edge is also added.
     * <p>
     * An IllegalArgumentException is thrown if there is no such
     * node in the graph.
     *
     * @param  fromNode source node of edge
     * @param  toNode   sink node of edge
     * @param  weight   a weight of edge
     * @throws IllegalArgumentException if node number is less than one.
     */
    public void addEdge(int fromNode, int toNode, int weight) {
        verifyNode(fromNode, "source");
        verifyNode(toNode, "sink");

        mEdges.add(new Edge(fromNode, toNode, weight));
        if (!mDirected) {
            mEdges.add(new Edge(toNode, fromNode, weight));
        }

        mMaxNodeIndex = Math.max(mMaxNodeIndex, Math.max(fromNode, toNode));
    }

    /**
     * @return edges as Iterable interface
     * @see    Iterable
     */
    public Iterable<Edge> getEdges() {
        return mEdges;
    }

    /**
     * @return maximal node index in this graph
     */
    public int getMaxNodeIndex() {
        return mMaxNodeIndex;
    }

    private void verifyNode(int node, String type) {
        if (node < 1) {
            throw new IllegalArgumentException("'" + node + "' is invalid value for " + type + " node parameter");
        }
    }
}
