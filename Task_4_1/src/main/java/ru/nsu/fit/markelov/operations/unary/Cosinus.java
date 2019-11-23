package ru.nsu.fit.markelov.operations.unary;

public class Cosinus extends UnaryOperation {

    public static final String SYMBOL = "cos";

    @Override
    public double calculate() {
        return Math.cos(getOperand(0));
    }
}
