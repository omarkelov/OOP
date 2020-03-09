package ru.nsu.fit.markelov.util;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;

/**
 * The <code>IterativeIntGenerator</code> class implements the <code>UniqueIntGenerator</code>
 * interface and is used for generating unique <code>int</code> value in iterative way. It provides
 * one synchronized method for getting the next value: <code>getNextInt()</code>.
 * <p>
 * The fist time <code>getNextInt()</code> is called, it returns 1. Then each call of
 * <code>getNextInt()</code> returns a value greater than the previous by 1.
 *
 * @author Oleg Markelov
 */
public class IterativeIntGenerator implements UniqueIntGenerator {

    public static final String NO_UNIQUE_INTS = "No more unique integers.";

    private int id;

    /**
     * Constructs a new <code>IterativeIntGenerator</code> object.
     */
    public IterativeIntGenerator() {
        id = 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int getNextInt() {
        if (id == 0) {
            throw new RuntimeException(buildMessage(IterativeIntGenerator.class, NO_UNIQUE_INTS));
        }

        return id++;
    }
}
