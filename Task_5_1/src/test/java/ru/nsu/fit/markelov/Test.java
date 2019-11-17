package ru.nsu.fit.markelov;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class Test {

    @org.junit.Test
    public void test() {
        long startNanoTime;
        boolean nonPrimeFound;
        ArrayList<Integer> numbers = InputArrayGenerator.generate(10000, 1000, new Random(5));

        // -----------------------------------------------------------------------------------------

        startNanoTime = System.nanoTime();

        nonPrimeFound = false;
        for (Integer number : numbers) {
            if (!PrimeNumbers.isPrime(number)) {
                nonPrimeFound = true;
                break;
            }
        }

        Assert.assertTrue(nonPrimeFound);

        System.out.println(System.nanoTime() - startNanoTime);

        // -----------------------------------------------------------------------------------------

        startNanoTime = System.nanoTime();

        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        ArrayList<Future<Boolean>> futures = new ArrayList<>();
        for (Integer number : numbers) {
            futures.add(CompletableFuture.supplyAsync(() -> PrimeNumbers.isPrime(number), threadPool));
        }

        nonPrimeFound = false;
        for (Future<Boolean> future : futures) {
            try {
                if (!future.get()) {
                    nonPrimeFound = true;
                    break;
                }
            } catch (ExecutionException|InterruptedException e) {
                System.out.println(e.toString());
            }
        }

        threadPool.shutdown();

        Assert.assertTrue(nonPrimeFound);

        System.out.println(System.nanoTime() - startNanoTime);

        /*startNanoTime = System.nanoTime();
        Assert.assertTrue(numbers.stream().anyMatch(PrimeNumbers::isNonPrime));
        System.out.println(System.nanoTime() - startNanoTime);*/
    }
}
