package ru.nsu.fit.markelov.properties;

import ru.nsu.fit.markelov.validation.Validatable;
import ru.nsu.fit.markelov.workers.Courier;

import java.util.Objects;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;

/**
 * The <code>CourierProperties</code> class is used for reading the properties of
 * <code>Courier</code> from .json file.
 *
 * @author Oleg Markelov
 * @see Courier
 */
public class CourierProperties implements Validatable<CourierProperties> {

    private String name;
    private long orderHandlingTime;
    private int bagCapacity;

    /**
     * Validates this object throwing an exception in case it is invalid.
     * <p>
     * Checks name for null and emptiness.
     * <p>
     * Checks orderHandlingTime and bagCapacity to be positive.
     *
     * @return the object itself.
     * @throws NullPointerException     if any validating parameter is null.
     * @throws IllegalArgumentException if any validating parameter is illegal.
     */
    @Override
    public CourierProperties validate()  {
        Objects.requireNonNull(name,
            buildMessage(CourierProperties.class, "name", NOT_NULL));

        if (name.isEmpty()) {
            throw new IllegalArgumentException(
                buildMessage(CourierProperties.class, "name", NOT_EMPTY));
        }

        if (orderHandlingTime <= 0) {
            throw new IllegalArgumentException(
                buildMessage(CourierProperties.class, "orderHandlingTime", POSITIVE));
        }

        if (bagCapacity <= 0) {
            throw new IllegalArgumentException(
                buildMessage(CourierProperties.class, "bagCapacity", POSITIVE));
        }

        return this;
    }

    public String getName() {
        return name;
    }

    public long getOrderHandlingTime() {
        return orderHandlingTime;
    }

    public int getBagCapacity() {
        return bagCapacity;
    }
}
