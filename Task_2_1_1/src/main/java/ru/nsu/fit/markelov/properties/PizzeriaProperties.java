package ru.nsu.fit.markelov.properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PizzeriaProperties {

    private int storageCapacity;
    private List<CookProperties> cookPropertiesList;
    private List<CourierProperties> courierPropertiesList;

    public PizzeriaProperties(JSONObject jsonObject) {
        storageCapacity = jsonObject.getInt("storage_capacity");

        cookPropertiesList = new ArrayList<>();
        JSONArray jsonCooks = jsonObject.getJSONArray("cooks");
        for (int i = 0; i < jsonCooks.length(); i++) {
            cookPropertiesList.add(new CookProperties(jsonCooks.getJSONObject(i)));
        }

        courierPropertiesList = new ArrayList<>();
        JSONArray jsonCouriers = jsonObject.getJSONArray("couriers");
        for (int i = 0; i < jsonCouriers.length(); i++) {
            courierPropertiesList.add(new CourierProperties(jsonCouriers.getJSONObject(i)));
        }
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public List<CookProperties> getCookPropertiesList() {
        return cookPropertiesList;
    }

    public List<CourierProperties> getCourierPropertiesList() {
        return courierPropertiesList;
    }
}
