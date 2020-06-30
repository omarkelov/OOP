package ru.nsu.fit.markelov;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ru.nsu.fit.markelov.app.Course;
import ru.nsu.fit.markelov.git.GitProviderStub;
import ru.nsu.fit.markelov.gradle.GradleProviderStub;
import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.Group;
import ru.nsu.fit.markelov.objects.Lesson;
import ru.nsu.fit.markelov.objects.Settings;
import ru.nsu.fit.markelov.objects.Tasks;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Set;

public class Main {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final String RESOURCES_DIR = "src/main/resources";
    private static final String ENGINE_DIR = "/ru/nsu/fit/markelov/engine/";
    private static final String COURSE_DSL_VAR = "course";

    public static void main(String[] args) {
        try (PrintWriter printWriter = new PrintWriter("report.html")) {
            Path enginePath = getResourcePath(ENGINE_DIR);
            Path scriptsPath = Paths.get(args.length > 0 ? args[0] + "/" : "scripts/");

            Settings settings = (Settings) runScript(enginePath, scriptsPath, "settings");
            Group group = (Group) runScript(enginePath, scriptsPath, "group");
            Tasks tasks = (Tasks) runScript(enginePath, scriptsPath, "tasks");
            @SuppressWarnings("unchecked")
            Set<Lesson> lessons = (Set<Lesson>) runScript(enginePath, scriptsPath, "lessons");
            @SuppressWarnings("unchecked")
            Set<ControlPoint> controlPoints = (Set<ControlPoint>) runScript(enginePath, scriptsPath, "controlPoints");

            Course course = new Course(new GitProviderStub(settings),
                new GradleProviderStub(settings), group, tasks, lessons, controlPoints);

            runScript(enginePath, scriptsPath, "attendance", COURSE_DSL_VAR, course);
            runScript(enginePath, scriptsPath, "passing", COURSE_DSL_VAR, course);

            printWriter.println(course.createReport());
        } catch (IOException|URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static Path getResourcePath(String pathName) throws URISyntaxException, IOException {
        URI uri = Main.class.getResource(pathName).toURI();

        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            return fileSystem.getPath(pathName);
        } else {
            return Paths.get(RESOURCES_DIR + pathName);
        }
    }

    private static Object runScript(Path enginePath, Path scriptsPath, String name) throws IOException {
        Binding binding = new Binding();

        new GroovyShell(binding).evaluate(
            readFile(enginePath.resolve(name.toLowerCase()).resolve(name + ".groovy")) +
            readFile(scriptsPath.resolve(name + ".dsl"))
        );

        return binding.getVariable(name + "DSL");
    }

    private static void runScript(Path enginePath, Path scriptsPath, String name,
                                  String variableName, Object variable) throws IOException {
        Binding binding = new Binding();
        binding.setVariable(variableName, variable);
        new GroovyShell(binding).evaluate(
            readFile(enginePath.resolve(name.toLowerCase()).resolve(name + ".groovy")) +
            readFile(scriptsPath.resolve(name + ".dsl"))
        );
    }

    private static String readFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }
}
