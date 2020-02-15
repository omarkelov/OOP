package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.workers.Cook;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;

public class Order {

    public enum State {
        RECIEVED,
        BEING_COOKED,
        STORED,
        BEING_DELIVERED,
        DELIVERED
    }

    private State state;
    private String id;

    private Operator operator;
    private Cook cook;
    private Courier courier;

    public Order(String id, Operator operator) {
        state = State.RECIEVED;
        this.id = id;
        this.operator = operator;
    }

    public String getId() {
        return id;
    }

    public Operator getOperator() {
        return operator;
    }

    public Cook getCook() {
        return cook;
    }

    public void setCook(Cook cook) {
        this.cook = cook;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
