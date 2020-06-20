package ru.nsu.fit.markelov.objects;

import java.util.Map;
import java.util.TreeMap;

public class TasksObject {
    private Integer semester;
    private Map<String, TaskObject> tasks = new TreeMap<>();

    /**
     * Returns semester.
     *
     * @return semester.
     */
    public Integer getSemester() {
        return semester;
    }

    /**
     * Sets semester.
     */
    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    /**
     * Returns tasks.
     *
     * @return tasks.
     */
    public Map<String, TaskObject> getTasks() {
        return tasks;
    }

    /**
     * Sets tasks.
     */
    public void setTasks(Map<String, TaskObject> tasks) {
        this.tasks = tasks;
    }
}
