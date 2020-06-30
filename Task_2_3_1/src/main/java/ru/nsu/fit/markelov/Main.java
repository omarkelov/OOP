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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Set;

public class Main {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final String ENGINE_DIR = "src/main/groovy/ru/nsu/fit/markelov/engine/";
    private static final String COURSE_DSL_VAR = "course";

    public static void main(String[] args) {
        String scriptsDir = args.length > 0 ? args[0] + "/" : "scripts/";

        try (PrintWriter printWriter = new PrintWriter("report.html")) {
            Settings settings = (Settings) runScript(scriptsDir, "settings");
            Group group = (Group) runScript(scriptsDir, "group");
            Tasks tasks = (Tasks) runScript(scriptsDir, "tasks");
            Set<Lesson> lessons = (Set<Lesson>) runScript(scriptsDir, "lessons");
            Set<ControlPoint> controlPoints = (Set<ControlPoint>) runScript(scriptsDir, "controlPoints");

            Course course = new Course(new GitProviderStub(settings),
                new GradleProviderStub(settings), group, tasks, lessons, controlPoints);

            runScript(scriptsDir, "attendance", COURSE_DSL_VAR, course);
            runScript(scriptsDir, "passing", COURSE_DSL_VAR, course);

            printWriter.println(course.createReport());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object runScript(String scriptsDir, String name) throws IOException {
        Binding binding = new Binding();

        new GroovyShell(binding).evaluate(
            readFile(ENGINE_DIR + "/" + name + "/" + name + ".groovy") +
            readFile(scriptsDir + "/" + name + ".dsl")
        );

        return binding.getVariable(name + "DSL");
    }

    private static void runScript(String scriptsDir, String name,
                                  String variableName, Object variable) throws IOException {
        Binding binding = new Binding();
        binding.setVariable(variableName, variable);
        new GroovyShell(binding).evaluate(
            readFile(ENGINE_DIR + "/" + name + "/" + name + ".groovy") +
            readFile(scriptsDir + "/" + name + ".dsl")
        );
    }

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
