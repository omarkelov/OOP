package ru.nsu.fit.markelov.log;

import java.io.IOException;
import java.io.Writer;

public class StaticLog {

    private static Writer writer;
    private static long startTime;

    public static void init(Writer pWriter) {
        writer = pWriter;
        startTime = System.currentTimeMillis();
    }

    public static void i(String message) {
        log("Info: ", getTime(), message);
    }

    public static void d(String message) {
        log("Debug: ", getTime(), message);
    }

    public static void e(String message) {
        log("Error: ", getTime(), message);
    }

    private static void log(String logLevel, long time, String message) {
        try {
            synchronized (writer) {
                writer.write(logLevel);
                writer.write(time + " ms -- ");
                writer.write(message);
                writer.write('\n');
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static long getTime() {
        return System.currentTimeMillis() - startTime;
    }
}
