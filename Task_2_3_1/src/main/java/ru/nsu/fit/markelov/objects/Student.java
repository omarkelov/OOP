package ru.nsu.fit.markelov.objects;

public class Student implements Comparable<Student> {
    private String id;
    private String fullName;
    private String repositoryUrl;
    private String branchName;

    @Override
    public int compareTo(Student student) {
        return id.compareTo(student.getId());
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
