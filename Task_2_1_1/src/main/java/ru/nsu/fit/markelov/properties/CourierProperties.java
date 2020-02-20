package ru.nsu.fit.markelov.properties;

import org.json.JSONObject;

public class CourierProperties {

    private String name;
    private long time;
    private int bagCapacity;

    public CourierProperties(JSONObject jsonObject) {
        name = jsonObject.getString("name");
        time = jsonObject.getLong("productivity");
        bagCapacity = jsonObject.getInt("bag_capacity");
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public int getBagCapacity() {
        return bagCapacity;
    }
}
