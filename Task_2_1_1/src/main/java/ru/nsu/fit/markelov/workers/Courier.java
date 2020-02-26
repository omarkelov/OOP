package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.CourierProperties;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Courier extends Worker {

    private Log log;

    private long orderHandlingTime;
    private int bagCapacity;

    private final BlockingQueue<Order> storedOrders;
    private final BlockingQueue<Order> finishedOrders;

    private ArrayList<Order> bagOrders = new ArrayList<>();
    private StringBuilder stringBuilder = new StringBuilder();

    public Courier(CourierProperties courierProperties, BlockingQueue<Order> storedOrders, BlockingQueue<Order> finishedOrders, Log log) {
        super(courierProperties.getName(), log);

        orderHandlingTime = courierProperties.getOrderHandlingTime();
        bagCapacity = courierProperties.getBagCapacity();
        this.storedOrders = storedOrders;
        this.finishedOrders = finishedOrders;
        this.log = log;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        stringBuilder.setLength(0);

        synchronized (storedOrders) {
            while (bagOrders.size() < bagCapacity && !storedOrders.isEmpty()) {
                Order order = storedOrders.take().setCourier(this);
                bagOrders.add(order);
                stringBuilder.append(order.getName()).append(", ");
            }
        }

        if (stringBuilder.length() > 0) {
            log.i(stringBuilder.toString() + "are taken from storedOrders by " + getName());
        }

        // work with the order
        Thread.sleep(bagOrders.size() * orderHandlingTime);

        // put the order
        stringBuilder.setLength(0);

        synchronized (finishedOrders) {
            for (Order order : bagOrders) {
                finishedOrders.put(order);
                stringBuilder.append(order.getName()).append(", ");
            }
        }

        if (stringBuilder.length() > 0) {
            log.i(stringBuilder.toString() + "are put to finishedOrders by " + getName());
        }

        bagOrders.clear();
    }
}
