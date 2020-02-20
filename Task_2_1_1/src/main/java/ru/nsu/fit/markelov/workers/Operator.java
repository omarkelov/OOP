package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.OperatorProperties;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Operator extends Worker {

    private Log log;

    private long orderHandlingTimeMin;
    private long orderHandlingTimeMax;

    private int id; ////////////////////////////////////////////////////////////////////////////////

    private final BlockingQueue<Order> newOrders;

    public Operator(Log log, OperatorProperties operatorProperties, BlockingQueue<Order> newOrders) {
        super(log, operatorProperties.getName());

        id = 1;

        this.log = log;
        orderHandlingTimeMin = operatorProperties.getOrderHandlingTimeMin();
        orderHandlingTimeMax = operatorProperties.getOrderHandlingTimeMax();
        this.newOrders = newOrders;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        Order order = new Order("Order_" + id++).setOperator(this);

        // work with the order
        Thread.sleep(ThreadLocalRandom.current().nextLong(orderHandlingTimeMin, orderHandlingTimeMax));

        // put the order
        newOrders.put(order);
        log.i(order.getId() + " is put to newOrders by " + getName());
    }
}
