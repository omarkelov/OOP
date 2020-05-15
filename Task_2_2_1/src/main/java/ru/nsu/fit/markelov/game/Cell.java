package ru.nsu.fit.markelov.game;

public class Cell {

    public enum Type { EMPTY, SNAKE, SNAKE_HEAD, DEAD_SNAKE, OBSTACLE, FOOD }

    private Type type;
    private int row;
    private int column;

    private WorldObserver worldObserver;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void changeType(Type type) {
        this.type = type;
        worldObserver.onCellChanged(this);
    }

    public boolean hasSamePosition(Cell cell) {
        return row == cell.row && column == cell.column;
    }

    public Type getType() {
        return type;
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

    public void setWorldObserver(WorldObserver worldObserver) {
        this.worldObserver = worldObserver;
    }
}
