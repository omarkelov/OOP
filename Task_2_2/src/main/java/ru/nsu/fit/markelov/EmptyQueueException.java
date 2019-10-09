package ru.nsu.fit.markelov;

public class EmptyQueueException extends RuntimeException {

    /**
     * Constructs an EmptyQueueException with no detail message.
     */
    public EmptyQueueException() {
        super();
    }

    /**
     * Constructs an EmptyQueueException with the specified
     * detail message.
     *
     * @param str the detail message.
     */
    public EmptyQueueException(String str) {
        super(str);
    }
}
