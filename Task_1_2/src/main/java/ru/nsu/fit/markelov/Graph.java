package ru.nsu.fit.markelov;

import java.util.Iterator;

public class Graph {

    private Stack<Edge> mEdges;
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
        mEdges = new Stack<>();
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

        mEdges.push(new Edge(fromNode, toNode, weight));
        if (!mDirected) {
            mEdges.push(new Edge(toNode, fromNode, weight));
        }

        mMaxNodeIndex = Math.max(mMaxNodeIndex, Math.max(fromNode, toNode));
    }

    /**
     * @return stack of edges
     */
    public Stack<Edge> getEdges() {
        return mEdges;
    }

    /**
     * @return standard iterator over graph's edges
     * @see    Iterator
     */
    public Iterator getEdgesIterator() {
        return mEdges.iterator();
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
