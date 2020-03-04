package ru.nsu.fit.markelov.workers;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.junit.Assert;
import org.junit.Test;
import ru.nsu.fit.markelov.Order;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.SystemLog;
import ru.nsu.fit.markelov.properties.OperatorProperties;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.util.IterativeIntGenerator;
import ru.nsu.fit.markelov.util.UniqueIntGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static ru.nsu.fit.markelov.properties.OperatorProperties.GREATER;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_EMPTY;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;
import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.POSITIVE;

public class OperatorTest {

    private Log log = new SystemLog();

    private UniqueIntGenerator idGenerator = new IterativeIntGenerator();

    private BlockingQueue<Order> newOrders = new LinkedBlockingQueue<>(9);

    @Test
    public void testValidData() {
        String jsonFileName = "src/test/resources/properties/operator/valid.json";

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            Operator operator = new Operator(operatorProperties, idGenerator, newOrders, log);
            Assert.assertEquals("Operator_1", operator.getName());
        } catch (IOException|JsonParseException|NullPointerException|IllegalArgumentException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullProperties() {
        boolean exceptionCaught = false;

        try {
            new Operator(null, idGenerator, newOrders, log);
        } catch (NullPointerException e) {
            if (e.getMessage().endsWith(NOT_NULL)) {
                exceptionCaught = true;
            }
        }

        Assert.assertTrue(exceptionCaught);
    }

    @Test
    public void testNullIdGenerator() {
        String jsonFileName = "src/test/resources/properties/operator/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            new Operator(operatorProperties, null, newOrders, log);
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
    public void testNullNewOrders() {
        String jsonFileName = "src/test/resources/properties/operator/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            new Operator(operatorProperties, idGenerator, null, log);
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
        String jsonFileName = "src/test/resources/properties/operator/valid.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            new Operator(operatorProperties, idGenerator, newOrders, null);
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
        String jsonFileName = "src/test/resources/properties/operator/nullName.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            new Operator(operatorProperties, idGenerator, newOrders, log);
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
            new Operator(operatorProperties, idGenerator, newOrders, log);
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
            new Operator(operatorProperties, idGenerator, newOrders, log);
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
            new Operator(operatorProperties, idGenerator, newOrders, log);
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
    public void testNonPositiveTimeMaxGreater() {
        String jsonFileName = "src/test/resources/properties/operator/timeMaxGreater.json";
        boolean exceptionCaught = false;

        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFileName)));
            OperatorProperties operatorProperties = new Gson().fromJson(json, OperatorProperties.class);
            new Operator(operatorProperties, idGenerator, newOrders, log);
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
