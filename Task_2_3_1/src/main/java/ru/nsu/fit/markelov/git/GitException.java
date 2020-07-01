package ru.nsu.fit.markelov.git;

/**
 * <code>GitException</code> signals that something went wrong during working with a git.
 *
 * @author Oleg Markelov
 */
public class GitException extends Exception {

    /**
     * {@inheritDoc}
     */
    public GitException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public GitException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public GitException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public GitException(Throwable cause) {
        super(cause);
    }
}
