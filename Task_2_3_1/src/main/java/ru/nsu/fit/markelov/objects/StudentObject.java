package ru.nsu.fit.markelov.objects;

public class StudentObject {
    private String nickname;
    private String fullName;
    private String repositoryUrl;
    private String branchName;

    /**
     * Returns nickname.
     *
     * @return nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
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
