package ru.nsu.fit.markelov;

public class FloydWarshall implements ShortestPath {

    // cannot be higher for algorithm correction
    private static int UNREACHABLE_WEIGHT = Integer.MAX_VALUE / 2;

    private int mMatrix[][];

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
        mMatrix = new int[graph.getMaxNodeIndex()+1][graph.getMaxNodeIndex()+1];

        for (int i = 0; i < mMatrix.length; i++) {
            for (int j = 0; j < mMatrix.length; j++) {
                mMatrix[i][j] = UNREACHABLE_WEIGHT;
            }
        }

        for (Edge edge : graph.getEdges()) {
            mMatrix[edge.getFrom()][edge.getTo()] = edge.getWeight();
        }

        for (int k = 1; k < mMatrix.length; k++) {
            for (int i = 1; i < mMatrix.length; i++) {
                for (int j = 1; j < mMatrix.length; j++) {
                    mMatrix[i][j] = Integer.min(mMatrix[i][j], mMatrix[i][k] + mMatrix[k][j]);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getShortestPath(int start, int finish) {
        if (start < 1 || start > mMatrix.length || finish < 1 || finish > mMatrix.length) {
            throw new IllegalArgumentException(
                    "The starting or ending node index cannot be " +
                    "less than one or higher than the maximal node index"
            );
        }

        return (mMatrix[start][finish] != UNREACHABLE_WEIGHT) ? mMatrix[start][finish] : -1;
    }
}
