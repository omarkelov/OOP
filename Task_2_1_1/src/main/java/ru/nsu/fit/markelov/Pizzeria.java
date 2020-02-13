package ru.nsu.fit.markelov;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria {

    private int nCooks;
    private int nCouriers;
    private int storageCapacity = 5;

    private Operator operator;
    private Cook cook_1;
    private Cook cook_2;
    private Courier courier;

    private BlockingQueue<Order> newOrders;
    private BlockingQueue<Order> storedOrders;
    private BlockingQueue<Order> finishedOrders;

    public Pizzeria() {
        newOrders = new LinkedBlockingQueue<>();
        storedOrders = new LinkedBlockingQueue<>(storageCapacity);
        finishedOrders = new LinkedBlockingQueue<>();

        operator = new Operator("Operator_1", newOrders);
        cook_1 = new Cook("Cook_1", 10, newOrders, storedOrders);
        cook_2 = new Cook("Cook_2", 50, newOrders, storedOrders);
        courier = new Courier("Courier_1", 30, 2, storedOrders, finishedOrders);

        new Thread(operator).start();
        new Thread(cook_1).start();
        new Thread(cook_2).start();
        new Thread(courier).start();
    }
}
