package ru.nsu.fit.markelov.util;

public class UniqueIntGenerator {

    private int id;

    public UniqueIntGenerator() {
        id = 1;
    }

    public synchronized int getNextId() {
        return id++;
    }
}
