package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.operations.Operation;

import java.util.Deque;
import java.util.LinkedList;

public class Calculator {

    private OperationFactory operationFactory;
    private Deque<Operation> stack;
    private double res;

    public Calculator() {
        operationFactory = new OperationFactory();
        stack = new LinkedList<>();
    }

    public double calculate(String expression) throws InstantiationException, IllegalAccessException {
        stack.clear();

        for (String token : tokenize(expression)) {
            process(token);
        }

        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("No operands for operation: " +
                    "\"" + stack.pop().getClass().getSimpleName() + "\"");
        }

        return res;
    }

    private String[] tokenize(String str) {
        return str.split("\\s+");
    }

    private void process(String token) throws InstantiationException, IllegalAccessException {
        try {
            Operation operation = operationFactory.createOperation(token);
            if (operation != null) {
                stack.push(operation);
            } else {
                double number = Double.parseDouble(token);
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Extra operand found: \"" + token + "\"");
                }
                rollBackThroughStack(number);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid token found: \"" + token + "\"");
        }
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
