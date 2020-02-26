package ru.nsu.fit.markelov;

import org.json.JSONException;
import org.json.JSONObject;
import ru.nsu.fit.markelov.log.WriterLog;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.util.UniqueIntGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static final String LOG_FILE_NAME = "log.txt";
    public static final String JSON_FILE_NAME = "1.json";

    public static void main(String[] args) {
        try (
                FileWriter fileWriter = new FileWriter(LOG_FILE_NAME);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            new Pizzeria(
                new PizzeriaProperties(new JSONObject(new String(Files.readAllBytes(Paths.get(JSON_FILE_NAME))))),
                new WriterLog(bufferedWriter),
                new UniqueIntGenerator()
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }
}
