package ru.nsu.fit.markelov.properties;

import org.json.JSONObject;

public class CookProperties {

    private String name;
    private long orderHandlingTime;

    public CookProperties(JSONObject jsonObject) {
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
