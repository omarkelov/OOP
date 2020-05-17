package ru.nsu.fit.markelov.validation;

/**
 * <code>IllegalInputException</code> signals that an input is null or illegal.
 *
 * @author Oleg Markelov
 */
public class IllegalInputException extends Exception {

    /**
     * {@inheritDoc}
     */
    public IllegalInputException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public IllegalInputException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalInputException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public IllegalInputException(Throwable cause) {
        super(cause);
    }

    /**
     * Checks that the specified object reference is not null. This method is designed primarily for
     * doing parameter validation in methods and constructors.
     *
     * @param object  the object reference to check for nullity.
     * @param message detail message to be used in the event that a
     *                <code>IllegalInputException</code> is thrown.
     * @param <T>     the type of the reference.
     * @return        object itself if not null.
     * @throws IllegalInputException if object is null.
     */
    public static <T> T requireNonNull(T object, String message) throws IllegalInputException {
        if (object == null) {
            throw new IllegalInputException(message);
        }

        return object;
    }
}
