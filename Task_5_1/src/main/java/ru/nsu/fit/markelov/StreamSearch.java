package ru.nsu.fit.markelov;

import java.util.ArrayList;

/**
 * StreamSearch class is used to detect, whether a composite number is present in the array. For
 * doing that Parallel Stream API is used.
 *
 * @author Oleg Markelov
 */
public class StreamSearch extends NonPrimeSearch {

    private ArrayList<Integer> numbers;

    /**
     * Creates a new StreamSearch with specified array of numbers.
     *
     * @param numbers the array of numbers.
     */
    public StreamSearch(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "parallelStream";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean find() {
        return numbers.parallelStream().anyMatch(PrimeNumbers::isNonPrime);
    }
}
