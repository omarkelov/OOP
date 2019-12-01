package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.operations.Operation;
import ru.nsu.fit.markelov.operations.binary.*;
import ru.nsu.fit.markelov.operations.unary.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * The <code>OperationFactory</code> provides a method for creating an <code>Operation</code> class
 * from an appropriate symbol. Also it is possible to modify the collection of operations adding
 * some new or removing the old ones.
 *
 * @author Oleg Markelov
 * @see    Operation
 */
public class OperationFactory {

    public static final String ARGUMENT_EXCEPTION_MESSAGE =
            "\"null\" is not a valid argument.";

    private Map<String, Class<?>> map;

    /**
     * Creates a new <code>OperationFactory</code> and initializes it with row of standard
     * <code>Operation</code> classes: Negation (~), SquareRoot (sqrt), Sinus (sin), Cosinus (cos),
     * Addition (+), Subtraction (-), Multiplication (*), Division (/), Exponentiation (^), Logarithm (log).
     */
    public OperationFactory() {
        initMap();
    }

    private void initMap() {
        map = new TreeMap<>();

        map.put(Addition.SYMBOL,       Addition.class);
        map.put(Subtraction.SYMBOL,    Subtraction.class);
        map.put(Multiplication.SYMBOL, Multiplication.class);
        map.put(Division.SYMBOL,       Division.class);
        map.put(Negation.SYMBOL,       Negation.class);
        map.put(Sinus.SYMBOL,          Sinus.class);
        map.put(Cosinus.SYMBOL,        Cosinus.class);
        map.put(Logarithm.SYMBOL,      Logarithm.class);
        map.put(Exponentiation.SYMBOL, Exponentiation.class);
        map.put(SquareRoot.SYMBOL,     SquareRoot.class);
    }

    /**
     * Creates and returns the <code>Operation</code> class created from the specified symbol.
     *
     * @param  symbol                 the operation symbol.
     * @return                        the <code>Operation</code> class created from the specified
     *                                symbol. Or null if there is no such operation in the
     *                                collection.
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible.
     * @throws InstantiationException if this Class represents an abstract class, an interface, an
     *                                array class, a primitive type, or void; or if the class has
     *                                no nullary constructor; or if the instantiation fails for some
     *                                other reason.
     */
    public Operation createOperation(String symbol) throws InstantiationException, IllegalAccessException {
        checkInput(symbol);

        if (map.containsKey(symbol)) {
            return (Operation) map.get(symbol).newInstance();
        } else {
            return null;
        }
    }

    /**
     * Adds the <code>Operation</code> class to the collection.
     *
     * @param symbol the symbol for <code>Operation</code> class.
     * @param clazz  the <code>Operation</code> class.
     */
    public void addOperation(String symbol, Class<?> clazz) {
        checkInput(symbol);
        checkInput(clazz);

        map.put(symbol, clazz);
    }

    /**
     * Removes the <code>Operation</code> class from the collection.
     *
     * @param symbol the symbol for <code>Operation</code> class.
     */
    public void removeOperation(String symbol) {
        checkInput(symbol);

        map.remove(symbol);
    }

    private <T> void checkInput(T arg) {
        if (arg == null) {
            throw new IllegalArgumentException(ARGUMENT_EXCEPTION_MESSAGE);
        }
    }
}
