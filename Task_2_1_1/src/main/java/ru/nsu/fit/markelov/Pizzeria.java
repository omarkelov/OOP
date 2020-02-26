package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.BakerProperties;
import ru.nsu.fit.markelov.properties.CourierProperties;
import ru.nsu.fit.markelov.properties.OperatorProperties;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.util.UniqueIntGenerator;
import ru.nsu.fit.markelov.workers.Baker;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;
import ru.nsu.fit.markelov.workers.Worker;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria {

    private BlockingQueue<Order> newOrders;
    private BlockingQueue<Order> storedOrders;
    private BlockingQueue<Order> finishedOrders;

    private ArrayList<Worker> workers;

    public Pizzeria(PizzeriaProperties pizzeriaProperties, Log log, UniqueIntGenerator idGenerator) {
        newOrders = new LinkedBlockingQueue<>();
        storedOrders = new LinkedBlockingQueue<>(pizzeriaProperties.getStorageCapacity());
        finishedOrders = new LinkedBlockingQueue<>();

        workers = new ArrayList<>();

        for (OperatorProperties operatorProperties : pizzeriaProperties.getOperatorPropertiesList()) {
            workers.add(new Operator(operatorProperties, idGenerator, newOrders, log));
        }

        for (BakerProperties bakerProperties : pizzeriaProperties.getBakerPropertiesList()) {
            workers.add(new Baker(bakerProperties, newOrders, storedOrders, log));
        }

        for (CourierProperties courierProperties : pizzeriaProperties.getCourierPropertiesList()) {
            workers.add(new Courier(courierProperties, storedOrders, finishedOrders, log));
        }

        try {
            for (Worker worker : workers) {
                worker.startWorkingDay();
            }

            Thread.sleep(pizzeriaProperties.getWorkingTime());

            for (Worker worker : workers) {
                worker.finishWorkingDay();
            }
        } catch (InterruptedException e) {
            log.e("Main thread is interrupted.");
        }
    }
}
