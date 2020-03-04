package ru.nsu.fit.markelov.log;

/**
 * The <code>AbstractLog</code> class is used for sending log output. It implements the
 * <code>Log</code> interface, providing three methods for sending messages: <code>i()</code>,
 * <code>d()</code> and <code>e()</code>.
 * <p>
 * The <code>AbstractLog</code> class has one abstract method <code>log()</code>, which should
 * define a way of sending log messages after overriding.
 *
 * @author Oleg Markelov
 * @see    Log
 */
public abstract class AbstractLog implements Log {

    private long startTime;

    /**
     * Constructs a new <code>AbstractLog</code> object, remembering the time of creation for future
     * use in <code>log()</code> method.
     */
    public AbstractLog() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Sends an informational message using <code>log()</code> method.
     * <p>
     * If the message is <code>null</code> an empty message is sent.
     *
     * @param message a message to be sent.
     */
    @Override
    public void i(String message) {
        log("Info", getTime(), message != null ? message : "");
    }

    /**
     * Sends a debug message using <code>log()</code> method.
     * <p>
     * If the message is <code>null</code> an empty message is sent.
     *
     * @param message a message to be sent.
     */
    @Override
    public void d(String message) {
        log("Debug", getTime(), message != null ? message : "");
    }

    /**
     * Sends an error message using <code>log()</code> method.
     * <p>
     * If the message is <code>null</code> an empty message is sent.
     *
     * @param message a message to be sent.
     */
    @Override
    public void e(String message) {
        log("Error", getTime(), message != null ? message : "");
    }

    private long getTime() {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Sends a message with specified logLevel, current time and the message itself.
     *
     * @param logLevel log level.
     * @param time     time in milliseconds since <code>AbstractLog</code> object creation.
     * @param message  a message to be sent.
     */
    protected abstract void log(String logLevel, long time, String message);
}
