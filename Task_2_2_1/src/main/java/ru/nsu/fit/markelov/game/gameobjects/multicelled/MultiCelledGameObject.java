package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Collection;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * MultiCelledGameObject is used for representing a multi-cell game object in the World.
 *
 * @author Oleg Markelov
 */
public abstract class MultiCelledGameObject {

    /**
     * Returns whether specified cell collides with any cell of this game object.
     *
     * @param cell cell to collide with.
     * @return whether specified cell collides with any cell of this game object.
     * @throws IllegalInputException if 'cell' parameter is null. It can also be rethrown from Cell.
     */
    public boolean isColliding(Cell cell) throws IllegalInputException {
        requireNonNull(cell);

        for (Cell gameObjectCell : getGameObjectCells()) {
            if (gameObjectCell.hasSamePosition(cell)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Changes the type of every cell of this game object.
     *
     * @param type cell type.
     * @throws IllegalInputException if 'type' parameter is null.
     */
    protected void changeObjectCellsType(Cell.Type type) throws IllegalInputException {
        requireNonNull(type);

        for (Cell cell : getGameObjectCells()) {
            cell.changeType(type);
        }
    }

    /**
     * Returns collection of game object cells.
     *
     * @return collection of game object cells.
     */
    protected abstract Collection<Cell> getGameObjectCells();
}
