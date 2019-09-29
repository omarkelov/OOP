package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class PriorityQueueTest {

    @Test
    public void test1() {
        PriorityQueue<Pair<Integer, String>> pairPriorityQueue = new PriorityQueue<>();
        Pair[] pairs = {
                new Pair<>(4, "1"), new Pair<>(5, "2"), new Pair<>(7, "3"), new Pair<>(2, "4"),
                new Pair<>(3, "5"), new Pair<>(6, "6"), new Pair<>(1, "7"), new Pair<>(8, "8")
        };
        for (Pair pair : pairs) {
            pairPriorityQueue.insert(pair);
        }
        Assert.assertEquals(8, pairPriorityQueue.count());

        int j = 0;
        Integer[] arr = new Integer[] {1, 3, 2, 5, 4, 7, 6, 8};
        Iterator iterator = pairPriorityQueue.iterator();
        while (iterator.hasNext()) {
            Assert.assertEquals(arr[j++], ((Pair) iterator.next()).getKey());
        }

        for (int i = 1; i <= 8; i++) {
            Assert.assertEquals(i, ((Pair) pairPriorityQueue.extract()).getKey());
        }
        Assert.assertEquals(0, pairPriorityQueue.count());

        Assert.assertNull(pairPriorityQueue.extract());
    }
}
