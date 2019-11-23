package ru.nsu.fit.markelov;

import java.util.ArrayList;

public class LinearSearch extends NonPrimeSearch {

    private ArrayList<Integer> numbers;

    public LinearSearch(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String getType() {
        return "Linear";
    }

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
