package ru.nsu.fit.markelov.util;

public class ErrorHelper {

    private static final String ERROR_PREFIX = "Error occurred: ";

    public static void printError(String message) {
        if (message == null) {
            message = "Unknown";
        }

        System.out.println(ERROR_PREFIX + message);
    }
}
