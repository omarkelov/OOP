package ru.nsu.fit.markelov.game.gameobjects.singlecelled;

import javafx.scene.layout.Region;
import ru.nsu.fit.markelov.game.Cell;

public class Food {

    private static final String FOOD_CLASS_NAME = "food";

    private Cell cell;

    public Food(Region[][] regions, Cell cell) {
        this.cell = cell;

        cell.draw(regions, FOOD_CLASS_NAME);
    }

    public boolean isColliding(Cell cell) {
        return this.cell.hasSamePosition(cell);
    }
}
