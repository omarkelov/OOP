package ru.nsu.fit.markelov.validation;

import org.junit.Assert;
import org.junit.Test;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.UNKNOWN_ERROR;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;

public class ExceptionMessageBuilderTest {

    public static final Class<?> CLASS = ExceptionMessageBuilderTest.class;
    public static final String PARAMETER = "someParameter";
    public static final String MESSAGE = "errorMessage";

    @Test
    public void testBasics() {
        Assert.assertEquals("Error in \"" + CLASS.getSimpleName() + "\" class: " + MESSAGE,
            buildMessage(CLASS, MESSAGE));

        Assert.assertEquals("Error in \"" + CLASS.getSimpleName() + "\" class: \"" +
            PARAMETER + "\" " + MESSAGE, buildMessage(CLASS, PARAMETER, MESSAGE));
    }

    @Test
    public void testNullClass() {
        Assert.assertEquals(UNKNOWN_ERROR, buildMessage(null, MESSAGE));
        Assert.assertEquals(UNKNOWN_ERROR, buildMessage(null, PARAMETER, MESSAGE));
    }

    @Test
    public void testNullParameter() {
        Assert.assertEquals(UNKNOWN_ERROR, buildMessage(CLASS, null, MESSAGE));
    }

    @Test
    public void testNullMessage() {
        Assert.assertEquals(UNKNOWN_ERROR, buildMessage(CLASS, null));
        Assert.assertEquals(UNKNOWN_ERROR, buildMessage(CLASS, PARAMETER, null));
    }
}
