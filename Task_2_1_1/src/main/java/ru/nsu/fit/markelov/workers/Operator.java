package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.StaticLog;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Operator extends Worker {

    private Random random;
    private int id;

    private final BlockingQueue<Order> newOrders;

    public Operator(Log log, long spinningDebugTime, String name, BlockingQueue<Order> newOrders) {
        super(log, spinningDebugTime, name);

        random = new Random(1);
        id = 1;

        this.newOrders = newOrders;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        Order order = new Order("Order_" + id++).setOperator(this);

        // work with the order
        Thread.sleep(random.nextInt(20) + 5);

        // put the order
        newOrders.put(order);
        log(order.getId() + " is put to newOrders by " + getWorkerName());
    }
}
