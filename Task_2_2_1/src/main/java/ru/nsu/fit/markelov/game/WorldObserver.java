package ru.nsu.fit.markelov.game;

public interface WorldObserver {
    void onFoodEaten();
    void onSnakeDeath();
    void onCellChanged(Cell cell);
}
