package ru.nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SubstringInFileTest {
    @Test
    public void test1() {
        String fileName = "src/test/txt/sample1.txt";
        SubstringInFile substringInFile = new SubstringInFile(); // default buffer size
        print(substringInFile.find(fileName, "aba"));

        Assert.assertArrayEquals(new int[] {0, 4, 6}, substringInFile.find(fileName, "aba"));
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

                SubstringInFile substringInFile1 = new SubstringInFile(); // default buffer size
                SubstringInFile substringInFile2 = new SubstringInFile(bufferSize); // random buffer size

                int[] arr1 = substringInFile1.find(fileName, pattern);
                int[] arr2 = substringInFile2.find(fileName, pattern);
                /*System.out.println("String '" + pattern + "', " + "test " + i + ": " + bufferSize + " bufferSize");
                print(arr1);
                print(arr2);*/

                Assert.assertArrayEquals(arr1, arr2);
            }
        }
    }

    private void print(int[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i != arr.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}");
    }
}
