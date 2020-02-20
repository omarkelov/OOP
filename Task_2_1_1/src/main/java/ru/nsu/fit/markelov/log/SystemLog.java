package ru.nsu.fit.markelov.log;

public class SystemLog extends AbstractLog {
    @Override
    protected void log(String logLevel, long time, String message) {
        System.out.print(logLevel);
        System.out.print(time + " ms -- ");
        System.out.println(message);
    }
}
