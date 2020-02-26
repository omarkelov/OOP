package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.log.Log;

public abstract class Worker {

    private Log log;

    private String name;

    private Thread thread;

    public Worker(String name, Log log) {
        this.name = name;
        this.log = log;
    }

    public void startWorkingDay() {
        thread = new Thread(this::runThread);
        thread.start();
    }

    public void finishWorkingDay() throws InterruptedException {
        thread.interrupt();
        thread.join();
    }

    private void runThread() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleNextOrder();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.d(name + " is interrupted.");
            }
        }
        log.i(name + " has finished the working day.");
    }

    protected abstract void handleNextOrder() throws InterruptedException;

    public String getName() {
        return name;
    }
}
