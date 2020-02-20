package ru.nsu.fit.markelov;

import org.json.JSONObject;
import ru.nsu.fit.markelov.log.WriterLog;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;

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
            String jsonStr = new String(Files.readAllBytes(Paths.get(JSON_FILE_NAME)), StandardCharsets.UTF_8);
            new Pizzeria(
                    new PizzeriaProperties(new JSONObject(jsonStr)),
                    new WriterLog(bufferedWriter)
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
