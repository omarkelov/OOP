package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.operations.Operation;
import ru.nsu.fit.markelov.operations.binary.*;
import ru.nsu.fit.markelov.operations.unary.*;

import java.util.Deque;
import java.util.LinkedList;

/**
 * The <code>Calculator</code> class is a simple calculator that lets to calculate the expressions
 * in the polish notation.
 * <p>
 * The <code>Calculator</code> provides next operations:
 * <pre>    Unary: Negation (~), SquareRoot (sqrt), Sinus (sin), Cosinus (cos).</pre>
 * <pre>    Binary: Addition (+), Subtraction (-), Multiplication (*), Division (/), Exponentiation (^), Logarithm (log).</pre>
 *
 * @author Oleg Markelov
 * @see    Negation
 * @see    SquareRoot
 * @see    Sinus
 * @see    Cosinus
 * @see    Addition
 * @see    Subtraction
 * @see    Multiplication
 * @see    Division
 * @see    Exponentiation
 * @see    Logarithm
 */
public class Calculator {

    public static final String EXTRA_OPERAND_EXCEPTION_MESSAGE = "Extra operand found: ";
    public static final String INVALID_TOKEN_EXCEPTION_MESSAGE = "Invalid token found: ";
    public static final String NO_OPERAND_EXCEPTION_MESSAGE = "No operand for operation found: ";

    private OperationFactory operationFactory;
    private Deque<Operation> stack;
    private double res;

    /**
     * Creates a new <code>Calculator</code>.
     */
    public Calculator() {
        operationFactory = new OperationFactory();
        stack = new LinkedList<>();
    }

    /**
     * Calculates an expression in the polish notation and returns the result or NaN if the
     * calculation cannot be done.
     *
     * @param  expression               an expression in the polish notation.
     * @return                          the result of calculation or NaN if the calculation cannot
     *                                  be done.
     * @throws IllegalArgumentException if: 1) Extra operand found;
     *                                      2) Invalid token found;
     *                                      3) No operands for operation found.
     */
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
            throw new IllegalArgumentException(NO_OPERAND_EXCEPTION_MESSAGE +
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
