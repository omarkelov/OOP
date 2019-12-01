package ru.nsu.fit.markelov.operations.binary;

/**
 * The <code>Division</code> class is a binary operation that is used to calculate the division of
 * two numbers.
 *
 * @author Oleg Markelov
 */
public class Division extends BinaryOperation {

    public static final String SYMBOL = "/";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return getOperand(0) / getOperand(1);
    }
}
