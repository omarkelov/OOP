package ru.nsu.fit.markelov.operations.binary;

public class Division extends BinaryOperation {

    public static final String SYMBOL = "/";

    @Override
    public double calculate() {
        return getOperand(0) / getOperand(1);
    }
}
