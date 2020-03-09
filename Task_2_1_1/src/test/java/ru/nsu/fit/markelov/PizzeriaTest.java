package ru.nsu.fit.markelov;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.SystemLog;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.util.IterativeIntGenerator;
import ru.nsu.fit.markelov.util.UniqueIntGenerator;
import ru.nsu.fit.markelov.validation.IllegalInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;

public class PizzeriaTest {

    private Log log = new SystemLog();

    private UniqueIntGenerator idGenerator = new IterativeIntGenerator();

    @Test
    public void testValidData() {
        String jsonFileName = "src/test/resources/properties/valid.json";

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, idGenerator);
        } catch (IOException|JsonParseException|IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testInvalidData() {
        String jsonFileName = "src/test/resources/properties/invalidJson.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            new Gson().fromJson(json, PizzeriaProperties.class);
        } catch (JsonParseException e) {
            exceptionCaught = true;
        } catch (IOException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullProperties() {
        boolean exceptionCaught = false;

        try {
            new Pizzeria(null, log, idGenerator);
        } catch (IllegalInputException e) {
            if (e.getMessage().endsWith(NOT_NULL)) {
                exceptionCaught = true;
            }
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullLog() {
        String jsonFileName = "src/test/resources/properties/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, null, idGenerator);
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
    public void testNullIdGenerator() {
        String jsonFileName = "src/test/resources/properties/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, null);
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
    public void testNullOperators() {
        String jsonFileName = "src/test/resources/properties/nullOperators.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, idGenerator);
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
    public void testNullBakers() {
        String jsonFileName = "src/test/resources/properties/nullBakers.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, idGenerator);
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
    public void testNullCouriers() {
        String jsonFileName = "src/test/resources/properties/nullCouriers.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, idGenerator);
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
    public void testNonPositiveTime() {
        String jsonFileName = "src/test/resources/properties/nonPositiveTime.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, idGenerator);
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
    public void testNonPositiveNewOrderCapacity() {
        String jsonFileName = "src/test/resources/properties/nonPositiveNewOrderCapacity.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, idGenerator);
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
    public void testNonPositiveStorageCapacity() {
        String jsonFileName = "src/test/resources/properties/nonPositiveStorageCapacity.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            PizzeriaProperties pizzeriaProperties = new Gson().fromJson(json, PizzeriaProperties.class);
            new Pizzeria(pizzeriaProperties, log, idGenerator);
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
