package ru.nsu.fit.markelov;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolSearch extends NonPrimeSearch {

    private ArrayList<Integer> numbers;
    int nThreads;

    public ThreadPoolSearch(int nThreads, ArrayList<Integer> numbers) {
        this.nThreads = nThreads;
        this.numbers = numbers;
    }

    @Override
    public String getType() {
        return "ThreadPool";
    }

    @Override
    protected boolean find() {
        ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);

        ArrayList<Future<Boolean>> futures = new ArrayList<>();
        for (Integer number : numbers) {
            futures.add(CompletableFuture.supplyAsync(() -> PrimeNumbers.isNonPrime(number), threadPool));
        }

        boolean nonPrimeFound = false;
        for (Future<Boolean> future : futures) {
            try {
                if (future.get()) {
                    nonPrimeFound = true;
                    break;
                }
            } catch (ExecutionException|InterruptedException e) {
                System.out.println(e.toString());
            }
        }

        threadPool.shutdown();

        return nonPrimeFound;
    }
}
