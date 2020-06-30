package ru.nsu.fit.markelov.app;

import java.util.Date;

public class TaskPoints {
    private int points;
    private Date date;
    private String message;

    /**
     * Returns points.
     *
     * @return points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets points.
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns date.
     *
     * @return date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns message.
     *
     * @return message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
