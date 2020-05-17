package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.Collection;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public abstract class MultiCelledGameObject {

    public boolean isColliding(Cell cell) throws IllegalInputException {
        requireNonNull(cell);

        for (Cell gameObjectCell : getGameObjectCells()) {
            if (gameObjectCell.hasSamePosition(cell)) {
                return true;
            }
        }

        return false;
    }

    protected void changeObjectCellsType(Cell.Type type) throws IllegalInputException {
        requireNonNull(type);

        for (Cell cell : getGameObjectCells()) {
            cell.changeType(type);
        }
    }

    protected abstract Collection<Cell> getGameObjectCells();
}
