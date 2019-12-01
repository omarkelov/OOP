package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.operations.Operation;
import ru.nsu.fit.markelov.operations.binary.*;
import ru.nsu.fit.markelov.operations.unary.*;

import java.util.Map;
import java.util.TreeMap;

public class OperationFactory {

    public static final String SYMBOL_EXCEPTION_MESSAGE =
            "\"null\" is not a valid operation symbol.";

    private Map<String, Class<?>> map;

    public OperationFactory() {
        initMap();
    }

    private void initMap() {
        map = new TreeMap<>();

        map.put(Plus.SYMBOL,           Plus.class);
        map.put(Minus.SYMBOL,          Minus.class);
        map.put(Multiplication.SYMBOL, Multiplication.class);
        map.put(Division.SYMBOL,       Division.class);
        map.put(Negation.SYMBOL,       Negation.class);
        map.put(Sinus.SYMBOL,          Sinus.class);
        map.put(Cosinus.SYMBOL,        Cosinus.class);
        map.put(Logarithm.SYMBOL,      Logarithm.class);
        map.put(Exponentiation.SYMBOL, Exponentiation.class);
        map.put(SquareRoot.SYMBOL,     SquareRoot.class);
    }

    public Operation createOperation(String symbol) throws InstantiationException, IllegalAccessException {
        checkInput(symbol);

        if (map.containsKey(symbol)) {
            return (Operation) map.get(symbol).newInstance();
        } else {
            return null;
        }
    }

    public void addOperation(String symbol, Class<?> clazz) {
        checkInput(symbol);
        map.put(symbol, clazz);
    }

    public void removeOperation(String symbol) {
        checkInput(symbol);
        map.remove(symbol);
    }

    private void checkInput(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException(SYMBOL_EXCEPTION_MESSAGE);
        }
    }
}
