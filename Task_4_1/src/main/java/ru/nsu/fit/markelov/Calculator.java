package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.operations.Operation;

import java.util.Deque;
import java.util.LinkedList;

public class Calculator {

    public static final String EXTRA_OPERAND_EXCEPTION_MESSAGE = "Extra operand found: ";
    public static final String INVALID_TOKEN_EXCEPTION_MESSAGE = "Invalid token found: ";
    public static final String NO_OPERANDS_EXCEPTION_MESSAGE = "No operands for operation: ";

    private OperationFactory operationFactory;
    private Deque<Operation> stack;
    private double res;

    public Calculator() {
        operationFactory = new OperationFactory();
        stack = new LinkedList<>();
    }

    public double calculate(String expression) {
        stack.clear();

        for (String token : tokenize(expression)) {
            Operation operation;
            try {
                operation = operationFactory.createOperation(token);
            } catch (InstantiationException|IllegalAccessException e) {
                System.out.println("Error (" + e.getMessage() + ")");
                return Double.NaN;
            }

            if (operation != null) {
                stack.push(operation);
            } else try {
                double number = Double.parseDouble(token);

                if (stack.isEmpty()) {
                    throw new IllegalArgumentException(EXTRA_OPERAND_EXCEPTION_MESSAGE +
                            "\"" + token + "\"");
                }

                rollBackThroughStack(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(INVALID_TOKEN_EXCEPTION_MESSAGE +
                        "\"" + token + "\"");
            }
        }

        if (!stack.isEmpty()) {
            throw new IllegalArgumentException(NO_OPERANDS_EXCEPTION_MESSAGE +
                    "\"" + stack.pop().getClass().getSimpleName() + "\"");
        }

        return res;
    }

    private String[] tokenize(String str) {
        return str.split("\\s+");
    }

    private void rollBackThroughStack(double operand) {
        if (stack.isEmpty()) {
            return;
        }

        stack.peek().setOperand(operand);
        if (stack.peek().isReadyToCalculate()) {
            res = stack.pop().calculate();
            rollBackThroughStack(res);
        }
    }
}
