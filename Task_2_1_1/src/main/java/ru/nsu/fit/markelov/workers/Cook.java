package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;

import java.util.concurrent.BlockingQueue;

public class Cook extends Worker {

    private Log log;

    private long time;

    private final BlockingQueue<Order> newOrders;
    private final BlockingQueue<Order> storedOrders;

    private Order order;

    public Cook(Log log, long spinningDebugTime, String name, long time, BlockingQueue<Order> newOrders, BlockingQueue<Order> storedOrders) {
        super(spinningDebugTime, name);

        this.log = log;
        this.time = time;
        this.newOrders = newOrders;
        this.storedOrders = storedOrders;
    }

    @Override
    protected void consume() throws InterruptedException {
        order = newOrders.take();
        String message = order.getId() + " is taken from newOrders by " + getWorkerName();
        System.out.println(message);
        log.i(message);
        order.setCook(this);
    }

    @Override
    protected void produce() throws InterruptedException {
        Thread.sleep(time);
        String message = order.getId() + " is cooked by " + getWorkerName();
        System.out.println(message);
        log.i(message);

        storedOrders.put(order);
        message = order.getId() + " is put to storedOrders by " + getWorkerName();
        System.out.println(message);
        log.i(message);
    }
}
