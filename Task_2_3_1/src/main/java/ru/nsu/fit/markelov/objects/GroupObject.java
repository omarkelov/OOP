package ru.nsu.fit.markelov.objects;

import java.util.Map;
import java.util.TreeMap;

public class GroupObject {
    private String name;
    private Map<String, StudentObject> students = new TreeMap<>();

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
     * Returns students.
     *
     * @return students.
     */
    public Map<String, StudentObject> getStudents() {
        return students;
    }

    /**
     * Sets students.
     */
    public void setStudents(Map<String, StudentObject> students) {
        this.students = students;
    }
}
