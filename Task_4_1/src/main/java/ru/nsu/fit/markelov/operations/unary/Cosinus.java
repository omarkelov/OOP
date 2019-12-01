package ru.nsu.fit.markelov.operations.unary;

/**
 * The <code>Cosinus</code> class is a unary operation that is used to calculate the cosinus of the
 * number.
 *
 * @author Oleg Markelov
 */
public class Cosinus extends UnaryOperation {

    public static final String SYMBOL = "cos";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return Math.cos(getOperand(0));
    }
}
