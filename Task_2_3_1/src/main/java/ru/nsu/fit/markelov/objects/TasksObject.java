package ru.nsu.fit.markelov.objects;

import java.util.Map;
import java.util.TreeMap;

public class TasksObject {
    private Integer semester;
    private Map<String, TaskObject> taskObjects = new TreeMap<>();

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
    public Map<String, TaskObject> getTaskObjects() {
        return taskObjects;
    }

    /**
     * Sets tasks.
     */
    public void setTaskObjects(Map<String, TaskObject> taskObjects) {
        this.taskObjects = taskObjects;
    }
}
