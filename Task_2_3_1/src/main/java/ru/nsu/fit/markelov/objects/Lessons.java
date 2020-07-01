package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Set;
import java.util.TreeSet;

public class Lessons implements Validatable<Lessons> {
    private Set<Lesson> lessons = new TreeSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Lessons validate() throws IllegalInputException {
        for (Lesson lesson : lessons) {
            lesson.validate();
        }

        return this;
    }

    /**
     * Returns lessons.
     *
     * @return lessons.
     */
    public Set<Lesson> getLessons() {
        return lessons;
    }

    /**
     * Sets lessons.
     */
    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }
}
