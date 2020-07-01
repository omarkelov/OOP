package ru.nsu.fit.markelov.objects;

import java.util.Date;

public class ControlPoint implements Comparable<ControlPoint> {
    private String name;
    private Date date;
    private int pointsForFive;
    private int pointsForFour;
    private int pointsForThree;

    @Override
    public int compareTo(ControlPoint controlPoint) {
        return date.compareTo(controlPoint.getDate());
    }

    /**
     * Returns name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     */
    public void setName(String name) {
        this.name = name;
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
     * Returns pointsForFive.
     *
     * @return pointsForFive.
     */
    public int getPointsForFive() {
        return pointsForFive;
    }

    /**
     * Sets pointsForFive.
     */
    public void setPointsForFive(int pointsForFive) {
        this.pointsForFive = pointsForFive;
    }

    /**
     * Returns pointsForFour.
     *
     * @return pointsForFour.
     */
    public int getPointsForFour() {
        return pointsForFour;
    }

    /**
     * Sets pointsForFour.
     */
    public void setPointsForFour(int pointsForFour) {
        this.pointsForFour = pointsForFour;
    }

    /**
     * Returns pointsForThree.
     *
     * @return pointsForThree.
     */
    public int getPointsForThree() {
        return pointsForThree;
    }

    /**
     * Sets pointsForThree.
     */
    public void setPointsForThree(int pointsForThree) {
        this.pointsForThree = pointsForThree;
    }
}
