package ru.nsu.fit.markelov.util;

import org.junit.Assert;
import org.junit.Test;

public class IterativeIntGeneratorTest {
    @Test
    public void testBasics() {
        IterativeIntGenerator iterativeIntGenerator = new IterativeIntGenerator();
        Assert.assertEquals(1, iterativeIntGenerator.getNextInt());
        Assert.assertEquals(2, iterativeIntGenerator.getNextInt());
        Assert.assertEquals(3, iterativeIntGenerator.getNextInt());
    }
}
