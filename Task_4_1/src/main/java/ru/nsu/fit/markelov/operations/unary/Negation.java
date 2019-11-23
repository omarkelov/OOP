package ru.nsu.fit.markelov.operations.unary;

public class Negation extends UnaryOperation {

    public static final String SYMBOL = "~";

    @Override
    public double calculate() {
        return -getOperand(0);
    }
}
