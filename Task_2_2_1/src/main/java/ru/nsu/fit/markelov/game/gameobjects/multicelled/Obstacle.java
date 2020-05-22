package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Collection;
import java.util.List;

import static ru.nsu.fit.markelov.game.Cell.Type.OBSTACLE;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Obstacle is used for representing an obstacle in the World.
 *
 * @author Oleg Markelov
 */
public class Obstacle extends MultiCelledGameObject {

    private final List<Cell> obstacleCells;

    /**
     * Creates new Obstacle with specified obstacle cells.
     *
     * @param obstacleCells obstacle cells.
     * @throws IllegalInputException if 'obstacleCells' parameter is null. It can also be rethrown
     *                               from Cell.
     */
    public Obstacle(List<Cell> obstacleCells) throws IllegalInputException {
        this.obstacleCells = requireNonNull(obstacleCells);

        changeObjectCellsType(OBSTACLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Collection<Cell> getGameObjectCells() {
        return obstacleCells;
    }
}
