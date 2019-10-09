package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.EmptyStackException;

public class PriorityQueueTest {

    @Test
    public void test() {
        PriorityQueue<Pair<Integer, String>> pairPriorityQueue = new PriorityQueue<>();

        pairPriorityQueue.insert(new Pair<>(4, "1"));
        pairPriorityQueue.insert(new Pair<>(5, "2"));
        pairPriorityQueue.insert(new Pair<>(7, "3"));
        pairPriorityQueue.insert(new Pair<>(2, "4"));
        pairPriorityQueue.insert(new Pair<>(3, "5"));
        pairPriorityQueue.insert(new Pair<>(6, "6"));
        pairPriorityQueue.insert(new Pair<>(1, "7"));
        pairPriorityQueue.insert(new Pair<>(8, "8"));

        Assert.assertEquals(8, pairPriorityQueue.getCount());

        int j = 0;
        Integer[] arr = new Integer[] {1, 3, 2, 5, 4, 7, 6, 8};
        for (Pair pair : pairPriorityQueue) {
            Assert.assertEquals(arr[j++], pair.getKey());
        }

        for (int i = 1; i <= 8; i++) {
            Assert.assertEquals(i, ((Pair) pairPriorityQueue.extract()).getKey());
        }
        Assert.assertEquals(0, pairPriorityQueue.getCount());

        boolean exceptionCaught;
        try {
            pairPriorityQueue.extract();
            exceptionCaught = false;
        } catch (EmptyQueueException e) {
            exceptionCaught = true;
        }
        Assert.assertTrue(exceptionCaught);
    }
}
