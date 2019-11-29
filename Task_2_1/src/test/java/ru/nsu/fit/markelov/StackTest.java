package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackTest {

    @Test
    public void testPush() {
        Stack<Integer> stack = new Stack<>(2);

        for (int i = 1; i <= 4; i++) {
            stack.push(i);
        }

        int j = 0;
        Integer[] arr = new Integer[] {1, 2, 3, 4};
        for (Integer i : stack) {
            Assert.assertEquals(arr[j++], i);
        }
    }

    @Test
    public void testPop() {
        Stack<Integer> stack = new Stack<>(2);

        for (int i = 1; i <= 5; i++) {
            stack.push(i);
        }

        Assert.assertEquals(new Integer(5), stack.pop());
        Assert.assertEquals(new Integer(4), stack.pop());

        int j = 0;
        Integer[] arr = new Integer[] {1, 2, 3};
        for (Integer i : stack) {
            Assert.assertEquals(arr[j++], i);
        }
    }

    @Test
    public void testCountAndCapacity() {
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

        stack.pop();
        stack.pop();
        Assert.assertEquals(3, stack.count());
        Assert.assertEquals(8, stack.getCapacity());

        for (int i = 11; i <= 14; i++) {
            stack.push(i);
        }
        Assert.assertEquals(7, stack.count());
        Assert.assertEquals(8, stack.getCapacity());

        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        Assert.assertEquals(8, stack.getCapacity());
        stack.pop();
        Assert.assertEquals(4, stack.getCapacity());
        stack.pop();
        Assert.assertEquals(2, stack.getCapacity());
        stack.pop();
        Assert.assertEquals(1, stack.getCapacity());
    }

    @Test
    public void testEmptyStackException() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.pop();
            Assert.fail();
        } catch (EmptyStackException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught.");
        }
    }

    @Test
    public void testNoSuchElementException() {
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= 4; i++) {
            stack.push(i);
        }

        Iterator iterator = stack.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        try {
            iterator.next();
            Assert.fail();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught.");
        }
    }

    @Test
    public void testNoSuchElementExceptionEmptyStack() {
        Stack<Integer> stack = new Stack<>();
        try {
            stack.iterator().next();
            Assert.fail();
        } catch (NoSuchElementException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught.");
        }
    }
}
