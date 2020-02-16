package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.workers.Cook;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;

public class Order {

    private String id;

    private Operator operator;
    private Cook cook;
    private Courier courier;

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Operator getOperator() {
        return operator;
    }

    public Order setOperator(Operator operator) {
        this.operator = operator;

        return this;
    }

    public Cook getCook() {
        return cook;
    }

    public Order setCook(Cook cook) {
        this.cook = cook;

        return this;
    }

    public Courier getCourier() {
        return courier;
    }

    public Order setCourier(Courier courier) {
        this.courier = courier;

        return this;
    }
}
