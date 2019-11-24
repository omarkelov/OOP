package ru.nsu.fit.markelov.operations;

public abstract class Operation {

    private int nOperandsSet;
    private int arity;
    private double[] operands;

    public Operation(int arity) {
        nOperandsSet = 0;
        this.arity = arity;
        operands = new double[arity];
    }

    public abstract double calculate();

    public void setOperand(double value) {
        operands[nOperandsSet++] = value;
    }

    public boolean isReadyToCalculate() {
        return nOperandsSet == arity;
    }

    protected double getOperand(int id) {
        return operands[id];
    }
}
