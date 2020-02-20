package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.log.WriterLog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try (
                FileWriter fileWriter = new FileWriter("log.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            new Pizzeria(
                    new String(Files.readAllBytes(Paths.get("1.json")), StandardCharsets.UTF_8),
                    new WriterLog(bufferedWriter)
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
