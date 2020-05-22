package ru.nsu.fit.markelov.game.gameobjects.singlecelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.game.Cell.Type.FOOD;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Food is used for representing a food in the World.
 *
 * @author Oleg Markelov
 */
public class Food {

    private final Cell cell;

    /**
     * Creates new Food with specified cell.
     *
     * @param cell cell.
     * @throws IllegalInputException if 'cell' parameter is null. It can also be rethrown from Cell.
     */
    public Food(Cell cell) throws IllegalInputException {
        this.cell = requireNonNull(cell);

        cell.changeType(FOOD);
    }

    /**
     * Returns whether specified cell collides with the cell of this food.
     *
     * @param cell cell to collide with.
     * @return whether specified cell collides with the cell of this food.
     * @throws IllegalInputException if 'cell' parameter is null. It can also be rethrown from Cell.
     */
    public boolean isColliding(Cell cell) throws IllegalInputException {
        requireNonNull(cell);
        return this.cell.hasSamePosition(cell);
    }
}
