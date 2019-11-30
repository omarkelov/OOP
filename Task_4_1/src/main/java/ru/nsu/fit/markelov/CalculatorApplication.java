package ru.nsu.fit.markelov;

import java.util.Scanner;

public class CalculatorApplication {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        Scanner iScanner = new Scanner(System.in);
        String input;
        System.out.println("Enter the expression and press \"Enter\" to calculate:");
        while (iScanner.hasNextLine()) {
            try {
                input = iScanner.nextLine();
                System.out.println(calculator.calculate(input));
            } catch (IllegalArgumentException|InstantiationException|IllegalAccessException e) {
                System.out.println("Error (" + e.getMessage() + ")");
            }
        }

        iScanner.close();
    }
}

//~ ^ 5 ~ sin + - 1 2 sqrt 1
