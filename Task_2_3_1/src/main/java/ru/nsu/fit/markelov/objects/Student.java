package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_EMPTY;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Student class is used for setting and getting the student data.
 *
 * @author Oleg Markelov
 */
public class Student implements Comparable<Student>, Validatable<Student> {
    private String id;
    private String fullName;
    private String repositoryUrl;
    private String branchName;

    /**
     * Compares this object with the specified object based on their ids.
     *
     * @param student the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     *         equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Student student) {
        return id.compareTo(student.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Student validate() throws IllegalInputException {
        requireNonNull(id, "Student id " + NOT_NULL);
        requireNonNull(fullName, "Student full name " + NOT_NULL);
        requireNonNull(repositoryUrl, "Student repository url " + NOT_NULL);
        requireNonNull(branchName, "Student branch name " + NOT_NULL);

        if (id.isEmpty()) {
            throw new IllegalInputException("Student id " + NOT_EMPTY);
        }

        if (fullName.isEmpty()) {
            throw new IllegalInputException("Student full name " + NOT_EMPTY);
        }

        if (repositoryUrl.isEmpty()) {
            throw new IllegalInputException("Student repository url " + NOT_EMPTY);
        }

        if (branchName.isEmpty()) {
            throw new IllegalInputException("Student branch name " + NOT_EMPTY);
        }

        return this;
    }

    /**
     * Returns nickname.
     *
     * @return nickname.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets nickname.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns fullName.
     *
     * @return fullName.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets fullName.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Returns repositoryUrl.
     *
     * @return repositoryUrl.
     */
    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    /**
     * Sets repositoryUrl.
     */
    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    /**
     * Returns branchName.
     *
     * @return branchName.
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * Sets branchName.
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
