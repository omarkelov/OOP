package ru.nsu.fit.markelov.operations;

public abstract class Operation {

    private int nReady;
    private int arity;
    private double[] operands;

    public Operation(int arity) {
        nReady = 0;
        this.arity = arity;
        operands = new double[arity];
    }

    public abstract double calculate();

    public void setOperand(double value) {
        operands[nReady++] = value;
    }

    public boolean isReady() {
        return nReady == arity;
    }

    protected double getOperand(int id) {
        return operands[id];
    }
}
