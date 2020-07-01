package ru.nsu.fit.markelov;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovyjarjarpicocli.CommandLine;
import ru.nsu.fit.markelov.app.Course;
import ru.nsu.fit.markelov.app.UserScriptException;
import ru.nsu.fit.markelov.git.GitException;
import ru.nsu.fit.markelov.git.GitProvider;
import ru.nsu.fit.markelov.git.GitProviderStub;
import ru.nsu.fit.markelov.gradle.GradleException;
import ru.nsu.fit.markelov.gradle.GradleProvider;
import ru.nsu.fit.markelov.gradle.GradleProviderStub;
import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.ControlPoints;
import ru.nsu.fit.markelov.objects.Group;
import ru.nsu.fit.markelov.objects.Lesson;
import ru.nsu.fit.markelov.objects.Lessons;
import ru.nsu.fit.markelov.objects.Settings;
import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Tasks;
import ru.nsu.fit.markelov.util.ErrorHelper;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Main class is a command line application aimed to help teachers manage the learning process of
 * OOP. It allows to write scripts on a specific dsl language based on Groovy. It also provides
 * several commands: update students' repositories, compile the task and generate a documentation,
 * check the code style for the task, run the tests for the task, calculate the points for the task,
 * create the report for every control point, create the .html report for the group.
 *
 * @author Oleg Markelov
 */
@CommandLine.Command(name = "GroovyDSL", description = "This application is aimed to help " +
    "teachers manage the learning process of OOP. It allows to write scripts on a specific dsl " +
    "language based on Groovy.")
public class Main implements Callable<Integer> {

    /**
     * Date format used in user scripts.
     */
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final String NO_MESSAGE = "[Command completed]";

    private static final String DEFAULT_SCRIPTS_DIR = "scripts/";
    private static final String RESOURCES_DIR = "src/main/resources";
    private static final String ENGINE_DIR = "/ru/nsu/fit/markelov/engine/";
    private static final String RULES_PATH = "/ru/nsu/fit/markelov/rules.txt";

    private static final String COURSE_DSL_VAR = "course";

    @CommandLine.Parameters(index = "0", defaultValue = "",
        description = "run one of the next commands: " +
            "update (update all the students' repositories), " +
            "compile (compile the task and generate a documentation), " +
            "style (check the code style for the task), " +
            "test (run the tests for the task), " +
            "points (calculate the points for the task), " +
            "control (create the report for every control point), " +
            "report (create the .html report for the group)")
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

    @CommandLine.Option(names = { "-r", "--rules" },
        description = "display rules of writing scripts and exit")
    private boolean rulesRequested;

    /**
     * Starts the application.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    /**
     * Executes a command passed as a command line argument.
     *
     * @return exit code.
     */
    @Override
    public Integer call() {
        try {
            if (rulesRequested) {
                System.out.println(readFile(getResourcePath(RULES_PATH)));

                return 0;
            }

            Path enginePath = getResourcePath(ENGINE_DIR);
            Path scriptsPath = Paths.get(scriptsDir);

            Settings settings = ((Settings) runScript(
                enginePath, scriptsPath, "settings")).validate();

            Group group = ((Group) runScript(
                enginePath, scriptsPath, "group")).validate();

            Tasks tasks = ((Tasks) runScript(
                enginePath, scriptsPath, "tasks")).validate();

            Lessons lessons = ((Lessons) runScript(
                enginePath, scriptsPath, "lessons")).validate();

            ControlPoints controlPoints = ((ControlPoints) runScript(
                enginePath, scriptsPath, "controlPoints")).validate();

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
        } catch (UserScriptException e) {
            ErrorHelper.printError("Cannot invoke the script (" + e.getMessage() + ")");
        } catch (IllegalInputException e) {
            ErrorHelper.printError("Illegal input (" + e.getMessage() + ")");
        } catch (GitException|GradleException e) {
            ErrorHelper.printError(e.getMessage());
        } catch (URISyntaxException e) {
            ErrorHelper.printError("Cannot open the resource (" + e.getMessage() + ")");
        } catch (IOException e) {
            ErrorHelper.printError("Cannot read the file (" + e.getMessage() + ")");
        }

        return 0;
    }

