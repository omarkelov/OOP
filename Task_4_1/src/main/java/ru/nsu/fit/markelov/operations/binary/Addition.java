package ru.nsu.fit.markelov.operations.binary;

/**
 * The <code>Addition</code> class is a binary operation that is used to calculate the addition of
 * two numbers.
 *
 * @author Oleg Markelov
 */
public class Addition extends BinaryOperation {

    public static final String SYMBOL = "+";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return getOperand(0) + getOperand(1);
    }
}
