package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Operator extends Worker {

    private Log log;

    private Random random;
    private int id;

    private final BlockingQueue<Order> newOrders;

    private Order order;

    public Operator(Log log, long spinningDebugTime, String name, BlockingQueue<Order> newOrders) {
        super(spinningDebugTime, name);

        random = new Random(1);
        id = 1;

        this.log = log;
        this.newOrders = newOrders;
    }

    @Override
    protected void consume() {
        order = new Order("Order_" + id++, this);
    }

    @Override
    protected void produce() throws InterruptedException {
        Thread.sleep(random.nextInt(20) + 5);

        newOrders.put(order);
        String message = order.getId() + " is put to newOrders by " + getWorkerName();
        System.out.println(message);
        log.i(message);
    }
}
