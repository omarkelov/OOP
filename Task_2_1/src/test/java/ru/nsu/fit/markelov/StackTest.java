package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.EmptyStackException;

public class StackTest {
    @Test
    public void test() {
        Stack<Integer> stack = new Stack<>(2);

        for (int i = 1; i <= 4; i++) {
            stack.push(i);
        }
        Assert.assertEquals(4, stack.getCapacity());

        stack.push(5);
        Assert.assertEquals(5, stack.count());
        Assert.assertEquals(8, stack.getCapacity());

        int j = 0;
        Integer[] arr = new Integer[] {1, 2, 3, 4, 5};
        for (Integer i : stack) {
            Assert.assertEquals(arr[j++], i);
        }

        Assert.assertEquals(new Integer(5), stack.pop());
        Assert.assertEquals(new Integer(4), stack.pop());
        Assert.assertEquals(3, stack.count());
        Assert.assertEquals(8, stack.getCapacity());

        for (int i = 11; i <= 14; i++) {
            stack.push(i);
        }
        Assert.assertEquals(7, stack.count());
        Assert.assertEquals(8, stack.getCapacity());

        j = 0;
        arr = new Integer[] {1, 2, 3, 11, 12, 13, 14};
        for (Integer i : stack) {
            Assert.assertEquals(arr[j++], i);
        }

        Assert.assertEquals(new Integer(14), stack.pop());
        Assert.assertEquals(new Integer(13), stack.pop());
        Assert.assertEquals(new Integer(12), stack.pop());
        Assert.assertEquals(new Integer(11), stack.pop());
        Assert.assertEquals(8, stack.getCapacity());
        Assert.assertEquals(new Integer(3), stack.pop());
        Assert.assertEquals(4, stack.getCapacity());
        Assert.assertEquals(new Integer(2), stack.pop());
        Assert.assertEquals(2, stack.getCapacity());
        Assert.assertEquals(new Integer(1), stack.pop());
        Assert.assertEquals(1, stack.getCapacity());

        boolean exceptionCaught;
        try {
            stack.pop();
            exceptionCaught = false;
        } catch (EmptyStackException e) {
            exceptionCaught = true;
        }
        Assert.assertTrue(exceptionCaught);
    }
}
