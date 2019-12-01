package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.operations.Operation;
import ru.nsu.fit.markelov.operations.binary.Addition;
import ru.nsu.fit.markelov.operations.binary.Subtraction;
import ru.nsu.fit.markelov.operations.unary.Negation;

import java.util.NoSuchElementException;

public class OperationTest {

    private static final double DELTA = 0.000001d;

    @Test
    public void testUnary() {
        Operation operation = new Negation();

        Assert.assertFalse(operation.isReadyToCalculate());

        operation.setOperand(3);

        Assert.assertTrue(operation.isReadyToCalculate());
        Assert.assertEquals(-3, operation.calculate(), DELTA);
        Assert.assertEquals(3, operation.getOperand(0), DELTA);
    }

    @Test
    public void testBinary() {
        Operation operation = new Addition();

        Assert.assertFalse(operation.isReadyToCalculate());

        operation.setOperand(3);
        operation.setOperand(2);

        Assert.assertTrue(operation.isReadyToCalculate());
        Assert.assertEquals(5, operation.calculate(), DELTA);
        Assert.assertEquals(3, operation.getOperand(0), DELTA);
        Assert.assertEquals(2, operation.getOperand(1), DELTA);
    }

    @Test
    public void testExceptions() {
        Operation operation = new Subtraction();
        operation.setOperand(3);
        operation.setOperand(2);

        try {
            operation.setOperand(1);
            Assert.fail();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(Operation.SET_OPERAND_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            operation.getOperand(2);
            Assert.fail();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(Operation.GET_OPERAND_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }
    }
}
