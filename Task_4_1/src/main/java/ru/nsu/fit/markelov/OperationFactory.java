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

    public static final String NULL_ARGUMENT_EXCEPTION_MESSAGE =
            "\"null\" is not a valid argument.";
    public static final String SYMBOL_IS_NUMBER_EXCEPTION_MESSAGE =
            "Number is not a valid operation's symbol: ";

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
        checkForNull(symbol);

        if (map.containsKey(symbol)) {
            return (Operation) map.get(symbol).newInstance();
        } else {
            return null;
        }
    }

    /**
     * Adds the <code>Operation</code> class to the collection.
     *
     * @param symbol the symbol for <code>Operation</code> class. Any sequence of chars can be a
     *               valid operation's symbol, except one that can be interpreted as number.
     * @param clazz  the <code>Operation</code> class.
     */
    public void addOperation(String symbol, Class<?> clazz) {
        checkForNull(symbol);
        checkForNull(clazz);

        try {
            Double.parseDouble(symbol);

            // exception was not caught - "symbol" is a number
            throw new IllegalArgumentException(SYMBOL_IS_NUMBER_EXCEPTION_MESSAGE +
                    "\"" + symbol + "\"");
        } catch (NumberFormatException e) {
            // normal case - "symbol" is not a number
        }

        map.put(symbol, clazz);
    }

    /**
     * Removes the <code>Operation</code> class from the collection.
     *
     * @param symbol the symbol for <code>Operation</code> class.
     */
    public void removeOperation(String symbol) {
        checkForNull(symbol);

        map.remove(symbol);
    }

    private <T> void checkForNull(T arg) {
        if (arg == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT_EXCEPTION_MESSAGE);
        }
    }
}
