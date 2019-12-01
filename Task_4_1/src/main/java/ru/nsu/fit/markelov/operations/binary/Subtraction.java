package ru.nsu.fit.markelov.operations.binary;

/**
 * The <code>Subtraction</code> class is a binary operation that is used to calculate the
 * subtraction of two numbers.
 *
 * @author Oleg Markelov
 */
public class Subtraction extends BinaryOperation {

    public static final String SYMBOL = "-";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return getOperand(0) - getOperand(1);
    }
}
