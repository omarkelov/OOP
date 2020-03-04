package ru.nsu.fit.markelov.log;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;

/**
 * The <code>WriterLog</code> class is used for writing log messages to the specified
 * <code>Writer</code>. It extends the <code>AbstractLog</code> class overriding
 * <code>log()</code> method, making it synchronized as well.
 * <p>
 * The <code>WriterLog</code> class provides three methods for writing messages: <code>i()</code>,
 * <code>d()</code> and <code>e()</code>. These methods are defined in <code>AbstractLog</code>.
 *
 * @author Oleg Markelov
 * @see    Log
 * @see    AbstractLog
 * @see    Writer
 */
public class WriterLog extends AbstractLog {

    private Writer writer;

    /**
     * Constructs a new <code>WriterLog</code> object, initializing it with specified
     * <code>Writer</code>.
     *
     * @param  writer a <code>Writer</code> for writing messages.
     * @throws NullPointerException if any input parameter is null.
     */
    public WriterLog(Writer writer) {
        this.writer = Objects.requireNonNull(writer,
            buildMessage(WriterLog.class, "writer", NOT_NULL));
    }

    /**
     * Writes a message to the specified <code>Writer</code> in the next format:
     * "<logLevel>: <time> ms — <message>".
     *
     * @param logLevel log level.
     * @param time     time in milliseconds since <code>AbstractLog</code> object creation.
     * @param message  a message to be sent.
     */
    @Override
    protected synchronized void log(String logLevel, long time, String message) {
        try {
            writer.write(logLevel);
            writer.write(": " + time + " ms — ");
            writer.write(message);
            writer.write('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
