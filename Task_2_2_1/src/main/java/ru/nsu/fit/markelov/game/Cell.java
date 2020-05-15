package ru.nsu.fit.markelov.game;

import ru.nsu.fit.markelov.util.Vector2;

public class Cell {

    public enum Type { EMPTY, SNAKE, SNAKE_HEAD, DEAD_SNAKE, OBSTACLE, FOOD }

    private Vector2 position;
    private Type type;

    private WorldObserver worldObserver;

    public Cell(Vector2 position, WorldObserver worldObserver) {
        this.position = position;
        this.worldObserver = worldObserver;
    }

    public void changeType(Type type) {
        this.type = type;
        worldObserver.onCellChanged(this);
    }

    public boolean hasSamePosition(Cell cell) {
        return position.equals(cell.getPosition());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }
}
