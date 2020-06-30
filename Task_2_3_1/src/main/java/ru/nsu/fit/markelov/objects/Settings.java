package ru.nsu.fit.markelov.objects;

public class Settings {
    private String workingDirectory;
    private String gitLogin;
    private String gitPassword;

    /**
     * Returns workingDirectory.
     *
     * @return workingDirectory.
     */
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * Sets workingDirectory.
     */
    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    /**
     * Returns gitLogin.
     *
     * @return gitLogin.
     */
    public String getGitLogin() {
        return gitLogin;
    }

    /**
     * Sets gitLogin.
     */
    public void setGitLogin(String gitLogin) {
        this.gitLogin = gitLogin;
    }

    /**
     * Returns gitPassword.
     *
     * @return gitPassword.
     */
    public String getGitPassword() {
        return gitPassword;
    }

    /**
     * Sets gitPassword.
     */
    public void setGitPassword(String gitPassword) {
        this.gitPassword = gitPassword;
    }
}
