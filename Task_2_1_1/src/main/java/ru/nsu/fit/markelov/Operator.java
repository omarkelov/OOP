package ru.nsu.fit.markelov;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Operator extends Worker {

    private final BlockingQueue<Order> newOrders;

    public Operator(String name, BlockingQueue<Order> newOrders) {
        super(name);

        this.newOrders = newOrders;
    }

    @Override
    public void run() {
        Random random = new Random(1);
        int id = 1;

        long endTime = System.currentTimeMillis() + 100;
        while (System.currentTimeMillis() < endTime) {
            try {
                Thread.sleep(random.nextInt(20) + 5); // working
                Order order = new Order("Order_" + id++, this);
                System.out.println(order.getId() + " is created");
                newOrders.put(order);
                System.out.println(order.getId() + " is put to newOrders");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
