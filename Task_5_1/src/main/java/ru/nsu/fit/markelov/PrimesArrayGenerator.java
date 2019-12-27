package ru.nsu.fit.markelov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * PrimesArrayGenerator class is used to generate a shuffled array of specified amount of prime and
 * composite numbers.
 *
 * @author Oleg Markelov
 */
public class PrimesArrayGenerator {

    public static final int PRIME = 2147483647;
    public static final int NON_PRIME = 4;

    /**
     * Generates a shuffled array of specified amount of prime and composite numbers. The array is
     * shuffled based on specified Random parameter.
     *
     * @param nPrimes    the amount of prime numbers.
     * @param nNonPrimes the amount of composite numbers.
     * @param random     is used for array shuffling.
     * @return           generated array.
     */
    public static ArrayList<Integer> generate(int nPrimes, int nNonPrimes, Random random) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        arrayList.addAll(Collections.nCopies(nPrimes, PRIME));
        arrayList.addAll(Collections.nCopies(nNonPrimes, NON_PRIME));
        Collections.shuffle(arrayList, random);

        return arrayList;
    }
}
