package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Map;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_EMPTY;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Group class is used for setting and getting group name and the collection of students.
 *
 * @author Oleg Markelov
 */
public class Group implements Validatable<Group> {
    private String name;
    private Map<String, Student> students = new TreeMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Group validate() throws IllegalInputException {
        requireNonNull(name, "Group name " + NOT_NULL);

        if (name.isEmpty()) {
            throw new IllegalInputException("Group name " + NOT_EMPTY);
        }

        for (Student student : students.values()) {
            student.validate();
        }

        return this;
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
     * Returns students.
     *
     * @return students.
     */
    public Map<String, Student> getStudents() {
        return students;
    }

    /**
     * Sets students.
     */
    public void setStudents(Map<String, Student> students) {
        this.students = students;
    }
}
