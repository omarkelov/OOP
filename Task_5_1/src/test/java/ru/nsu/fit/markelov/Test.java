package ru.nsu.fit.markelov;

import org.junit.Assert;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    private final ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();

    private final class Task implements Runnable {

        private List<Integer> sublist;
        private int id;

        public Task(List<Integer> sublist, int id) {
            this.sublist = sublist;
            this.id = id;
        }

        @Override
        public void run() {
            for (Integer elem : sublist) {
                synchronized (list) {
                    list.add(new Pair<>(elem, id));
                }
            }
        }
    }

    @org.junit.Test
    public void test() {
        long startNanoTime;
        boolean nonPrimeFound;
        ArrayList<Integer> numbers = InputArrayGenerator.generate(10000, 1, new Random(5));

        Task task1 = new Task(numbers.subList(0, numbers.size() / 2), 1);
        Task task2 = new Task(numbers.subList(numbers.size() / 2, numbers.size()), 2);
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        thread1.start();
        thread2.start();

        try {
            wait(1000);
            System.out.println(list);
        } catch (InterruptedException e) {

        }


        // -----------------------------------------------------------------------------------------

        /*startNanoTime = System.nanoTime();

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

//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        ThreadPool threadPool = new ThreadPool(5);

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

        System.out.println(System.nanoTime() - startNanoTime);*/

        /*startNanoTime = System.nanoTime();
        Assert.assertTrue(numbers.parallelStream().anyMatch(PrimeNumbers::isNonPrime));
        System.out.println(System.nanoTime() - startNanoTime);*/
    }
}
