package ru.nsu.fit.markelov;

import java.util.ArrayList;

/**
 * LinearSearch class is used to detect, whether a composite number is present in the array. For
 * doing that simple linear search is used.
 *
 * @author Oleg Markelov
 */
public class LinearSearch extends NonPrimeSearch {

    private ArrayList<Integer> numbers;

    /**
     * Creates a new LinearSearch with specified array of numbers.
     *
     * @param numbers the array of numbers.
     */
    public LinearSearch(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "Linear";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean find() {
        for (Integer number : numbers) {
            if (PrimeNumbers.isNonPrime(number)) {
                return true;
            }
        }

        return false;
    }
}
