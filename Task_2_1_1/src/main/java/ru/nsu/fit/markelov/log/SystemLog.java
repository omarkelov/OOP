package ru.nsu.fit.markelov.log;

/**
 * The <code>SystemLog</code> class is used for writing log messages to the standard output. It
 * extends the <code>AbstractLog</code> class overriding <code>log()</code> method, making it
 * synchronized as well.
 * <p>
 * The <code>SystemLog</code> class provides three methods for writing messages: <code>i()</code>,
 * <code>d()</code> and <code>e()</code>. These methods are defined in <code>AbstractLog</code>.
 *
 * @author Oleg Markelov
 * @see    Log
 * @see    AbstractLog
 */
public class SystemLog extends AbstractLog {
    /**
     * Writes a message to the standard output in the next format:
     * "<logLevel>: <time> ms -- <message>".
     *
     * @param logLevel log level.
     * @param time     time in milliseconds since <code>AbstractLog</code> object creation.
     * @param message  a message to be sent.
     */
    @Override
    protected synchronized void log(String logLevel, long time, String message) {
        System.out.print(logLevel);
        System.out.print(": ");
        System.out.print(time);
        System.out.print(" ms -- ");
        System.out.println(message);
    }
}
