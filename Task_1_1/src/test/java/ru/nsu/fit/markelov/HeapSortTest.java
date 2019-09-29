package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class HeapSortTest {
    @Test
    public void test() {
        HeapSort<String> strHeapSort = new HeapSort<>();
        HeapSort<Integer> intHeapSort = new HeapSort<>();
        Comparator<Integer> comparator = (Integer a, Integer b) -> {
            return b - a;
        };

        Assert.assertArrayEquals(
                new String[] {"a", "b", "c", "d", "e"},
                strHeapSort.sort(new String[] {"a", "c", "b", "e", "d"}, null)
        );
        Assert.assertArrayEquals(
                new Integer[] {5, 4, 3, 2, 1},
                intHeapSort.sort(new Integer[] {5, 4, 3, 2, 1}, comparator)
        );
        Assert.assertArrayEquals(
                new Integer[] {-9, -7, 0, 0, 0, 0, 5, 5, 27, 100},
                intHeapSort.sort(new Integer[] {100, -9, 0, 5, 0, 0, 27, 0, -7, 5})
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

            Assert.assertArrayEquals(arrayExpected, intHeapSort.sort(array, comparator));
        }
    }
}
