package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;

import java.util.concurrent.BlockingQueue;

public class Cook extends Worker {

    private long time;

    private final BlockingQueue<Order> newOrders;
    private final BlockingQueue<Order> storedOrders;

    public Cook(Log log, long spinningDebugTime, String name, long time, BlockingQueue<Order> newOrders, BlockingQueue<Order> storedOrders) {
        super(log, spinningDebugTime, name);

        this.time = time;
        this.newOrders = newOrders;
        this.storedOrders = storedOrders;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        Order order = newOrders.take().setCook(this);
        log(order.getId() + " is taken from newOrders by " + getWorkerName());

        // work with the order
        Thread.sleep(time);
        log(order.getId() + " is cooked by " + getWorkerName());

        // put the order
        storedOrders.put(order);
        log(order.getId() + " is put to storedOrders by " + getWorkerName());
    }
}
