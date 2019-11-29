package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.operations.Operation;
import ru.nsu.fit.markelov.operations.binary.*;
import ru.nsu.fit.markelov.operations.unary.*;

import java.util.Map;
import java.util.TreeMap;

public class OperationFactory {

    private Map<String, Class<?>> map;

    public OperationFactory() {
        initMap();
    }

    private void initMap() {
        map = new TreeMap<>();

        map.put("+",    Plus.class);
        map.put("-",    Minus.class);
        map.put("*",    Multiplication.class);
        map.put("/",    Division.class);
        map.put("~",    Negation.class);
        map.put("sin",  Sinus.class);
        map.put("cos",  Cosinus.class);
        map.put("log",  Logarithm.class);
        map.put("^",    Exponentiation.class);
        map.put("sqrt", SquareRoot.class);
    }

    public Operation createOperation(String token) throws InstantiationException, IllegalAccessException {
        return (Operation) map.get(token).newInstance();
    }

    public void addOperation(String token, Class<?> clazz) {
        map.put(token, clazz);
    }

    public void removeOperation(String token) {
        map.remove(token);
    }
}
