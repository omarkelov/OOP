package ru.nsu.fit.markelov.managers.levelmanager;

import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Level class is used for holding level data.
 *
 * @author Oleg Markelov
 */
public class Level implements Validatable<Level> {

    private int width;
    private int height;

    private int goalScore;
    private int delayBetweenMoves;

    private int emptyCellsCount;

    private final Deque<Vector2> snakeCellPositions;
    private final List<Vector2> obstacleCellPositions;

    /**
     * Creates new Level with empty data.
     */
    public Level() {
        snakeCellPositions = new LinkedList<>();
        obstacleCellPositions = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Level validate() throws IllegalInputException {
        if (width < 2 ||
            height < 2 ||
            goalScore < 2 ||
            emptyCellsCount < 3 ||
            emptyCellsCount < goalScore ||
            delayBetweenMoves < 20 ||
            snakeCellPositions.size() != 1
        ) {
            throw new IllegalInputException();
        }

        return this;
    }

    /**
     * Returns level width.
     *
     * @return level width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns level height.
     *
     * @return level height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns goal score.
     *
     * @return goal score.
     */
    public int getGoalScore() {
        return goalScore;
    }

    /**
     * Sets goal score.
     *
     * @param goalScore goal score.
     */
    public void setGoalScore(int goalScore) {
        this.goalScore = goalScore;
    }

    /**
     * Returns delay between moves.
     *
     * @return delay between moves.
     */
    public int getDelayBetweenMoves() {
        return delayBetweenMoves;
    }

    /**
     * Sets delay between moves.
     *
     * @param delayBetweenMoves delay between moves.
     */
    public void setDelayBetweenMoves(int delayBetweenMoves) {
        this.delayBetweenMoves = delayBetweenMoves;
    }

    /**
     * Sets empty cells count.
     *
     * @param emptyCellsCount empty cells count.
     */
    public void setEmptyCellsCount(int emptyCellsCount) {
        this.emptyCellsCount = emptyCellsCount;
    }

    /**
     * Returns snake cell positions.
     *
     * @return snake cell positions.
     */
    public Deque<Vector2> getSnakeCellPositions() {
        return snakeCellPositions;
    }

    /**
     * Adds new cell position to snake cell positions.
     *
     * @param vector2 cell position.
     * @throws IllegalInputException if 'vector2' parameter is null.
     */
    public void addSnakeCellPosition(Vector2 vector2) throws IllegalInputException {
        snakeCellPositions.addFirst(requireNonNull(vector2));
    }

    /**
     * Returns obstacle cell positions.
     *
     * @return obstacle cell positions.
     */
    public List<Vector2> getObstacleCellPositions() {
        return obstacleCellPositions;
    }

    /**
     * Adds new cell position to obstacle cell positions.
     *
     * @param vector2 cell position.
     * @throws IllegalInputException if 'vector2' parameter is null.
     */
    public void addObstacleCellPosition(Vector2 vector2) throws IllegalInputException {
        obstacleCellPositions.add(requireNonNull(vector2));
    }
}
