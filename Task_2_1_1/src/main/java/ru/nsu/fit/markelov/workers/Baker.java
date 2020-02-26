package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.BakerProperties;

import java.util.concurrent.BlockingQueue;

public class Baker extends Worker {

    private Log log;

    private long orderHandlingTime;

    private final BlockingQueue<Order> newOrders;
    private final BlockingQueue<Order> storedOrders;

    public Baker(BakerProperties bakerProperties, BlockingQueue<Order> newOrders, BlockingQueue<Order> storedOrders, Log log) {
        super(bakerProperties.getName(), log);

        orderHandlingTime = bakerProperties.getOrderHandlingTime();
        this.newOrders = newOrders;
        this.storedOrders = storedOrders;
        this.log = log;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        Order order = newOrders.take().setBaker(this);
        log.i(order.getName() + " is taken from newOrders by " + getName());

        // work with the order
        Thread.sleep(orderHandlingTime);
        log.i(order.getName() + " is cooked by " + getName());

        // put the order
        storedOrders.put(order);
        log.i(order.getName() + " is put to storedOrders by " + getName());
    }
}
