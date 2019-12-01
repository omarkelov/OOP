package ru.nsu.fit.markelov.operations.unary;

/**
 * The <code>Sinus</code> class is a unary operation that is used to calculate the sinus of the
 * number.
 *
 * @author Oleg Markelov
 */
public class Sinus extends UnaryOperation {

    public static final String SYMBOL = "sin";

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculate() {
        return Math.sin(getOperand(0));
    }
}
