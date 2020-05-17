package ru.nsu.fit.markelov.game;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;

public interface WorldObserver {
    void onFoodEaten();
    void onSnakeDeath();
    void onCellChanged(Cell cell) throws IllegalInputException;
}
