package ru.nsu.fit.markelov;

/**
 * <code>ZFunction</code> class is a helper class that calculates
 * a z-Function of the string.
 * <p>
 * It takes O(n) char comparisons, where n is the length of the
 * string.
 *
 * @author Oleg Markelov
 */
public class ZFunction {
    /**
     * Calculates and returns a z-Function of the specified string.
     *
     * @param  chars char array to calculate for.
     * @return       z-Function of the specified string.
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
