package ru.nsu.fit.markelov.properties;

import org.json.JSONObject;

public class CookProperties {

    private String name;
    private long time;

    public CookProperties(JSONObject jsonObject) {
        name = jsonObject.getString("name");
        time = jsonObject.getLong("productivity");
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }
}
