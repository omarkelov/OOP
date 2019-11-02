package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class FilePatternMatcherTest {
    @Test
    public void test1() {
        String fileName = "src/test/txt/sample1.txt";
        FilePatternMatcher filePatternMatcher = new FilePatternMatcher('#'); // default buffer size

        try {
            Assert.assertArrayEquals(new int[] {0, 4, 6}, filePatternMatcher.matchAll(fileName, "aba"));
            Assert.assertArrayEquals(new int[] {1, 5, 7}, filePatternMatcher.matchAll(fileName, "ba"));
            Assert.assertArrayEquals(new int[] {0, 2, 4, 6, 8}, filePatternMatcher.matchAll(fileName, "a"));
        } catch (IOException e) {
            System.out.println(e.toString());
            Assert.fail();
        }
    }

    @Test
    public void test2() {
        String fileName = "src/test/txt/sample2.txt";
        String[] patterns = {
                " ",
                " я",
                "л.",
                "Volksmengepolitik",
                "Я и так это прекрасно понимаю. — Возразил я.",
                "Я остался один на один с Машинами. И полчаса, которые было необходимо чем-то занять."
        };

        for (String pattern : patterns) {
            for (int i = 0; i < 1000; i++) {
                Random random = new Random(i);

                int bufferSize = random.nextInt(255) + 1;

                FilePatternMatcher filePatternMatcher1 = new FilePatternMatcher('#'); // default buffer size
                FilePatternMatcher filePatternMatcher2 = new FilePatternMatcher('#', bufferSize); // random buffer size


                try {
                    int[] arr1 = filePatternMatcher1.matchAll(fileName, pattern);
                    int[] arr2 = filePatternMatcher2.matchAll(fileName, pattern);
                    Assert.assertArrayEquals(arr1, arr2);
                } catch (IOException e) {
                    System.out.println(e.toString());
                    Assert.fail();
                }
            }
        }
    }
}
