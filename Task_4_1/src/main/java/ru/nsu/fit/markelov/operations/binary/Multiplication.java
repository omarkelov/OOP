package ru.nsu.fit.markelov.operations.binary;

public class Multiplication extends BinaryOperation {

    public static final String SYMBOL = "*";

    @Override
    public double calculate() {
        return getOperand(0) * getOperand(1);
    }
}
