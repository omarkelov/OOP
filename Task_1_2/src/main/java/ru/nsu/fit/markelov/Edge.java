package ru.nsu.fit.markelov;

public class Edge {

    private int from;
    private int to;
    private int weight;

    /**
     * Creates a new Edge.
     *
     * @param from   the source node index
     * @param to     the sink node index
     * @param weight the weight of this edge
     */
    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * @return the source node index
     */
    public int getFrom() {
        return from;
    }

    /**
     * @return the sink node index
     */
    public int getTo() {
        return to;
    }

    /**
     * @return the weight of this edge
     */
    public int getWeight() {
        return weight;
    }
}
