package ru.nsu.fit.markelov.properties;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PizzeriaProperties {

    private int workingTime;
    private int storageCapacity;
    private List<OperatorProperties> operatorPropertiesList;
    private List<CookProperties> cookPropertiesList;
    private List<CourierProperties> courierPropertiesList;

    public PizzeriaProperties(JSONObject jsonObject) {
        workingTime = jsonObject.getInt("workingTime");
        storageCapacity = jsonObject.getInt("storageCapacity");

        operatorPropertiesList = new ArrayList<>();
        JSONArray jsonOperators = jsonObject.getJSONArray("operators");
        for (int i = 0; i < jsonOperators.length(); i++) {
            operatorPropertiesList.add(new OperatorProperties(jsonOperators.getJSONObject(i)));
        }

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

    public int getWorkingTime() {
        return workingTime;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public List<OperatorProperties> getOperatorPropertiesList() {
        return operatorPropertiesList;
    }

    public List<CookProperties> getCookPropertiesList() {
        return cookPropertiesList;
    }

    public List<CourierProperties> getCourierPropertiesList() {
        return courierPropertiesList;
    }
}
