package ru.nsu.fit.markelov;

public class Edge {

    private int mFrom;
    private int mTo;
    private int mWeight;

    /**
     * Creates a new Edge.
     *
     * @param from   the source node index
     * @param to     the sink node index
     * @param weight the weight of this edge
     */
    public Edge(int from, int to, int weight) {
        mFrom = from;
        mTo = to;
        mWeight = weight;
    }

    /**
     * @return the source node index
     */
    public int getFrom() {
        return mFrom;
    }

    /**
     * @return the sink node index
     */
    public int getTo() {
        return mTo;
    }

    /**
     * @return the weight of this edge
     */
    public int getWeight() {
        return mWeight;
    }
}
