package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class ZFunctionTest {
    @Test
    public void test() {
        String s = "abacababa";
        ZFunction zFunction = new ZFunction(s.toCharArray(), s.length(), '#', "aba".toCharArray());
        Assert.assertArrayEquals(new int[] {0, 4, 6}, zFunction.getPositions());
    }
}
