package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class HeapSortTest {
    @Test
    public void test() {
        HeapSort heapSort = new HeapSort();
        Comparator comparator = (Object a, Object b) -> {
            return (Integer) a - (Integer) b;
        };

        Assert.assertArrayEquals(
                new Integer[] {1, 2, 3, 4, 5},
                heapSort.sort(new Integer[] {5, 4, 3, 2, 1}, comparator)
        );
        Assert.assertArrayEquals(
                new Integer[] {-9, -7, 0, 0, 0, 0, 5, 5, 27, 100},
                heapSort.sort(new Integer[] {100, -9, 0, 5, 0, 0, 27, 0, -7, 5}, comparator)
        );

        for (int i = 0; i < 1000; i++) {
            Random random = new Random(i);

            int len = random.nextInt(1000);
            Integer[] array = new Integer[len];
            for (int j = 0; j < len; j++) {
                array[j] = random.nextInt(len + 1) - len / 2;
            }

            Integer[] arrayExpected = array.clone();
            Arrays.sort(arrayExpected, comparator);

            Assert.assertArrayEquals(arrayExpected, heapSort.sort(array, comparator));
        }
    }
}
