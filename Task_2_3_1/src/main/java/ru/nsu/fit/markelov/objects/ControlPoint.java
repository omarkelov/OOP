package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Date;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_EMPTY;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_POSITIVE;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class ControlPoint implements Comparable<ControlPoint>, Validatable<ControlPoint> {
    private String name;
    private Date date;
    private Integer pointsForExc;
    private Integer pointsForGood;
    private Integer pointsForSat;

    @Override
    public int compareTo(ControlPoint controlPoint) {
        return date.compareTo(controlPoint.getDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ControlPoint validate() throws IllegalInputException {
        requireNonNull(name, "ControlPoint name " + NOT_NULL);
        requireNonNull(date, "ControlPoint date " + NOT_NULL);
        requireNonNull(pointsForExc, "ControlPoint points for Exc " + NOT_NULL);
        requireNonNull(pointsForGood, "ControlPoint points for good " + NOT_NULL);
        requireNonNull(pointsForSat, "ControlPoint points for Sat " + NOT_NULL);

        if (name.isEmpty()) {
            throw new IllegalInputException("ControlPoint name " + NOT_EMPTY);
        }

        if (pointsForExc <= 0) {
            throw new IllegalInputException("ControlPoint points for Exc " + NOT_POSITIVE);
        }

        if (pointsForGood <= 0) {
            throw new IllegalInputException("ControlPoint points for good " + NOT_POSITIVE);
        }

        if (pointsForSat <= 0) {
            throw new IllegalInputException("ControlPoint points for Sat " + NOT_POSITIVE);
        }

        return this;
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
     * Returns pointsForExc.
     *
     * @return pointsForExc.
     */
    public Integer getPointsForExc() {
        return pointsForExc;
    }

    /**
     * Sets pointsForExc.
     */
    public void setPointsForExc(Integer pointsForExc) {
        this.pointsForExc = pointsForExc;
    }

    /**
     * Returns pointsForGood.
     *
     * @return pointsForGood.
     */
    public Integer getPointsForGood() {
        return pointsForGood;
    }

    /**
     * Sets pointsForGood.
     */
    public void setPointsForGood(Integer pointsForGood) {
        this.pointsForGood = pointsForGood;
    }

    /**
     * Returns pointsForSat.
     *
     * @return pointsForSat.
     */
    public Integer getPointsForSat() {
        return pointsForSat;
    }

    /**
     * Sets pointsForSat.
     */
    public void setPointsForSat(Integer pointsForSat) {
        this.pointsForSat = pointsForSat;
    }
}
