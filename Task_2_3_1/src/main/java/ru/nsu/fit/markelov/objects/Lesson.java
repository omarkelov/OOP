package ru.nsu.fit.markelov.objects;

import java.util.Date;

public class Lesson implements Comparable<Lesson> {
    private Date date;

    @Override
    public int compareTo(Lesson lesson) {
        return date.compareTo(lesson.getDate());
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
