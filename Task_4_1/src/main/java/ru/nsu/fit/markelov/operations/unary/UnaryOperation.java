package ru.nsu.fit.markelov.operations.unary;

import ru.nsu.fit.markelov.operations.Operation;

/**
 * The <code>BinaryOperation</code> class is an abstract class that is used to calculate the result
 * of one operand in a manner defined by its inheritors.
 *
 * @author Oleg Markelov
 */
public abstract class UnaryOperation extends Operation {
    public UnaryOperation() {
        super(1);
    }
}
