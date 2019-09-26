package ru.nsu.fit.markelov;

import org.junit.Test;

public class StackTest {
    @Test
    public void test1() {
        Stack<Integer> stack = new Stack();
        for (int i = 1; i <= 20; i++) {
            stack.push(i);
            stack.print();
        }
        for (int i = 1; i <= 10; i++) {
            stack.pop();
            stack.print();
        }
        for (int i = 1; i <= 10; i++) {
            stack.push(i+100);
            stack.print();
        }
    }
}
