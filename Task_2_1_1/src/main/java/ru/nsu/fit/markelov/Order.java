package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.workers.Baker;
import ru.nsu.fit.markelov.workers.Courier;
import ru.nsu.fit.markelov.workers.Operator;

public class Order {

    private int id;
    private String name;

    private Operator operator;
    private Baker baker;
    private Courier courier;

    public Order(int id) {
        this.id = id;
        name = "Order_" + id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Operator getOperator() {
        return operator;
    }

    public Order setOperator(Operator operator) {
        this.operator = operator;

        return this;
    }

    public Baker getBaker() {
        return baker;
    }

    public Order setBaker(Baker baker) {
        this.baker = baker;

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
