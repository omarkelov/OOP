package ru.nsu.fit.markelov.operations.binary;

/**
 * The <code>Multiplication</code> class is a binary operation that is used to calculate the
 * multiplication of two numbers.
 *
 * @author Oleg Markelov
 */
public class Multiplication extends BinaryOperation {

    public static final String SYMBOL = "*";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return getOperand(0) * getOperand(1);
    }
}
