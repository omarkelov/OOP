package ru.nsu.fit.markelov.operations.binary;

public class Logarithm extends BinaryOperation {

    public static final String SYMBOL = "log";

    @Override
    public double calculate() {
        return Math.log(getOperand(1)) / Math.log(getOperand(0));
    }
}
