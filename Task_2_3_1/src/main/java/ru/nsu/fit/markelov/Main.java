package ru.nsu.fit.markelov;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovyjarjarpicocli.CommandLine;
import ru.nsu.fit.markelov.app.Course;
import ru.nsu.fit.markelov.git.GitProvider;
import ru.nsu.fit.markelov.git.GitProviderStub;
import ru.nsu.fit.markelov.gradle.GradleProvider;
import ru.nsu.fit.markelov.gradle.GradleProviderStub;
import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.Group;
import ru.nsu.fit.markelov.objects.Lesson;
import ru.nsu.fit.markelov.objects.Settings;
import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Tasks;

import java.io.FileNotFoundException;
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
import java.util.concurrent.Callable;

@CommandLine.Command(name = "DSL", description = "") // TODO
public class Main implements Callable<Integer> {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final String DEFAULT_SCRIPTS_DIR = "scripts/";
    private static final String RESOURCES_DIR = "src/main/resources";
    private static final String ENGINE_DIR = "/ru/nsu/fit/markelov/engine/";
    private static final String RULES_PATH = "/ru/nsu/fit/markelov/rules.txt";
    private static final String COURSE_DSL_VAR = "course";

    @CommandLine.Parameters(index = "0", defaultValue = "",
        description = "runs one of the next commands: " +
            "compile (compiles the task and generates a documentation), " +
            "style (checks the code style for the task), " +
            "test (runs the tests for the task), " +
            "points (calculates the points for the task), " +
            "control (creates the report for every control point), " +
            "report (creates the .html report for the group)")
    private String command;

    @CommandLine.Parameters(index = "1", defaultValue = "", description = "student unique id")
    private String student;

    @CommandLine.Parameters(index = "2", defaultValue = "", description = "task unique id")
    private String task;

    @CommandLine.Option(names = { "-h", "--help" }, usageHelp = true,
        description = "display this help and exit")
    boolean helpRequested;

    @CommandLine.Option(names = { "-s", "--scripts" }, defaultValue = DEFAULT_SCRIPTS_DIR,
        description = "define a directory with scripts")
    private String scriptsDir;

    @CommandLine.Option(names = { "-r", "--rules" }, description = "display rules of writing scripts and exit")
    private boolean rulesRequested;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        try {
            if (rulesRequested) {
                System.out.println(readFile(getResourcePath(RULES_PATH)));

                return 0;
            }

            Path enginePath = getResourcePath(ENGINE_DIR);
            Path scriptsPath = Paths.get(scriptsDir);

            Settings settings = (Settings) runScript(enginePath, scriptsPath, "settings");
            Group group = (Group) runScript(enginePath, scriptsPath, "group");
            Tasks tasks = (Tasks) runScript(enginePath, scriptsPath, "tasks");
            @SuppressWarnings("unchecked")
            Set<Lesson> lessons = (Set<Lesson>) runScript(enginePath, scriptsPath, "lessons");
            @SuppressWarnings("unchecked")
            Set<ControlPoint> controlPoints = (Set<ControlPoint>) runScript(
                enginePath, scriptsPath, "controlPoints");

            GitProvider gitProvider = new GitProviderStub();
            gitProvider.setWorkingDirectory(settings.getWorkingDirectory());
            gitProvider.setUser(settings.getGitLogin(), settings.getGitPassword());
            if (command.equals("update")) {
                for (Student student : group.getStudents().values()) {
                    if (gitProvider.exists(student)) {
                        gitProvider.pull(student);
                        System.out.println(student.getId() + "'s repository pulled");
                    } else {
                        gitProvider.clone(student);
                        System.out.println(student.getId() + "'s repository cloned");
                    }
                }

                return 0;
            }

            GradleProvider gradleProvider = new GradleProviderStub();
            gradleProvider.setWorkingDirectory(settings.getWorkingDirectory());

            Course course = new Course(gitProvider, gradleProvider,
                group, tasks, lessons, controlPoints);

            runScript(enginePath, scriptsPath, "attendance", COURSE_DSL_VAR, course);
            runScript(enginePath, scriptsPath, "passing", COURSE_DSL_VAR, course);

            handleCommand(course);
        } catch (IOException|URISyntaxException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Path getResourcePath(String pathName) throws URISyntaxException, IOException {
        URI uri = Main.class.getResource(pathName).toURI();

        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            return fileSystem.getPath(pathName);
        } else {
            return Paths.get(RESOURCES_DIR + pathName);
        }
    }

    private Object runScript(Path enginePath, Path scriptsPath, String name)
                                    throws IOException {
        Binding binding = new Binding();

        new GroovyShell(binding).evaluate(
            readFile(enginePath.resolve(name.toLowerCase()).resolve(name + ".groovy")) +
            readFile(scriptsPath.resolve(name + ".dsl"))
        );

        return binding.getVariable(name + "DSL");
    }

    private void runScript(Path enginePath, Path scriptsPath, String name,
                                  String variableName, Object variable) throws IOException {
        Binding binding = new Binding();
        binding.setVariable(variableName, variable);

        new GroovyShell(binding).evaluate(
            readFile(enginePath.resolve(name.toLowerCase()).resolve(name + ".groovy")) +
            readFile(scriptsPath.resolve(name + ".dsl"))
        );
    }

    private String readFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private void handleCommand(Course course) throws FileNotFoundException {
        String message;
        switch (command) {
            case "compile":
                message = course.compile(student, task);
                System.out.println(student + "'s \"" + task + "\" compile result: " + message);
                break;
            case "style":
                message = course.checkStyle(student, task);
                System.out.println(student + "'s \"" + task + "\" style check result: " + message);
                break;
            case "test":
                message = course.test(student, task);
                System.out.println(student + "'s \"" + task + "\" test result: " + message);
                break;
            case "points":
                int points = course.countPoints(student, task);
                System.out.println(student + "'s \"" + task + "\" points: " + points);
                break;
            case "control":
                System.out.println(course.createControlPointsMessage());
                break;
            case "report":
                try (PrintWriter printWriter = new PrintWriter("report.html")) {
                    printWriter.println(course.createHtmlReport());
                }
                break;
            case "":
                System.out.println("No command provided");
                break;
            default:
                System.out.println("Bad command!");
        }
    }
}
