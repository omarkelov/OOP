package ru.nsu.fit.markelov.operations.unary;

/**
 * The <code>Negation</code> class is a unary operation that is used to calculate the negation of
 * the number.
 *
 * @author Oleg Markelov
 */
public class Negation extends UnaryOperation {

    public static final String SYMBOL = "~";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return -getOperand(0);
    }
}
