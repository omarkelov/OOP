package ru.nsu.fit.markelov.properties;

import ru.nsu.fit.markelov.validation.IllegalInputException;
import ru.nsu.fit.markelov.validation.Validatable;
import ru.nsu.fit.markelov.workers.Operator;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;
import static ru.nsu.fit.markelov.validation.IllegalInputException.requireNonNull;

/**
 * The <code>OperatorProperties</code> class is used for reading the properties of
 * <code>Operator</code> from .json file.
 *
 * @author Oleg Markelov
 * @see Operator
 */
public class OperatorProperties implements Validatable<OperatorProperties> {

    public static final String GREATER =
        "\"orderHandlingTimeMax\" must be greater than \"orderHandlingTimeMin\".";

    private String name;
    private long orderHandlingTimeMin;
    private long orderHandlingTimeMax;

    /**
     * Validates this object throwing an exception in case it is invalid.
     * <p>
     * Checks name for null and emptiness.
     * <p>
     * Checks orderHandlingTimeMin and orderHandlingTimeMax to be positive.
     *
     * @return the object itself.
     * @throws IllegalInputException if any validating parameter is null or illegal.
     */
    @Override
    public OperatorProperties validate() throws IllegalInputException {
        requireNonNull(name,
            buildMessage(OperatorProperties.class, "name", NOT_NULL));

        if (name.isEmpty()) {
            throw new IllegalInputException(
                buildMessage(OperatorProperties.class, "name", NOT_EMPTY));
        }

        if (orderHandlingTimeMin <= 0) {
            throw new IllegalInputException(
                buildMessage(OperatorProperties.class, "orderHandlingTimeMin", POSITIVE));
        }

        if (orderHandlingTimeMax <= 0) {
            throw new IllegalInputException(
                buildMessage(OperatorProperties.class, "orderHandlingTimeMin", POSITIVE));
        }

        if (orderHandlingTimeMax <= orderHandlingTimeMin) {
            throw new IllegalInputException(
                buildMessage(OperatorProperties.class, GREATER));
        }

        return this;
    }

    public String getName() {
        return name;
    }

    public long getOrderHandlingTimeMin() {
        return orderHandlingTimeMin;
    }

    public long getOrderHandlingTimeMax() {
        return orderHandlingTimeMax;
    }
}
