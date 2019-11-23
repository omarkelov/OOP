package ru.nsu.fit.markelov.operations.binary;

public class Exponentiation extends BinaryOperation {

    public static final String SYMBOL = "^";

    @Override
    public double calculate() {
        return Math.pow(getOperand(0), getOperand(1));
    }
}
