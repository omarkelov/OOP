package ru.nsu.fit.markelov.operations;

import java.util.NoSuchElementException;

public abstract class Operation {

    public static final String GET_OPERAND_EXCEPTION_MESSAGE =
            "All the operands have already been set.";
    public static final String SET_OPERAND_EXCEPTION_MESSAGE =
            "The operand has not been set yet.";

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
        if (nOperandsSet > arity) {
            throw new NoSuchElementException(GET_OPERAND_EXCEPTION_MESSAGE);
        }

        operands[nOperandsSet++] = value;
    }

    public boolean isReadyToCalculate() {
        return nOperandsSet == arity;
    }

    protected double getOperand(int id) {
        if (id > nOperandsSet) {
            throw new NoSuchElementException(SET_OPERAND_EXCEPTION_MESSAGE);
        }

        return operands[id];
    }
}
