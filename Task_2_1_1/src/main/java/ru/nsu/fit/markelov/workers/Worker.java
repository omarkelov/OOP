package ru.nsu.fit.markelov.workers;

public abstract class Worker extends Thread {

    private String workerName;
    private long spinningDebugTime;

    public Worker(long spinningDebugTime, String workerName) {
        this.spinningDebugTime = spinningDebugTime;
        this.workerName = workerName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void run() {
        long endTime = System.currentTimeMillis() + spinningDebugTime;
        while (System.currentTimeMillis() < endTime) {
            try {
                consume();
                produce();
            } catch (InterruptedException e) {
                System.out.println(getWorkerName() + " is interrupted");
            }
        }
    }

    protected abstract void consume() throws InterruptedException;

    protected abstract void produce() throws InterruptedException;
}