    private Path getResourcePath(String pathName) throws URISyntaxException, IOException {
        URL url = Main.class.getResource(pathName);
        if (url == null) {
            throw new IOException("No such resource: " + pathName);
        }

        URI uri = url.toURI();
        if (uri.getScheme().equals("jar")) {
            FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
            return fileSystem.getPath(pathName);
        } else {
            return Paths.get(RESOURCES_DIR + pathName);
        }
    }

    private Object runScript(Path enginePath, Path scriptsPath, String name)
            throws IOException, UserScriptException {
        Binding binding = new Binding();

        new GroovyShell(binding).evaluate(
            readFile(enginePath.resolve(name.toLowerCase()).resolve(name + ".groovy")) +
            wrapScriptWithTryCatch(readFile(scriptsPath.resolve(name + ".dsl")))
        );

        Exception e = (Exception) binding.getVariable("exceptionDSL");
        if (e != null) {
            throw new UserScriptException(e.getMessage());
        }

        return binding.getVariable(name + "DSL");
    }

    private void runScript(Path enginePath, Path scriptsPath, String name,
            String variableName, Object variable) throws IOException, UserScriptException {
        Binding binding = new Binding();
        binding.setVariable(variableName, variable);

        new GroovyShell(binding).evaluate(
            readFile(enginePath.resolve(name.toLowerCase()).resolve(name + ".groovy")) +
            wrapScriptWithTryCatch(readFile(scriptsPath.resolve(name + ".dsl")))
        );

        Exception e = (Exception) binding.getVariable("exceptionDSL");
        if (e != null) {
            throw new UserScriptException(e.getMessage());
        }
    }

    private String readFile(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private String wrapScriptWithTryCatch(String script) {
        return
        "try {" +
            script +
        "} catch (Exception e) {" +
            "exceptionDSL = e" +
        "}";
    }

    private void handleCommand(Course course) throws
                               FileNotFoundException, GradleException, IllegalInputException {
        String message;
        switch (command) {
            case "compile":
                validateArgs();
                message = course.compile(student, task);
                if (message == null || message.isEmpty()) {
                    message = NO_MESSAGE;
                }
                System.out.println(student + "'s \"" + task + "\" compile result: " + message);
                break;
            case "style":
                validateArgs();
                message = course.checkStyle(student, task);
                if (message == null || message.isEmpty()) {
                    message = NO_MESSAGE;
                }
                System.out.println(student + "'s \"" + task + "\" style check result: " + message);
                break;
            case "test":
                validateArgs();
                message = course.test(student, task);
                if (message == null || message.isEmpty()) {
                    message = NO_MESSAGE;
                }
                System.out.println(student + "'s \"" + task + "\" test result: " + message);
                break;
            case "points":
                validateArgs();
                int points = course.countPoints(student, task);
                System.out.println(student + "'s \"" + task + "\" points: " + points);
                break;
            case "control":
                message = course.createControlPointsMessage();
                if (message == null || message.isEmpty()) {
                    message = NO_MESSAGE;
                }
                System.out.println(message);
                break;
            case "report":
                try (PrintWriter printWriter = new PrintWriter("report.html")) {
                    message = course.createHtmlReport();
                    if (message == null || message.isEmpty()) {
                        message = NO_MESSAGE;
                    }
                    printWriter.println(message);
                }
                break;
            case "":
                ErrorHelper.printError("No command provided");
                break;
            default:
                ErrorHelper.printError("No such command: " + command);
        }
    }

    private void validateArgs() throws IllegalInputException {
        if (student.isEmpty()) {
            throw new IllegalInputException(
                "Argument 'student' must be provided for '" + command + "' command");
        }

        if (task.isEmpty()) {
            throw new IllegalInputException(
                "Argument 'task' must be provided for '" + command + "' command");
        }
    }
}
