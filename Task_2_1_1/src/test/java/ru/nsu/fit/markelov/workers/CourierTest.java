package ru.nsu.fit.markelov.workers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.SystemLog;
import ru.nsu.fit.markelov.properties.CourierProperties;
import ru.nsu.fit.markelov.validation.IllegalInputException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;

public class CourierTest {

    private Log log = new SystemLog();

    private BlockingQueue<Order> storedOrders = new LinkedBlockingQueue<>(9);

    @Test
    public void testValidData() {
        String jsonFileName = "src/test/resources/properties/courier/valid.json";

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            Courier courier = new Courier(courierProperties, storedOrders, log);
            Assert.assertEquals("Courier_1", courier.getName());
        } catch (IOException|JsonParseException|IllegalInputException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullProperties() {
        boolean exceptionCaught = false;

        try {
            new Courier(null, storedOrders, log);
        } catch (IllegalInputException e) {
            if (e.getMessage().endsWith(NOT_NULL)) {
                exceptionCaught = true;
            }
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullStoredOrders() {
        String jsonFileName = "src/test/resources/properties/courier/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            new Courier(courierProperties, null, log);
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
    public void testNullLog() {
        String jsonFileName = "src/test/resources/properties/courier/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            new Courier(courierProperties, storedOrders, null);
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
    public void testNullName() {
        String jsonFileName = "src/test/resources/properties/courier/nullName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            CourierProperties courierProperties = new Gson().fromJson(json, CourierProperties.class);
            new Courier(courierProperties, storedOrders, log);
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
            new Courier(courierProperties, storedOrders, log);
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
            new Courier(courierProperties, storedOrders, log);
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
            new Courier(courierProperties, storedOrders, log);
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
