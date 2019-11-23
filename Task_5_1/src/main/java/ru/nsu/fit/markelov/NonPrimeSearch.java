package ru.nsu.fit.markelov;

public abstract class NonPrimeSearch {

    private long executionTime;
    private boolean nonPrimeFound = false;

    public void execute() {
        long startNanoTime = System.nanoTime();
        nonPrimeFound = find();
        executionTime = System.nanoTime() - startNanoTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public boolean isNonPrimeFound() {
        return nonPrimeFound;
    }

    public abstract String getType();

    protected abstract boolean find();
}
