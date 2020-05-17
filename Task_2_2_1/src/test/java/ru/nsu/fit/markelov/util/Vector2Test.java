package ru.nsu.fit.markelov.util;

import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

public class Vector2Test {
    @Test
    public void testEqualsTrue() {
        try {
            Assert.assertTrue(new Vector2(0, 0).equals(new Vector2(0, 0)));
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testEqualsFalse() {
        try {
            Assert.assertFalse(new Vector2(1, 0).equals(new Vector2(0, 0)));
        } catch (IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testEqualsNull() {
        boolean exceptionCaught = false;

        try {
            new Vector2(0, 0).equals(null);
        } catch (IllegalInputException e) {
            exceptionCaught = true;
        }

        Assert.assertTrue(exceptionCaught);
    }
}
