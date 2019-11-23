package ru.nsu.fit.markelov.operations.unary;

public class Sinus extends UnaryOperation {

    public static final String SYMBOL = "sin";

    @Override
    public double calculate() {
        return Math.sin(getOperand(0));
    }
}
