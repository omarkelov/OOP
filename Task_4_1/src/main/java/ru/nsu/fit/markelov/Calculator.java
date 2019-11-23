package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.operations.Operation;
import ru.nsu.fit.markelov.operations.binary.*;
import ru.nsu.fit.markelov.operations.unary.Cosinus;
import ru.nsu.fit.markelov.operations.unary.Negation;
import ru.nsu.fit.markelov.operations.unary.Sinus;
import ru.nsu.fit.markelov.operations.unary.SquareRoot;

import java.util.Deque;
import java.util.LinkedList;

public class Calculator {

    private Deque<Operation> stack;
    private double res;

    public Calculator() {
        stack = new LinkedList<>();
    }

    public double calculate(String expression) throws IllegalArgumentException {
        for (String token : expression.split(" ")) {
            process(token);
        }

        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("No operands for operation: \"" + stack.pop().getClass().getSimpleName() + "\"");
        }

        return res;
    }

    private void process(String token) throws IllegalArgumentException {
        if (token.equals(Plus.SYMBOL)) {
            stack.push(new Plus());
        } else if (token.equals(Minus.SYMBOL)) {
            stack.push(new Minus());
        } else if (token.equals(Multiplication.SYMBOL)) {
            stack.push(new Multiplication());
        } else if (token.equals(Division.SYMBOL)) {
            stack.push(new Division());
        } else if (token.equals(Negation.SYMBOL)) {
            stack.push(new Negation());
        } else if (token.equals(Sinus.SYMBOL)) {
            stack.push(new Sinus());
        } else if (token.equals(Cosinus.SYMBOL)) {
            stack.push(new Cosinus());
        } else if (token.equals(Logarithm.SYMBOL)) {
            stack.push(new Logarithm());
        } else if (token.equals(Exponentiation.SYMBOL)) {
            stack.push(new Exponentiation());
        } else if (token.equals(SquareRoot.SYMBOL)) {
            stack.push(new SquareRoot());
        } else {
            if (stack.isEmpty()) {
                throw new IllegalArgumentException("Extra operand found: \"" + token + "\"");
            }

            rollBack(Double.parseDouble(token));
        }
    }

    private void rollBack(double operand) {
        if (stack.isEmpty()) {
            return;
        }

        stack.peek().setOperand(operand);
        if (stack.peek().isReady()) {
            res = stack.pop().calculate();
            rollBack(res);
        }
    }
}
