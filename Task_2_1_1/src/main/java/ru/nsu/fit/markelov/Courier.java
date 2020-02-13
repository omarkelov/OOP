package ru.nsu.fit.markelov;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Courier extends Worker {

    private long time;
    private int bagCapacity;

    private final BlockingQueue<Order> storedOrders;
    private final BlockingQueue<Order> finishedOrders;

    public Courier(String name, long time, int bagCapacity, BlockingQueue<Order> storedOrders, BlockingQueue<Order> finishedOrders) {
        super(name);

        this.time = time;
        this.bagCapacity = bagCapacity;
        this.storedOrders = storedOrders;
        this.finishedOrders = finishedOrders;
    }

    @Override
    public void run() {
        ArrayList<Order> orders = new ArrayList<>();

        long endTime = System.currentTimeMillis() + 300;
        while (System.currentTimeMillis() < endTime) {
            try {
                synchronized (storedOrders) {
                    while (orders.size() < bagCapacity && !storedOrders.isEmpty()) {
                        Order order = storedOrders.take();
                        order.setCourier(this);
                        orders.add(order);
                        System.out.println(order.getId() + " is taken from storedOrders");
                    }
                }

                Thread.sleep(orders.size() * time); // working

                synchronized (finishedOrders) {
                    for (Order order : orders) {
                        finishedOrders.put(order);
                        System.out.println(order.getId() + " is put to finishedOrders");
                    }
                }

                orders.clear();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
