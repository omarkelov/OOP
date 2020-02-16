package ru.nsu.fit.markelov.log;

import java.io.IOException;
import java.io.Writer;

public class StaticLog {

    private StaticLog() {} // singleton: prevent instantiation from other classes

    private static StaticLog log;

    private Writer writer;
    private long startTime;

    public static void init(Writer writer) {
        if (log == null) {
            log = new StaticLog();
            log.writer = writer;
            log.startTime = System.currentTimeMillis();
        }
    }

    public static void i(String message) {
        log.log("Info: ", getTime(), message);
    }

    public static void d(String message) {
        log.log("Debug: ", getTime(), message);
    }

    public static void e(String message) {
        log.log("Error: ", getTime(), message);
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

    private static long getTime() {
        return System.currentTimeMillis() - log.startTime;
    }
}
