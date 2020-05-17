package ru.nsu.fit.markelov.game;

import ru.nsu.fit.markelov.util.Vector2;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class Cell {

    public enum Type { EMPTY, SNAKE, SNAKE_HEAD, DEAD_SNAKE, OBSTACLE, FOOD }

    private final Vector2 position;
    private Type type;

    private final WorldObserver worldObserver;

    public Cell(Vector2 position, WorldObserver worldObserver) throws IllegalInputException {
        this.position = requireNonNull(position);
        this.worldObserver = requireNonNull(worldObserver);
    }

    public void changeType(Type type) throws IllegalInputException {
        this.type = type;
        worldObserver.onCellChanged(this);
    }

    public boolean hasSamePosition(Cell cell) throws IllegalInputException {
        requireNonNull(cell);
        return position.equals(cell.getPosition());
    }

    public Vector2 getPosition() {
        return position;
    }

    public Type getType() {
        return type;
    }
}
