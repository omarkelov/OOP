package ru.nsu.fit.markelov.properties;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.validation.IllegalInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;

public class CourierPropertiesTest {
    @Test
    public void testValidData() {
        String jsonFileName = "src/test/resources/properties/courier/valid.json";

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            courierProperties.validate();
        } catch (IOException|JsonParseException|IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullName() {
        String jsonFileName = "src/test/resources/properties/courier/nullName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            courierProperties.validate();
        } catch (IllegalInputException e) {
            if (e.getMessage().endsWith(NOT_NULL)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testEmptyName() {
        String jsonFileName = "src/test/resources/properties/courier/emptyName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            courierProperties.validate();
        } catch (IllegalInputException e) {
            if (e.getMessage().endsWith(NOT_EMPTY)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNonPositiveTime() {
        String jsonFileName = "src/test/resources/properties/courier/nonPositiveTime.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            courierProperties.validate();
        } catch (IllegalInputException e) {
            if (e.getMessage().endsWith(POSITIVE)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNonPositiveCapacity() {
        String jsonFileName = "src/test/resources/properties/courier/nonPositiveCapacity.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            courierProperties.validate();
        } catch (IllegalInputException e) {
            if (e.getMessage().endsWith(POSITIVE)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }
}
