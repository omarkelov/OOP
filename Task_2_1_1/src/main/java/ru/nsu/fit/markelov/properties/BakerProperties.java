package ru.nsu.fit.markelov.properties;

import ru.nsu.fit.markelov.validation.IllegalInputException;
import ru.nsu.fit.markelov.validation.Validatable;
import ru.nsu.fit.markelov.workers.Baker;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;
import static ru.nsu.fit.markelov.validation.IllegalInputException.requireNonNull;

/**
 * The <code>BakerProperties</code> class is used for reading the properties of <code>Baker</code>
 * from .json file.
 *
 * @author Oleg Markelov
 * @see Baker
 */
public class BakerProperties implements Validatable<BakerProperties> {

    private String name;
    private long orderHandlingTime;

    /**
     * Validates this object throwing an exception in case it is invalid.
     * <p>
     * Checks name for null and emptiness.
     * <p>
     * Checks orderHandlingTime to be positive.
     *
     * @return the object itself.
     * @throws IllegalInputException if any validating parameter is null or illegal.
     */
    @Override
    public BakerProperties validate() throws IllegalInputException {
        requireNonNull(name,
            buildMessage(BakerProperties.class, "name", NOT_NULL));

        if (name.isEmpty()) {
            throw new IllegalInputException(
                buildMessage(BakerProperties.class, "name", NOT_EMPTY));
        }

        if (orderHandlingTime <= 0) {
            throw new IllegalInputException(
                buildMessage(BakerProperties.class, "orderHandlingTime", POSITIVE));
        }

        return this;
    }

    public String getName() {
        return name;
    }

    public long getOrderHandlingTime() {
        return orderHandlingTime;
    }
}
