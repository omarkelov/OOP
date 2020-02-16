package ru.nsu.fit.markelov.log;

import java.io.IOException;
import java.io.Writer;

public class SingletonLog implements Log {

    private SingletonLog() {} // singleton: prevent instantiation from other classes

    private static SingletonLog log;

    private Writer writer;
    private long startTime;

    public static SingletonLog getInstance() {
        if (log == null) {
            log = new SingletonLog();
            log.startTime = System.currentTimeMillis();
        }

        return log;
    }

    @Override
    public void setWriter(Writer writer) {
        log.writer = writer;
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

    private synchronized void log(String logLevel, long time, String message) {
        try {
            writer.write(logLevel);
            writer.write(time + " ms -- ");
            writer.write(message);
            writer.write('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private long getTime() {
        return System.currentTimeMillis() - startTime;
    }
}
