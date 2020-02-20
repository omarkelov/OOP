package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.properties.CookProperties;
import ru.nsu.fit.markelov.properties.CourierProperties;
import ru.nsu.fit.markelov.properties.OperatorProperties;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.workers.Cook;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;
import ru.nsu.fit.markelov.workers.Worker;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria {

    public Pizzeria(PizzeriaProperties pizzeriaProperties, Log log) {
        BlockingQueue<Order> newOrders = new LinkedBlockingQueue<>();
        BlockingQueue<Order> storedOrders = new LinkedBlockingQueue<>(pizzeriaProperties.getStorageCapacity());
        BlockingQueue<Order> finishedOrders = new LinkedBlockingQueue<>();

        ArrayList<Worker>workers = new ArrayList<>();

        for (OperatorProperties operatorProperties : pizzeriaProperties.getOperatorPropertiesList()) {
            workers.add(new Operator(log, operatorProperties, newOrders));
        }

        for (CookProperties cookProperties : pizzeriaProperties.getCookPropertiesList()) {
            workers.add(new Cook(log, cookProperties, newOrders, storedOrders));
        }

        for (CourierProperties courierProperties : pizzeriaProperties.getCourierPropertiesList()) {
            workers.add(new Courier(log, courierProperties, storedOrders, finishedOrders));
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
