package ru.nsu.fit.markelov.game.gameobjects.singlecelled;

import ru.nsu.fit.markelov.game.Cell;

import static ru.nsu.fit.markelov.game.Cell.Type.FOOD;

public class Food {

    private Cell cell;

    public Food(Cell cell) {
        this.cell = cell;

        cell.changeType(FOOD);
    }

    public boolean isColliding(Cell cell) {
        return this.cell.hasSamePosition(cell);
    }
}
