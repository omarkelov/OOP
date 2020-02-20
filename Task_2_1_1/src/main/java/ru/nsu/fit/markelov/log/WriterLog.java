package ru.nsu.fit.markelov.log;

import java.io.IOException;
import java.io.Writer;

public class WriterLog extends AbstractLog {

    private Writer writer;

    public WriterLog(Writer writer) {
        this.writer = writer;
    }

    @Override
    protected synchronized void log(String logLevel, long time, String message) {
        try {
            writer.write(logLevel);
            writer.write(time + " ms -- ");
            writer.write(message);
            writer.write('\n');
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
