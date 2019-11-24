package ru.nsu.fit.markelov.operations;

import ru.nsu.fit.markelov.operations.binary.*;
import ru.nsu.fit.markelov.operations.unary.*;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class OperationMap {

    public static final Map<String, Class<?>> MAP;

    static {
        Map<String, Class<?>> map = new TreeMap<>();

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

        MAP = Collections.unmodifiableMap(map);
    }
}
