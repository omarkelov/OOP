package ru.nsu.fit.markelov;

public class ZFunction {
    /**
     * Calculates a z-Function of the specified string.
     * <p>
     * It takes O(n) char comparisons, where n is the length
     * of the string.
     *
     * @param  chars char array to matchAll in
     * @return       z-Function of the specified string
     */
    public static int[] getZValues(char[] chars) {
        int[] z = new int[chars.length];

        int l = 0,
            r = 0;

        for (int i = 1; i < chars.length; i++) {
            if (i < r) {
                z[i] = Math.min(r - i, z[i - l]);
            }

            while (i + z[i] < chars.length && chars[z[i]] == chars[i + z[i]]) {
                z[i]++;
            }

            if (i + z[i] > r) {
                l = i;
                r = i + z[i];
            }
        }

        return z;
    }
}
