package ru.nsu.fit.markelov;

public class PrimeNumbers {

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

    public static boolean isNonPrime(int n) {
        return !isPrime(n);
    }
}
