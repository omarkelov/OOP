package ru.nsu.fit.markelov;

import org.junit.Test;

public class CalculatorTest {

    @Test
    public void test() {
        Calculator calculator = new Calculator();
        try {
            System.out.println(calculator.calculate("~ ^ 5 ~ sin + - 1 2 sqrt 1"));
        } catch (IllegalArgumentException|InstantiationException|IllegalAccessException e) {
            System.out.println("Error (" + e.getMessage() + ")");
        }
    }
}
