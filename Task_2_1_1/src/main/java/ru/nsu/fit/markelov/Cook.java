package ru.nsu.fit.markelov;

import java.util.concurrent.BlockingQueue;

public class Cook extends Worker {

    private long time;

    private final BlockingQueue<Order> newOrders;
    private final BlockingQueue<Order> storedOrders;

    public Cook(String name, long time, BlockingQueue<Order> newOrders, BlockingQueue<Order> storedOrders) {
        super(name);

        this.time = time;
        this.newOrders = newOrders;
        this.storedOrders = storedOrders;
    }

    @Override
    public void run() {
        long endTime = System.currentTimeMillis() + 200;
        while (System.currentTimeMillis() < endTime) {
            try {
                Order order = newOrders.take();
                order.setCook(this);
                System.out.println(order.getId() + " is taken from newOrders");
                Thread.sleep(time); // working
                System.out.println(order.getId() + " is cooked");
                storedOrders.put(order);
                System.out.println(order.getId() + " is put to storedOrders");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
