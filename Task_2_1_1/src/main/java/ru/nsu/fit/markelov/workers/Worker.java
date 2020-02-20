package ru.nsu.fit.markelov.workers;

public abstract class Worker {

    private String name;
    private long spinningDebugTime;

    private Thread thread;

    public Worker(long spinningDebugTime, String name) {
        this.spinningDebugTime = spinningDebugTime;
        this.name = name;
    }

    public void startWorkingDay() {
        thread = new Thread(this::runThread);
        thread.start();
    }

    public void finishWorkingDay() {
        thread.interrupt();
    }

    private void runThread() {
        long endTime = System.currentTimeMillis() + spinningDebugTime;
        while (System.currentTimeMillis() < endTime) {
            try {
                handleNextOrder();
            } catch (InterruptedException e) {
                System.out.println(name + " is interrupted");
            }
        }
    }

    protected abstract void handleNextOrder() throws InterruptedException;

    public String getName() {
        return name;
    }

    public Thread getThread() {
        return thread;
    }
}
