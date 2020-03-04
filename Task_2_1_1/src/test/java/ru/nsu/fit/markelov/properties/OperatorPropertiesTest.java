package ru.nsu.fit.markelov.properties;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.nsu.fit.markelov.properties.OperatorProperties.GREATER;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;

public class OperatorPropertiesTest {
    @Test
    public void testValidData() {
        String jsonFileName = "src/test/resources/properties/operator/valid.json";

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            operatorProperties.validate();
        } catch (IOException|JsonParseException|NullPointerException|IllegalArgumentException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullName() {
        String jsonFileName = "src/test/resources/properties/operator/nullName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            operatorProperties.validate();
        } catch (NullPointerException e) {
            if (e.getMessage().endsWith(NOT_NULL)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException|IllegalArgumentException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testEmptyName() {
        String jsonFileName = "src/test/resources/properties/operator/emptyName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            operatorProperties.validate();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().endsWith(NOT_EMPTY)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException|NullPointerException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNonPositiveTimeMin() {
        String jsonFileName = "src/test/resources/properties/operator/nonPositiveTimeMin.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            operatorProperties.validate();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().endsWith(POSITIVE)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException|NullPointerException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNonPositiveTimeMax() {
        String jsonFileName = "src/test/resources/properties/operator/nonPositiveTimeMax.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            operatorProperties.validate();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().endsWith(POSITIVE)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException|NullPointerException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testTimeMaxGreater() {
        String jsonFileName = "src/test/resources/properties/operator/timeMaxGreater.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            operatorProperties.validate();
        } catch (IllegalArgumentException e) {
            if (e.getMessage().endsWith(GREATER)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException|NullPointerException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }
}
