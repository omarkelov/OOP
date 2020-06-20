package ru.nsu.fit.markelov.objects;

import java.util.Date;

public class LessonObject implements Comparable<LessonObject> {
    private Date date;

    @Override
    public int compareTo(LessonObject lessonObject) {
        return date.compareTo(lessonObject.getDate());
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
