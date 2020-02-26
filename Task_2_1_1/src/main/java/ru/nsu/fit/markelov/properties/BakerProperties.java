package ru.nsu.fit.markelov.properties;

import org.json.JSONObject;

public class BakerProperties {

    private String name;
    private long orderHandlingTime;

    public BakerProperties(JSONObject jsonObject) {
        name = jsonObject.getString("name");
        orderHandlingTime = jsonObject.getLong("orderHandlingTime");
    }

    public String getName() {
        return name;
    }

    public long getOrderHandlingTime() {
        return orderHandlingTime;
    }
}
