package ru.nsu.fit.markelov.operations.unary;

public class SquareRoot extends UnaryOperation {

    public static final String SYMBOL = "sqrt";

    @Override
    public double calculate() {
        return Math.sqrt(getOperand(0));
    }
}
