package ru.nsu.fit.markelov.objects;

import java.util.Date;

public class ControlPoint implements Comparable<ControlPoint> {
    private String name;
    private Date date;
    private int pointsForExc;
    private int pointsForGood;
    private int pointsForSat;

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
    public int getPointsForExc() {
        return pointsForExc;
    }

    /**
     * Sets pointsForFive.
     */
    public void setPointsForExc(int pointsForExc) {
        this.pointsForExc = pointsForExc;
    }

    /**
     * Returns pointsForFour.
     *
     * @return pointsForFour.
     */
    public int getPointsForGood() {
        return pointsForGood;
    }

    /**
     * Sets pointsForFour.
     */
    public void setPointsForGood(int pointsForGood) {
        this.pointsForGood = pointsForGood;
    }

    /**
     * Returns pointsForThree.
     *
     * @return pointsForThree.
     */
    public int getPointsForSat() {
        return pointsForSat;
    }

    /**
     * Sets pointsForThree.
     */
    public void setPointsForSat(int pointsForSat) {
        this.pointsForSat = pointsForSat;
    }
}
