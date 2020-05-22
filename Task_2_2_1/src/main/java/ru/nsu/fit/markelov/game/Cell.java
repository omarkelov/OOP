package ru.nsu.fit.markelov.game;

import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Cell class is used for representing a single part of a game object on the playing field. It holds
 * the information of its position and type.
 *
 * @author Oleg Markelov
 */
public class Cell {

    /**
     * Type enum is used for representing the type of this cell.
     */
    public enum Type { EMPTY, SNAKE, SNAKE_HEAD, DEAD_SNAKE, OBSTACLE, FOOD }

    private final Vector2 position;
    private Type type;

    private final WorldObserver worldObserver;

    /**
     * Creates new Cell with specified position.
     *
     * WorldObserver is used for firing 'onCellChanged()' event.
     *
     * @param position      cell position.
     * @param worldObserver world observer.
     * @throws IllegalInputException if one of the input parameters is null.
     */
    public Cell(Vector2 position, WorldObserver worldObserver) throws IllegalInputException {
        this.position = requireNonNull(position);
        this.worldObserver = requireNonNull(worldObserver);
    }

    /**
     * Changes the cell type and fires 'WorldObserver.onCellChanged()' event.
     *
     * @param type cell type.
     * @throws IllegalInputException from 'WorldObserver.onCellChanged()'.
     */
    public void changeType(Type type) throws IllegalInputException {
        this.type = type;
        worldObserver.onCellChanged(this);
    }

    /**
     * Returns whether specified cell has the same position as this cell.
     *
     * @param cell cell to compare with.
     * @return whether specified cell has the same position as this cell.
     * @throws IllegalInputException if 'cell' parameter is null.
     */
    public boolean hasSamePosition(Cell cell) throws IllegalInputException {
        requireNonNull(cell);
        return position.equals(cell.getPosition());
    }

    /**
     * Returns cell position.
     *
     * @return cell position.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Returns cell type.
     *
     * @return cell type.
     */
    public Type getType() {
        return type;
    }
}
