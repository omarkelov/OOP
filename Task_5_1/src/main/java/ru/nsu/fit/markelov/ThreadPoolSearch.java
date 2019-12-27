package ru.nsu.fit.markelov;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ThreadPoolSearch class is used to detect, whether a composite number is present in the array. For
 * doing that Thread Pool is used.
 *
 * @author Oleg Markelov
 */
public class ThreadPoolSearch extends NonPrimeSearch {

    private ArrayList<Integer> numbers;
    int nThreads;

    /**
     * Creates a new ThreadPoolSearch with specified amount of threads and array of numbers.
     *
     * @param nThreads the amount of threads.
     * @param numbers  the array of numbers.
     */
    public ThreadPoolSearch(int nThreads, ArrayList<Integer> numbers) {
        this.nThreads = nThreads;
        this.numbers = numbers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "ThreadPool";
    }

    /**
     * {@inheritDoc}
     */
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
