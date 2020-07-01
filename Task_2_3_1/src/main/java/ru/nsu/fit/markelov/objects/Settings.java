package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

/**
 * Settings class is used for setting and getting the settings data.
 *
 * @author Oleg Markelov
 */
public class Settings implements Validatable<Settings> {
    private String workingDirectory;
    private String gitLogin;
    private String gitPassword;

    /**
     * {@inheritDoc}
     */
    @Override
    public Settings validate() throws IllegalInputException {
        requireNonNull(workingDirectory, "Settings working directory " + NOT_NULL);

        return this;
    }

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
