package ru.nsu.fit.markelov.game.gameobjects.multicelled;

import ru.nsu.fit.markelov.game.Cell;

import java.util.Collection;

public abstract class MultiCelledGameObject {

    public boolean isColliding(Cell cell) {
        return getGameObjectCells().stream().anyMatch(objectCell -> objectCell.hasSamePosition(cell));
    }

    protected void changeObjectCellsType(Cell.Type type) {
        for (Cell cell : getGameObjectCells()) {
            cell.changeType(type);
        }
    }

    protected abstract Collection<Cell> getGameObjectCells();
}
