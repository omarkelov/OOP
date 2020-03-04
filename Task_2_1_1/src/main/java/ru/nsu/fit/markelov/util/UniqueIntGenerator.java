package ru.nsu.fit.markelov.util;

/**
 * The <code>UniqueIntGenerator</code> interface is used for generating unique <code>int</code>
 * value. It provides one method for getting the next value: <code>getNextInt()</code>.
 *
 * @author Oleg Markelov
 */
public interface UniqueIntGenerator {
    /**
     * Returns the next unique value.
     *
     * @return the next unique value.
     * @throws RuntimeException if there is no more unique integers.
     */
    int getNextInt() throws RuntimeException;
}
