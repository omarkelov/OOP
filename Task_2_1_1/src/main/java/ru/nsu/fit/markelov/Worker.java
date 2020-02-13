package ru.nsu.fit.markelov;

public abstract class Worker implements Runnable {

    private String name;

    public Worker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
