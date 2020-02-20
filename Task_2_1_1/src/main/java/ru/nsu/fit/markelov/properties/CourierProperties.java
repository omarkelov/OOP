package ru.nsu.fit.markelov.properties;

import org.json.JSONObject;

public class CourierProperties {

    private String name;
    private long orderHandlingTime;
    private int bagCapacity;

    public CourierProperties(JSONObject jsonObject) {
        name = jsonObject.getString("name");
        orderHandlingTime = jsonObject.getLong("orderHandlingTime");
        bagCapacity = jsonObject.getInt("bagCapacity");
    }

    public String getName() {
        return name;
    }

    public long getOrderHandlingTime() {
        return orderHandlingTime;
    }

    public int getBagCapacity() {
        return bagCapacity;
    }
}
