package ru.nsu.fit.markelov;

/**
 * NonPrimeSearch is an abstract class that is used to detect, whether a composite number is present
 * in the array.
 *
 * @author Oleg Markelov
 */
public abstract class NonPrimeSearch {

    private long executionTime;
    private boolean nonPrimeFound = false;

    /**
     * Executes a search of a composite number in the array.
     */
    public void execute() {
        long startNanoTime = System.nanoTime();
        nonPrimeFound = find();
        executionTime = System.nanoTime() - startNanoTime;
    }

    /**
     * Returns the execution time of the search in nanoseconds.
     *
     * Must be called after <code>execute</code> is finished. Otherwise, the return value is
     * invalid.
     *
     * @return the execution time of the search in nanoseconds.
     */
    public long getExecutionTime() {
        return executionTime;
    }

    /**
     * Returns whether a composite number is found in the array.
     *
     * Must be called after <code>execute</code> is finished. Otherwise, the return value is
     * invalid.
     *
     * @return whether a composite number is found in the array.
     */
    public boolean isNonPrimeFound() {
        return nonPrimeFound;
    }

    /**
     * A short description of the search.
     *
     * @return a short description of the search.
     */
    public abstract String getType();

    /**
     * Tries to find a composite number in the array.
     *
     * @return whether a composite number is found in the array.
     */
    protected abstract boolean find();
}
