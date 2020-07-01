package ru.nsu.fit.markelov.gradle;

/**
 * <code>GradleException</code> signals that something went wrong during working with a gradle.
 *
 * @author Oleg Markelov
 */
public class GradleException extends Exception {

    /**
     * {@inheritDoc}
     */
    public GradleException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public GradleException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public GradleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public GradleException(Throwable cause) {
        super(cause);
    }
}
