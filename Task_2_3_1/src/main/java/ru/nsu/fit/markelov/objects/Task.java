package ru.nsu.fit.markelov.objects;

import java.util.Date;

public class Task implements Comparable<Task> {
    private String id;
    private String name;
    private int points;
    private Date deadline;

    @Override
    public int compareTo(Task task) {
        return id.compareTo(task.getId());
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
