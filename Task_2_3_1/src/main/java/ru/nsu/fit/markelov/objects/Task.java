package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Date;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_EMPTY;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_POSITIVE;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class Task implements Comparable<Task>, Validatable<Task> {
    private String id;
    private String name;
    private Integer points;
    private Date deadline;

    @Override
    public int compareTo(Task task) {
        return id.compareTo(task.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Task validate() throws IllegalInputException {
        requireNonNull(id, "Task id " + NOT_NULL);
        requireNonNull(name, "Task name " + NOT_NULL);
        requireNonNull(points, "Task points " + NOT_NULL);
        requireNonNull(deadline, "Task deadline " + NOT_NULL);

        if (id.isEmpty()) {
            throw new IllegalInputException("Task id " + NOT_EMPTY);
        }

        if (name.isEmpty()) {
            throw new IllegalInputException("Task name " + NOT_EMPTY);
        }

        if (points <= 0) {
            throw new IllegalInputException("Task points " + NOT_POSITIVE);
        }

        return this;
    }

    /**
     * Returns id.
     *
     * @return id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     */
    public void setId(String id) {
        this.id = id;
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
     * Returns points.
     *
     * @return points.
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * Sets points.
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * Returns deadline.
     *
     * @return deadline.
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * Sets deadline.
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
