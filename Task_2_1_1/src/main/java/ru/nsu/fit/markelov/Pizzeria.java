package ru.nsu.fit.markelov;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.workers.Cook;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;
import ru.nsu.fit.markelov.workers.Worker;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Pizzeria {

    public static final long WORKING_DAY_TIME = 1000;

    public Pizzeria(String jsonStr, Log log) {
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray jsonCooks = jsonObject.getJSONArray("cooks");
        JSONArray jsonCouriers = jsonObject.getJSONArray("couriers");

        BlockingQueue<Order> newOrders = new LinkedBlockingQueue<>();
        BlockingQueue<Order> storedOrders = new LinkedBlockingQueue<>(jsonObject.getInt("storage_capacity"));
        BlockingQueue<Order> finishedOrders = new LinkedBlockingQueue<>();

        ArrayList<Worker>workers = new ArrayList<>();

        workers.add(new Operator(log, 100, "Operator_1", newOrders));

        for (int i = 0; i < jsonCooks.length(); i++) {
            JSONObject jsonCook = jsonCooks.getJSONObject(i);
            workers.add(new Cook(log, 200, jsonCook.getString("name"),
                    jsonCook.getInt("productivity"), newOrders, storedOrders));
        }

        for (int i = 0; i < jsonCouriers.length(); i++) {
            JSONObject jsonCook = jsonCouriers.getJSONObject(i);
            workers.add(new Courier(log, 300, jsonCook.getString("name"),
                    jsonCook.getInt("productivity"), jsonCook.getInt("bag_capacity"),
                    storedOrders, finishedOrders));
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
