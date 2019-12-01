package ru.nsu.fit.markelov.operations.binary;

import ru.nsu.fit.markelov.operations.Operation;

/**
 * The <code>BinaryOperation</code> class is an abstract class that is used to calculate the result
 * of two operands in a manner defined by its inheritors.
 *
 * @author Oleg Markelov
 */
public abstract class BinaryOperation extends Operation {
    public BinaryOperation() {
        super(2);
    }
}
