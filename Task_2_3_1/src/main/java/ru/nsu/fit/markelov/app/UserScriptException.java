package ru.nsu.fit.markelov.app;

/**
 * <code>UserScriptException</code> signals that something went wrong during user script executing.
 *
 * @author Oleg Markelov
 */
public class UserScriptException extends Exception {

    /**
     * {@inheritDoc}
     */
    public UserScriptException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public UserScriptException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public UserScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public UserScriptException(Throwable cause) {
        super(cause);
    }
}
