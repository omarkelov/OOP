package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class StackTest {
    @Test
    public void test1() {
        Stack<Integer> stack = new Stack<>();

        for (int i = 1; i <= 4; i++) {
            stack.push(i);
        }
        Assert.assertEquals(4, stack.count());

        int j = 0;
        Integer[] arr = new Integer[] {1, 2, 3, 4};
        Iterator iterator = stack.iterator();
        while (iterator.hasNext()) {
            Assert.assertEquals(arr[j++], iterator.next());
        }

        Assert.assertEquals(new Integer(4), stack.pop());
        Assert.assertEquals(new Integer(3), stack.pop());
        Assert.assertEquals(2, stack.count());

        for (int i = 11; i <= 14; i++) {
            stack.push(i);
        }
        Assert.assertEquals(6, stack.count());

        j = 0;
        arr = new Integer[] {1, 2, 11, 12, 13, 14};
        iterator = stack.iterator();
        while (iterator.hasNext()) {
            Assert.assertEquals(arr[j++], iterator.next());
        }

        Assert.assertEquals(new Integer(14), stack.pop());
        Assert.assertEquals(new Integer(13), stack.pop());
        Assert.assertEquals(new Integer(12), stack.pop());
        Assert.assertEquals(new Integer(11), stack.pop());
        Assert.assertEquals(new Integer(2), stack.pop());
        Assert.assertEquals(new Integer(1), stack.pop());
        Assert.assertNull(stack.pop());
        Assert.assertNull(stack.pop());
        Assert.assertEquals(0, stack.count());
    }
}
