package ru.nsu.fit.markelov.operations.binary;

/**
 * The <code>Logarithm</code> class is a binary operation that is used to calculate the logarithm of
 * two numbers.
 *
 * @author Oleg Markelov
 */
public class Logarithm extends BinaryOperation {

    public static final String SYMBOL = "log";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return Math.log(getOperand(1)) / Math.log(getOperand(0));
    }
}
