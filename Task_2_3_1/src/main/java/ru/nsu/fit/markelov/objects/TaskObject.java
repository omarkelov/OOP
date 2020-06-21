package ru.nsu.fit.markelov.objects;

import java.util.Date;

public class TaskObject implements Comparable<TaskObject> {
    private String id;
    private String name;
    private float points;
    private Date deadline;

    @Override
    public int compareTo(TaskObject taskObject) {
        return id.compareTo(taskObject.getId());
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
    public float getPoints() {
        return points;
    }

    /**
     * Sets points.
     */
    public void setPoints(float points) {
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
