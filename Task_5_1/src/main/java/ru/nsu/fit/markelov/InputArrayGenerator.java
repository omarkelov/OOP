package ru.nsu.fit.markelov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class InputArrayGenerator {

    public static final int PRIME = 2147483647;
    public static final int NON_PRIME = 4;

    public static ArrayList<Integer> generate(int nPrimes, int nNonPrimes, Random random) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        arrayList.addAll(Collections.nCopies(nPrimes, PRIME));
        arrayList.addAll(Collections.nCopies(nNonPrimes, NON_PRIME));
        Collections.shuffle(arrayList, random);

        return arrayList;
    }
}
