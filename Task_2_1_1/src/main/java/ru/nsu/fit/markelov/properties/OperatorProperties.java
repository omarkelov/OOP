package ru.nsu.fit.markelov.properties;

import ru.nsu.fit.markelov.validation.Validatable;
import ru.nsu.fit.markelov.workers.Operator;

import java.util.Objects;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;

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
     * @throws NullPointerException     if any validating parameter is null.
     * @throws IllegalArgumentException if any validating parameter is illegal.
     */
    @Override
    public OperatorProperties validate() {
        Objects.requireNonNull(name,
            buildMessage(OperatorProperties.class, "name", NOT_NULL));

        if (name.isEmpty()) {
            throw new IllegalArgumentException(
                buildMessage(OperatorProperties.class, "name", NOT_EMPTY));
        }

        if (orderHandlingTimeMin <= 0) {
            throw new IllegalArgumentException(
                buildMessage(OperatorProperties.class, "orderHandlingTimeMin", POSITIVE));
        }

        if (orderHandlingTimeMax <= 0) {
            throw new IllegalArgumentException(
                buildMessage(OperatorProperties.class, "orderHandlingTimeMin", POSITIVE));
        }

        if (orderHandlingTimeMax <= orderHandlingTimeMin) {
            throw new IllegalArgumentException(
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
