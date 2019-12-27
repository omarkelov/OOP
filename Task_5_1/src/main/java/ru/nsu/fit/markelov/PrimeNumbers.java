package ru.nsu.fit.markelov;

/**
 * PrimeNumbers class is used for defining whether the number is prime.
 *
 * @author Oleg Markelov
 */
public class PrimeNumbers {

    /**
     * Returns whether the number is prime.
     *
     * @param n the number.
     * @return  whether the number is prime.
     */
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;

        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }

        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns whether the number is composite.
     *
     * @param n the number.
     * @return  whether the number is composite.
     */
    public static boolean isNonPrime(int n) {
        return !isPrime(n);
    }
}
