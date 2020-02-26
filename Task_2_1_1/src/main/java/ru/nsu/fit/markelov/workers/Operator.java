package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.OperatorProperties;
import ru.nsu.fit.markelov.util.UniqueIntGenerator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Operator extends Worker {

    private Log log;

    private long orderHandlingTimeMin;
    private long orderHandlingTimeMax;

    UniqueIntGenerator idGenerator;

    private final BlockingQueue<Order> newOrders;

    public Operator(OperatorProperties operatorProperties, UniqueIntGenerator idGenerator, BlockingQueue<Order> newOrders, Log log) {
        super(operatorProperties.getName(), log);

        orderHandlingTimeMin = operatorProperties.getOrderHandlingTimeMin();
        orderHandlingTimeMax = operatorProperties.getOrderHandlingTimeMax();
        this.idGenerator = idGenerator;
        this.newOrders = newOrders;
        this.log = log;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        Order order = new Order(idGenerator.getNextId()).setOperator(this);

        // work with the order
        Thread.sleep(ThreadLocalRandom.current().nextLong(orderHandlingTimeMin, orderHandlingTimeMax));

        // put the order
        newOrders.put(order);
        log.i(order.getName() + " is put to newOrders by " + getName());
    }
}
