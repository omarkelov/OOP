package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class ZFunctionTest {
    @Test
    public void test() {
        String s = "aba#abacababa";
        Assert.assertArrayEquals(
                new int[] {0, 0, 1, 0, 3, 0, 1, 0, 3, 0, 3, 0, 1},
                ZFunction.getZValues(s.toCharArray())
        );
    }
}
