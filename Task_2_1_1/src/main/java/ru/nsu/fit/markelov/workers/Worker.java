package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.StaticLog;

public abstract class Worker extends Thread {

    private Log log;

    private String workerName;
    private long spinningDebugTime;

    public Worker(Log log, long spinningDebugTime, String workerName) {
        this.log = log;
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
                handleNextOrder();
            } catch (InterruptedException e) {
                System.out.println(getWorkerName() + " is interrupted");
            }
        }
    }

    protected abstract void handleNextOrder() throws InterruptedException;

    protected void log(String message) {
        System.out.println(message);
        log.i(message);
        StaticLog.i(message);
        Log.getInstance().i(message);
    }
}
