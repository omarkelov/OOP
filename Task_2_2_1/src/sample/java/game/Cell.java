package sample.java.game;

import javafx.scene.layout.Region;

public class Cell {

    public static final String DARK_BACKGROUND_CLASS_NAME = "dark-background";

    private int row;
    private int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void draw(Region[][] regions, String className) {
        erase(regions);
        regions[row][column].getStyleClass().add(className);
    }

    public void erase(Region[][] regions) {
        regions[row][column].getStyleClass().removeIf(className -> !className.equals(DARK_BACKGROUND_CLASS_NAME));
    }

    public boolean hasSamePosition(Cell cell) {
        return row == cell.row && column == cell.column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
