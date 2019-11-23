package ru.nsu.fit.markelov;

import java.util.ArrayList;

public class StreamSearch extends NonPrimeSearch {

    private ArrayList<Integer> numbers;

    public StreamSearch(ArrayList<Integer> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String getType() {
        return "parallelStream";
    }

    @Override
    protected boolean find() {
        return numbers.parallelStream().anyMatch(PrimeNumbers::isNonPrime);
    }
}
