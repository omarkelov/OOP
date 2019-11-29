package ru.nsu.fit.markelov;

import java.util.ConcurrentModificationException;

public class FloydWarshall implements ShortestPath {

    // cannot be higher for algorithm correction
    private static int UNREACHABLE_WEIGHT = Integer.MAX_VALUE / 2;

    private int[][] matrix;
    private Graph graph;
    private int initialEdgesAmount;

    /**
     * Creates a new FloydWarshall for a specified graph.
     * <p>
     * Generates the adjacency matrix of the graph to operate with
     * and then calculates all the shortest paths possible.
     * <p>
     * The Algorithm takes O (n^2) memory and O (n^3) time for calculation
     * where n is maximal node index in this graph. But once it's finished,
     * it takes O (1) to get any shortest path.
     *
     * @param graph a graph to get the data from
     */
    public FloydWarshall(Graph graph) {
        matrix = new int[graph.getMaxNodeIndex()+1][graph.getMaxNodeIndex()+1];
        this.graph = graph;
        initialEdgesAmount = graph.getEdgesAmount();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = UNREACHABLE_WEIGHT;
            }
        }

        for (Edge edge : graph.getEdges()) {
            matrix[edge.getFrom()][edge.getTo()] = edge.getWeight();
        }

        for (int k = 1; k < matrix.length; k++) {
            for (int i = 1; i < matrix.length; i++) {
                for (int j = 1; j < matrix.length; j++) {
                    matrix[i][j] = Integer.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getShortestPath(int start, int finish) throws ConcurrentModificationException, IllegalArgumentException {
        if (initialEdgesAmount != graph.getEdgesAmount()) {
            throw new ConcurrentModificationException("Cannot get the shortest path, as " +
                    "the graph was modified");
        }

        if (start < 1 || start > matrix.length || finish < 1 || finish > matrix.length) {
            throw new IllegalArgumentException("The starting or ending node index cannot be " +
                    "less than one or higher than the maximal node index");
        }

        return (matrix[start][finish] != UNREACHABLE_WEIGHT) ? matrix[start][finish] : -1;
    }
}
