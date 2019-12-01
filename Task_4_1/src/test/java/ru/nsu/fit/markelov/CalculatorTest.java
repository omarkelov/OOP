package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

    private static final double DELTA = 0.000001d;

    @Test
    public void testBasics() {
        Calculator calculator = new Calculator();
        Assert.assertEquals(5, calculator.calculate("+ 3 2"), DELTA);
        Assert.assertEquals(1, calculator.calculate("- 3 2"), DELTA);
        Assert.assertEquals(6, calculator.calculate("* 3 2"), DELTA);
        Assert.assertEquals(1.5d, calculator.calculate("/ 3 2"), DELTA);
        Assert.assertEquals(9, calculator.calculate("^ 3 2"), DELTA);
        Assert.assertEquals(0.63092975357d, calculator.calculate("log 3 2"), DELTA);
        Assert.assertEquals(-3, calculator.calculate("~ 3"), DELTA);
        Assert.assertEquals(1.73205080756d, calculator.calculate("sqrt 3"), DELTA);
        Assert.assertEquals(0.14112000805d, calculator.calculate("sin 3"), DELTA);
        Assert.assertEquals(-0.9899924966d, calculator.calculate("cos 3"), DELTA);
    }

    @Test
    public void testComplex() {
        Calculator calculator = new Calculator();
        Assert.assertEquals(0, calculator.calculate("sin + - 1 2 1"), DELTA);
        Assert.assertEquals(-3.9885581113d, calculator.calculate("~ ^ 12.34 ~ sin + - 10.67 2.353333 sqrt 99"), DELTA);
    }

    @Test
    public void testExceptions() {
        Calculator calculator = new Calculator();

        try {
            calculator.calculate("sin + - 1 2 1 999");
            Assert.fail();
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().startsWith(Calculator.EXTRA_OPERAND_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            calculator.calculate("sinus + - 1 2 1");
            Assert.fail();
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().startsWith(Calculator.INVALID_TOKEN_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            calculator.calculate("sin + - 1 2");
            Assert.fail();
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().startsWith(Calculator.NO_OPERANDS_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }
    }
}
