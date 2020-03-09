package ru.nsu.fit.markelov.properties;

import ru.nsu.fit.markelov.Pizzeria;
import ru.nsu.fit.markelov.validation.IllegalInputException;
import ru.nsu.fit.markelov.validation.Validatable;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;
import static ru.nsu.fit.markelov.validation.IllegalInputException.requireNonNull;

/**
 * The <code>PizzeriaProperties</code> class is used for reading the properties of
 * <code>Pizzeria</code> from .json file.
 *
 * @author Oleg Markelov
 * @see Pizzeria
 */
public class PizzeriaProperties implements Validatable<PizzeriaProperties> {

    private long workingTime;
    private int newOrdersCapacity;
    private int storageCapacity;
    private OperatorProperties[] operatorsProperties;
    private BakerProperties[] bakersProperties;
    private CourierProperties[] couriersProperties;

    /**
     * Validates this object throwing an exception in case it is invalid.
     * <p>
     * Checks operatorsProperties, bakersProperties and couriersProperties for null and tries to
     * validate each of them.
     * <p>
     * Checks workingTime, newOrdersCapacity and storageCapacity to be positive.
     *
     * @return the object itself.
     * @throws IllegalInputException if any validating parameter is null or illegal.
     */
    @Override
    public PizzeriaProperties validate() throws IllegalInputException {
        requireNonNull(operatorsProperties,
            buildMessage(PizzeriaProperties.class, "operatorsProperties", NOT_NULL));

        requireNonNull(bakersProperties,
            buildMessage(PizzeriaProperties.class, "bakersProperties", NOT_NULL));

        requireNonNull(couriersProperties,
            buildMessage(PizzeriaProperties.class, "couriersProperties", NOT_NULL));

        if (workingTime <= 0) {
            throw new IllegalInputException(
                buildMessage(PizzeriaProperties.class, "workingTime", POSITIVE));
        }

        if (newOrdersCapacity <= 0) {
            throw new IllegalInputException(
                buildMessage(PizzeriaProperties.class, "newOrdersCapacity", POSITIVE));
        }

        if (storageCapacity <= 0) {
            throw new IllegalInputException(
                buildMessage(PizzeriaProperties.class, "storageCapacity", POSITIVE));
        }

        for (OperatorProperties operatorProperties : operatorsProperties) {
            operatorProperties.validate();
        }

        for (BakerProperties bakerProperties : bakersProperties) {
            bakerProperties.validate();
        }

        for (CourierProperties courierProperties : couriersProperties) {
            courierProperties.validate();
        }

        return this;
    }

    public long getWorkingTime() {
        return workingTime;
    }

    public int getNewOrdersCapacity() {
        return newOrdersCapacity;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public OperatorProperties[] getOperatorsProperties() {
        return operatorsProperties;
    }

    public BakerProperties[] getBakersProperties() {
        return bakersProperties;
    }

    public CourierProperties[] getCouriersProperties() {
        return couriersProperties;
    }
}
