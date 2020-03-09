package ru.nsu.fit.markelov.log;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.NOT_NULL;

public class WriterLogTest {
    @Test
    public void testFileWriting() {
        String logFileName = "src/test/resources/testLog.txt";
        String message = "message";

        try (FileWriter fileWriter = new FileWriter(logFileName)) {
            WriterLog writerLog = new WriterLog(fileWriter);
            writerLog.i(message);
            writerLog.d(message);
            writerLog.e(message);
        } catch (IOException e) {
            Assert.fail();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFileName)))  {
            String line = reader.readLine();
            Assert.assertTrue(line.startsWith("Info"));
            Assert.assertTrue(line.endsWith(message));

            line = reader.readLine();
            Assert.assertTrue(line.startsWith("Debug"));
            Assert.assertTrue(line.endsWith(message));

            line = reader.readLine();
            Assert.assertTrue(line.startsWith("Error"));
            Assert.assertTrue(line.endsWith(message));

            line = reader.readLine();
            Assert.assertNull(line);
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testNullWriter() {
        boolean exceptionCaught = false;

        try {
            new WriterLog(null);
        } catch (NullPointerException e) {
            if (e.getMessage().endsWith(NOT_NULL)) {
                exceptionCaught = true;
            }
        }

        Assert.assertTrue(exceptionCaught);
    }
}
