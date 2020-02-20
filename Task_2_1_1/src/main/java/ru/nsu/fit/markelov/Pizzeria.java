package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.CookProperties;
import ru.nsu.fit.markelov.properties.CourierProperties;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.workers.Cook;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;
import ru.nsu.fit.markelov.workers.Worker;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria {

    public static final long WORKING_DAY_TIME = 1000;

    public Pizzeria(PizzeriaProperties pizzeriaProperties, Log log) {
        BlockingQueue<Order> newOrders = new LinkedBlockingQueue<>();
        BlockingQueue<Order> storedOrders = new LinkedBlockingQueue<>(pizzeriaProperties.getStorageCapacity());
        BlockingQueue<Order> finishedOrders = new LinkedBlockingQueue<>();

        ArrayList<Worker>workers = new ArrayList<>();

        workers.add(new Operator(log, 100, "Operator_1", newOrders));

        for (CookProperties cookProperties : pizzeriaProperties.getCookPropertiesList()) {
            workers.add(new Cook(log, 200, cookProperties, newOrders, storedOrders));
        }

        for (CourierProperties courierProperties : pizzeriaProperties.getCourierPropertiesList()) {
            workers.add(new Courier(log, 300, courierProperties, storedOrders, finishedOrders));
        }

        workers.forEach(Worker::startWorkingDay);

        workers.forEach(worker -> {
            try {
                worker.getThread().join(WORKING_DAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        workers.forEach(Worker::finishWorkingDay);
    }
}
