package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.CookProperties;

import java.util.concurrent.BlockingQueue;

public class Cook extends Worker {

    private Log log;

    private long time;

    private final BlockingQueue<Order> newOrders;
    private final BlockingQueue<Order> storedOrders;

    public Cook(Log log, CookProperties cookProperties, BlockingQueue<Order> newOrders, BlockingQueue<Order> storedOrders) {
        super(log, cookProperties.getName());

        this.log = log;
        time = cookProperties.getOrderHandlingTime();
        this.newOrders = newOrders;
        this.storedOrders = storedOrders;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        Order order = newOrders.take().setCook(this);
        log.i(order.getId() + " is taken from newOrders by " + getName());

        // work with the order
        Thread.sleep(time);
        log.i(order.getId() + " is cooked by " + getName());

        // put the order
        storedOrders.put(order);
        log.i(order.getId() + " is put to storedOrders by " + getName());
    }
}
