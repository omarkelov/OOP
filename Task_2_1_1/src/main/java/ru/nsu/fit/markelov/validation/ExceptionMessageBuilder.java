package ru.nsu.fit.markelov.validation;

/**
 * The <code>ExceptionMessageBuilder</code> class is used for building an error message. It provides
 * two static methods: <code>buildMessage()</code>.
 *
 * @author Oleg Markelov
 */
public class ExceptionMessageBuilder {

    public static final String NOT_NULL = "must be not null.";
    public static final String NOT_EMPTY = "must be not empty.";
    public static final String POSITIVE = "must be positive.";

    public static final String UNKNOWN_ERROR = "Unknown error.";

    /**
     * Builds and returns an error message in the next format:
     * 'Error in "[className]": [message].'
     * <p>
     * If one of the input parameters is <code>null</code> an error message is "Unknown error."
     *
     * @param clazz   a class for getting a class name.
     * @param message a message.
     * @return        an error message.
     */
    public static String buildMessage(Class<?> clazz, String message) {
        if (clazz == null || message == null) {
            return UNKNOWN_ERROR;
        }

        return "Error in \"" + clazz.getSimpleName() + "\" class: " + message;
    }

    /**
     * Builds and returns an error message in the next format:
     * 'Error in "[className]": "[parameter]" [message].'
     * <p>
     * If one of the input parameters is <code>null</code> an error message is "Unknown error."
     *
     * @param clazz     a class for getting a class name.
     * @param parameter a name of the parameter.
     * @param message   a message for the parameter.
     * @return          an error message.
     */
    public static String buildMessage(Class<?> clazz, String parameter, String message) {
        if (clazz == null || parameter == null || message == null) {
            return UNKNOWN_ERROR;
        }

        return "Error in \"" + clazz.getSimpleName() + "\" class: \"" + parameter + "\" " + message;
    }
}
