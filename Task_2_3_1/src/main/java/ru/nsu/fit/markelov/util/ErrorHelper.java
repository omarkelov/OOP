package ru.nsu.fit.markelov.util;

/**
 * ErrorHelper class is used for printing the error message with some prefix to STDOUT.
 *
 * @author Oleg Markelov
 */
public class ErrorHelper {

    private static final String ERROR_PREFIX = "Error occurred: ";

    /**
     * Prints the error message with the prefix to STDOUT.
     *
     * @param message error message.
     */
    public static void printError(String message) {
        if (message == null) {
            message = "Unknown";
        }

        System.out.println(ERROR_PREFIX + message);
    }
}
