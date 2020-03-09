package ru.nsu.fit.markelov.log;

/**
 * The <code>Log</code> interface is used for sending log output. It provides three methods for
 * sending messages: <code>i()</code>, <code>d()</code> and <code>e()</code>.
 *
 * @author Oleg Markelov
 */
public interface Log {

    /**
     * Sends an informational message.
     *
     * @param message a message to be sent.
     */
    void i(String message);

    /**
     * Sends a debug message.
     *
     * @param message a message to be sent.
     */
    void d(String message);

    /**
     * Sends an error message.
     *
     * @param message a message to be sent.
     */
    void e(String message);
}
