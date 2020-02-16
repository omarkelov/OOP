package ru.nsu.fit.markelov;

import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.SimpleLog;
import ru.nsu.fit.markelov.log.StaticLog;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try (
                FileWriter fileWriter = new FileWriter("simpleLog.txt");
                FileWriter staticFileWriter = new FileWriter("staticLog.txt");
                FileWriter singletonFileWriter = new FileWriter("singletonLog.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                BufferedWriter staticBufferedWriter = new BufferedWriter(staticFileWriter);
                BufferedWriter singletonBufferedWriter = new BufferedWriter(singletonFileWriter)
        ) {
            StaticLog.init(staticBufferedWriter);
            Log.getInstance().setWriter(singletonBufferedWriter);

            new Pizzeria(
                    new String(Files.readAllBytes(Paths.get("1.json")), StandardCharsets.UTF_8),
                    new SimpleLog(bufferedWriter)
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
