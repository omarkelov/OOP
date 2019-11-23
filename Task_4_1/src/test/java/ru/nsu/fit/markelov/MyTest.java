package ru.nsu.fit.markelov;

import org.junit.Test;

public class MyTest {

    @Test
    public void test() {
        Calculator calculator = new Calculator();
        try {
            System.out.println(calculator.calculate("~ ^ 5 ~ sin + - 1 2 sqrt 1"));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
