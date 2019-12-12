package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.operations.binary.*;
import ru.nsu.fit.markelov.operations.unary.*;

public class OperationFactoryTest {

    @Test
    public void testBasics() {
        OperationFactory operationFactory = new OperationFactory();

        try {
            Assert.assertEquals(Addition.class, operationFactory.createOperation(Addition.SYMBOL).getClass());
            Assert.assertEquals(Subtraction.class, operationFactory.createOperation(Subtraction.SYMBOL).getClass());
            Assert.assertEquals(Multiplication.class, operationFactory.createOperation(Multiplication.SYMBOL).getClass());
            Assert.assertEquals(Division.class, operationFactory.createOperation(Division.SYMBOL).getClass());
            Assert.assertEquals(Negation.class, operationFactory.createOperation(Negation.SYMBOL).getClass());
            Assert.assertEquals(Sinus.class, operationFactory.createOperation(Sinus.SYMBOL).getClass());
            Assert.assertEquals(Cosinus.class, operationFactory.createOperation(Cosinus.SYMBOL).getClass());
            Assert.assertEquals(Logarithm.class, operationFactory.createOperation(Logarithm.SYMBOL).getClass());
            Assert.assertEquals(Exponentiation.class, operationFactory.createOperation(Exponentiation.SYMBOL).getClass());
            Assert.assertEquals(SquareRoot.class, operationFactory.createOperation(SquareRoot.SYMBOL).getClass());

            Assert.assertNull(operationFactory.createOperation("qwerty"));
        } catch (InstantiationException|IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCollectionModification() {
        try {
            OperationFactory operationFactory = new OperationFactory();

            operationFactory.removeOperation(Addition.SYMBOL);
            Assert.assertNull(operationFactory.createOperation(Addition.SYMBOL));

            operationFactory.addOperation(Addition.SYMBOL, Addition.class);
            Assert.assertEquals(Addition.class, operationFactory.createOperation(Addition.SYMBOL).getClass());
        } catch (InstantiationException|IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testExceptions() {
        OperationFactory operationFactory = new OperationFactory();

        try {
            operationFactory.createOperation(null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(OperationFactory.NULL_ARGUMENT_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        } catch (InstantiationException|IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        try {
            operationFactory.addOperation("==", null);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(OperationFactory.NULL_ARGUMENT_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            operationFactory.addOperation("9", Addition.class);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(OperationFactory.SYMBOL_IS_NUMBER_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }
    }
}
