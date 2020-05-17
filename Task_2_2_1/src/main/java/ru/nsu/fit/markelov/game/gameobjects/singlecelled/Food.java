package ru.nsu.fit.markelov.game.gameobjects.singlecelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.game.Cell.Type.FOOD;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class Food {

    private Cell cell;

    public Food(Cell cell) throws IllegalInputException {
        this.cell = requireNonNull(cell);

        cell.changeType(FOOD);
    }

    public boolean isColliding(Cell cell) throws IllegalInputException {
        requireNonNull(cell);
        return this.cell.hasSamePosition(cell);
    }
}
