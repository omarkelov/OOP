package ru.nsu.fit.markelov.workers;

import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Courier extends Worker {

    private Log log;

    private long time;
    private int bagCapacity;

    private final BlockingQueue<Order> storedOrders;
    private final BlockingQueue<Order> finishedOrders;

    private ArrayList<Order> bagOrders = new ArrayList<>();
    private StringBuilder stringBuilder = new StringBuilder();

    public Courier(Log log, long spinningDebugTime, String name, long time, int bagCapacity, BlockingQueue<Order> storedOrders, BlockingQueue<Order> finishedOrders) {
        super(spinningDebugTime, name);

        this.log = log;
        this.time = time;
        this.bagCapacity = bagCapacity;
        this.storedOrders = storedOrders;
        this.finishedOrders = finishedOrders;
    }

    @Override
    protected void handleNextOrder() throws InterruptedException {
        // take the order
        stringBuilder.setLength(0);

        synchronized (storedOrders) {
            while (bagOrders.size() < bagCapacity && !storedOrders.isEmpty()) {
                Order order = storedOrders.take().setCourier(this);
                bagOrders.add(order);
                stringBuilder.append(order.getId()).append(", ");
            }
        }

        if (stringBuilder.length() > 0) {
            log.i(stringBuilder.toString() + "are taken from storedOrders by " + getName());
        }

        // work with the order
        Thread.sleep(bagOrders.size() * time);

        // put the order
        stringBuilder.setLength(0);

        synchronized (finishedOrders) {
            for (Order order : bagOrders) {
                finishedOrders.put(order);
                stringBuilder.append(order.getId()).append(", ");
            }
        }

        if (stringBuilder.length() > 0) {
            log.i(stringBuilder.toString() + "are put to finishedOrders by " + getName());
        }

        bagOrders.clear();
    }
}
