package ru.nsu.fit.markelov.workers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.SystemLog;
import ru.nsu.fit.markelov.properties.BakerProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;

public class BakerTest {

    private Log log = new SystemLog();

    private BlockingQueue<Order> newOrders = new LinkedBlockingQueue<>(9);
    private BlockingQueue<Order> storedOrders = new LinkedBlockingQueue<>(9);

    @Test
    public void testValidData() {
        String jsonFileName = "src/test/resources/properties/baker/valid.json";

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            BakerProperties bakerProperties = new Gson().fromJson(json, BakerProperties.class);
            Baker baker = new Baker(bakerProperties, newOrders, storedOrders, log);
            Assert.assertEquals("Baker_1", baker.getName());
        } catch (IOException|JsonParseException|NullPointerException|IllegalArgumentException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullProperties() {
        boolean exceptionCaught = false;

        try {
            new Baker(null, newOrders, storedOrders, log);
        } catch (NullPointerException e) {
            if (e.getMessage().endsWith(NOT_NULL)) {
                exceptionCaught = true;
            }
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullNewOrders() {
        String jsonFileName = "src/test/resources/properties/baker/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            BakerProperties bakerProperties = new Gson().fromJson(json, BakerProperties.class);
            new Baker(bakerProperties, null, storedOrders, log);
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
    public void testNullStoredOrders() {
        String jsonFileName = "src/test/resources/properties/baker/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            BakerProperties bakerProperties = new Gson().fromJson(json, BakerProperties.class);
            new Baker(bakerProperties, newOrders, null, log);
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
    public void testNullLog() {
        String jsonFileName = "src/test/resources/properties/baker/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            BakerProperties bakerProperties = new Gson().fromJson(json, BakerProperties.class);
            new Baker(bakerProperties, newOrders, storedOrders, null);
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
    public void testNullName() {
        String jsonFileName = "src/test/resources/properties/baker/nullName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            BakerProperties bakerProperties = new Gson().fromJson(json, BakerProperties.class);
            new Baker(bakerProperties, newOrders, storedOrders, log);
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
        String jsonFileName = "src/test/resources/properties/baker/emptyName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            BakerProperties bakerProperties = new Gson().fromJson(json, BakerProperties.class);
            new Baker(bakerProperties, newOrders, storedOrders, log);
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
    public void testNonPositiveTime() {
        String jsonFileName = "src/test/resources/properties/baker/nonPositiveTime.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            BakerProperties bakerProperties = new Gson().fromJson(json, BakerProperties.class);
            new Baker(bakerProperties, newOrders, storedOrders, log);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().endsWith(POSITIVE)) {
                exceptionCaught = true;
            }
        } catch (IOException|JsonParseException|NullPointerException e) {
            Assert.fail();
        }

        Assert.assertTrue(exceptionCaught);
    }
}
