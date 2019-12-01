package ru.nsu.fit.markelov.operations.unary;

/**
 * The <code>SquareRoot</code> class is a unary operation that is used to calculate the square root
 * of the number.
 *
 * @author Oleg Markelov
 */
public class SquareRoot extends UnaryOperation {

    public static final String SYMBOL = "sqrt";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return Math.sqrt(getOperand(0));
    }
}
