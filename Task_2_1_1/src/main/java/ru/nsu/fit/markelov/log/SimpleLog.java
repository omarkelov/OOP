package ru.nsu.fit.markelov.log;

import java.io.IOException;
import java.io.Writer;

public class SimpleLog implements Log {

    private Writer writer;
    private long startTime;

    public SimpleLog(Writer writer) {
        this.writer = writer;
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
