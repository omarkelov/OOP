package ru.nsu.fit.markelov.log;

public abstract class AbstractLog implements Log {

    private long startTime;

    public AbstractLog() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void i(String message) {
        log("Info: ", getTime(), message);
    }

    @Override
    public void d(String message) {
        log("Debug: ", getTime(), message);
    }

    @Override
    public void e(String message) {
        log("Error: ", getTime(), message);
    }

    private long getTime() {
        return System.currentTimeMillis() - startTime;
    }

    protected abstract void log(String logLevel, long time, String message);
}
