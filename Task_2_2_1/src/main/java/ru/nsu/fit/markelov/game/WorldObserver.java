package ru.nsu.fit.markelov.game;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;

/**
 * WorldObserver interface is used for reacting on the events occurred in the game world.
 *
 * @author Oleg Markelov
 */
public interface WorldObserver {
    /**
     * An event occurring when the food is eaten.
     */
    void onFoodEaten();

    /**
     * An event occurring when the snake dies.
     */
    void onSnakeDeath();

    /**
     * An event occurring when the cell changes.
     */
    void onCellChanged(Cell cell) throws IllegalInputException;
}
