package ru.nsu.fit.markelov;

import java.util.Arrays;

public class ZFunction {

    private char[] mString;
    private int[] mPositions;

    /**
     * Creates a new ZFunction.
     * <p>
     * Copies the input data (which stays unmodified) to operate with,
     * builds a z-function of the string and saves it in an array of ints.
     *
     * @param str       the sequence of chars to find at
     * @param strLen    the length of char sequence
     * @param delimiter the symbol never met in a string
     * @param pattern   the substring to find
     */
    public ZFunction(char[] str, int strLen, char delimiter, char[] pattern) {
        mString = new char[pattern.length + 1 + str.length];
        System.arraycopy(pattern, 0, mString, 0, pattern.length);
        mString[pattern.length] = delimiter;
        System.arraycopy(str, 0, mString, pattern.length + 1, str.length);

        findPositions(pattern.length, strLen);
    }

    /**
     * Returns the positions of a substring in a specified string.
     *
     * @return the positions of substring in a specified string
     */
    public int[] getPositions() {
        return mPositions;
    }

    private void findPositions(int m, int n) {
        int[] zs = getZFunction();
        printArr(zs);

        int count = 0;
        for (int i = m + 1; i < m + 1 + n; i++) {
            if (zs[i] == m) {
                zs[count++] = i - m - 1;
            }
        }

        mPositions = Arrays.copyOf(zs, count);

    }

    private int[] getZFunction() {
        int[] z = new int[mString.length];

        int l = 0,
            r = 0;

        for (int i = 1; i < mString.length; i++) {
            if (i < r) {
                z[i] = Math.min(r - i, z[i - l]);
            }

            while (i + z[i] < mString.length && mString[z[i]] == mString[i + z[i]]) {
                z[i]++;
            }

            if (i + z[i] > r) {
                l = i;
                r = i + z[i];
            }
        }

        return z;
    }

    private void printArr(int[] arr) {
        System.out.print("{");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i != arr.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}");
    }
}
