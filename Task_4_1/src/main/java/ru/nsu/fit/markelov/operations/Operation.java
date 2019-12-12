package ru.nsu.fit.markelov.operations;

import java.util.NoSuchElementException;

/**
 * The <code>Operation</code> class is an abstract class that is used to calculate the result of one
 * or more operands in a manner defined by its descendants.
 *
 * @author Oleg Markelov
 */
public abstract class Operation {

    public static final String SET_OPERAND_EXCEPTION_MESSAGE =
            "All the operands have already been set. An extra one has been found: ";
    public static final String GET_OPERAND_EXCEPTION_MESSAGE =
            "The operand has not been set yet.";

    private int nOperandsSet;
    private int arity;
    private double[] operands;

    /**
     * Creates a new <code>Operation</code> of specified arity.
     *
     * @param arity the arity of the operation.
     */
    public Operation(int arity) {
        nOperandsSet = 0;
        this.arity = arity;
        operands = new double[arity];
    }

    /**
     * Returns the result of calculation.
     *
     * @return the result of calculation.
     */
    public abstract double calculate();

    /**
     * Sets the next operand of this operation.
     *
     * @param value the value of the operand.
     */
    public void setOperand(double value) {
        if (nOperandsSet >= arity) {
            throw new NoSuchElementException(SET_OPERAND_EXCEPTION_MESSAGE + "\"" + value + "\"");
        }

        operands[nOperandsSet++] = value;
    }

    /**
     * Returns whether all the operands are correctly set.
     *
     * @return whether all the operands are correctly set.
     */
    public boolean isReadyToCalculate() {
        return nOperandsSet == arity;
    }

    /**
     * Returns the value of the operand.
     *
     * @param  id the number of the operand.
     * @return    the value of the operand.
     */
    public double getOperand(int id) {
        if (id >= nOperandsSet) {
            throw new NoSuchElementException(GET_OPERAND_EXCEPTION_MESSAGE);
        }

        return operands[id];
    }
}
