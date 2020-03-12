package sample.java.gameobjects;

import javafx.scene.layout.Region;

public class Cell {

    private int row;
    private int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public Cell setRow(int row) {
        this.row = row;

        return this;
    }

    public int getColumn() {
        return column;
    }

    public Cell setColumn(int column) {
        this.column = column;

        return this;
    }

    public void draw(Region[][] regions, String className) {
        erase(regions);
        regions[row][column].getStyleClass().add(className);
    }

    public void erase(Region[][] regions) {
        regions[row][column].getStyleClass().removeIf(className -> !className.equals("dark"));
    }

    public boolean hasSamePosition(Cell cell) {
        return row == cell.row && column == cell.column;
    }
}
