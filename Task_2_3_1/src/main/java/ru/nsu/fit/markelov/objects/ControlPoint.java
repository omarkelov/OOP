package ru.nsu.fit.markelov.objects;

import java.util.Date;

public class ControlPoint implements Comparable<ControlPoint> {
    private String name;
    private Date date;

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
}