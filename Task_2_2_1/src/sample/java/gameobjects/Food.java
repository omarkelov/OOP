package sample.java.gameobjects;

import javafx.scene.layout.Region;

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
