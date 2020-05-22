package ru.nsu.fit.markelov.util;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Vector2 class is used to represent a vector of integers on the plane with x- and y-axis.
 *
 * @author Oleg Markelov
 */
public class Vector2 {

    private int x;
    private int y;

    /**
     * Creates new Vector2 with specified x- and y-axis values.
     *
     * @param x x-axis value.
     * @param y y-axis value.
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns whether specified vector equals to this vector. Equality is checked on each axis.
     *
     * @param vector2 vector to compare with.
     * @return whether specified vector equals to this vector.
     * @throws IllegalInputException if vector2 parameter is null.
     */
    public boolean equals(Vector2 vector2) throws IllegalInputException {
        requireNonNull(vector2);
        return x == vector2.x && y == vector2.y;
    }

    /**
     * Returns x-axis value.
     *
     * @return x-axis value.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets x-axis value.
     *
     * @param x x-axis value.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns y-axis value.
     *
     * @return y-axis value.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets y-axis value.
     *
     * @param y x-axis value.
     */
    public void setY(int y) {
        this.y = y;
    }
}
