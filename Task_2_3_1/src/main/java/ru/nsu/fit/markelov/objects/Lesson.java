package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Date;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Lesson class is used for setting and getting the lesson data.
 *
 * @author Oleg Markelov
 */
public class Lesson implements Comparable<Lesson>, Validatable<Lesson> {
    private Date date;

    /**
     * Compares this object with the specified object based on their dates.
     *
     * @param lesson the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     *         equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Lesson lesson) {
        return date.compareTo(lesson.getDate());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lesson validate() throws IllegalInputException {
        requireNonNull(date, "Lesson date " + NOT_NULL);

        return this;
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
