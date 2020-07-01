package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Map;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_POSITIVE;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class Tasks implements Validatable<Tasks> {
    private Integer semester;
    private Map<String, Task> tasks = new TreeMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Tasks validate() throws IllegalInputException {
        requireNonNull(semester, "Tasks semester " + NOT_NULL);

        if (semester <= 0) {
            throw new IllegalInputException("Tasks semester " + NOT_POSITIVE);
        }

        for (Task task : tasks.values()) {
            task.validate();
        }

        return this;
    }

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
    public Map<String, Task> getTasks() {
        return tasks;
    }

    /**
     * Sets tasks.
     */
    public void setTasks(Map<String, Task> tasks) {
        this.tasks = tasks;
    }
}
