package ru.nsu.fit.markelov;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        Scanner iScanner = new Scanner(System.in);
        String input;
        while (iScanner.hasNextLine()) {
            try {
                input = iScanner.nextLine();
                System.out.println(calculator.calculate(input));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        iScanner.close();
    }
}

//~ ^ 5 ~ sin + - 1 2 sqrt 1
