package ru.nsu.fit.markelov;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import picocli.CommandLine;
import ru.nsu.fit.markelov.log.Log;
import ru.nsu.fit.markelov.log.SystemLog;
import ru.nsu.fit.markelov.log.WriterLog;
import ru.nsu.fit.markelov.properties.PizzeriaProperties;
import ru.nsu.fit.markelov.util.IterativeIntGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.Callable;

import static ru.nsu.fit.markelov.validation.ExceptionMessageBuilder.buildMessage;

@CommandLine.Command(name = "pizzeria", description = "Simulates the working day in pizzeria, " +
    "logging every action in STDOUT or into a file.")
public class PizzeriaApplication implements Callable<Integer> {

    public static final String DEFAULT_LOG_FILE_NAME = "log.txt";

    @CommandLine.Parameters(index = "0", description = ".json file with pizzeria properties")
    private File pizzeriaPropertiesFile;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help and exit")
    boolean help;

    @CommandLine.Option(names = {"-f", "--file"}, description = "log to a file")
    boolean usingFileLog;

    @CommandLine.Option(names = {"-o", "--output"}, description = "define a name of the log file")
    private String logFileName = DEFAULT_LOG_FILE_NAME;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new PizzeriaApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        if (usingFileLog) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFileName))) {
                simulatePizzeria(new WriterLog(bufferedWriter));
            } catch (IOException e) {
                System.out.println(buildMessage(PizzeriaApplication.class, e.getMessage()));
            }
        } else {
            simulatePizzeria(new SystemLog());
        }

        return 0;
    }

    private void simulatePizzeria(Log log) {
        try {
            String json = new String(Files.readAllBytes(pizzeriaPropertiesFile.toPath()));

            new Pizzeria(
                new Gson().fromJson(json, PizzeriaProperties.class),
                log,
                new IterativeIntGenerator()
            );
        } catch (IOException e) {
            System.out.println(buildMessage(PizzeriaApplication.class, e.getMessage()));
        } catch (JsonParseException|NullPointerException|IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
