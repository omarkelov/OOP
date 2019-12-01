package ru.nsu.fit.markelov.operations.binary;

/**
 * The <code>Exponentiation</code> class is a binary operation that is used to calculate the
 * exponentiation of two numbers.
 *
 * @author Oleg Markelov
 */
public class Exponentiation extends BinaryOperation {

    public static final String SYMBOL = "^";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return Math.pow(getOperand(0), getOperand(1));
    }
}
