package ru.nsu.fit.markelov.properties;

import org.json.JSONObject;

public class OperatorProperties {

    private String name;
    private long orderHandlingTimeMin;
    private long orderHandlingTimeMax;

    public OperatorProperties(JSONObject jsonObject) {
        name = jsonObject.getString("name");
        orderHandlingTimeMin = jsonObject.getLong("orderHandlingTimeMin");
        orderHandlingTimeMax = jsonObject.getLong("orderHandlingTimeMax");
    }

    public String getName() {
        return name;
    }

    public long getOrderHandlingTimeMin() {
        return orderHandlingTimeMin;
    }

    public long getOrderHandlingTimeMax() {
        return orderHandlingTimeMax;
    }
}
