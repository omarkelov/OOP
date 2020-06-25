package ru.nsu.fit.markelov;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import ru.nsu.fit.markelov.app.Course;
import ru.nsu.fit.markelov.git.GitHardcoded;
import ru.nsu.fit.markelov.gradle.GradleHardcoded;
import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.Group;
import ru.nsu.fit.markelov.objects.Lesson;
import ru.nsu.fit.markelov.objects.Tasks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Set;

public class Main {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private static final String GROUP_SCRIPT_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/engine/group/group.groovy";
    private static final String GROUP_DSL_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/scripts/group.dsl";
    private static final String GROUP_DSL_VAR = "groupDSL";

    private static final String TASKS_SCRIPT_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/engine/tasks/tasks.groovy";
    private static final String TASKS_DSL_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/scripts/tasks.dsl";
    private static final String TASKS_DSL_VAR = "tasksDSL";

    private static final String LESSONS_SCRIPT_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/engine/lessons/lessons.groovy";
    private static final String LESSONS_DSL_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/scripts/lessons.dsl";
    private static final String LESSONS_DSL_VAR = "lessonsDSL";

    private static final String CONTROL_POINTS_SCRIPT_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/engine/controlpoints/controlPoints.groovy";
    private static final String CONTROL_POINTS_DSL_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/scripts/controlPoints.dsl";
    private static final String CONTROL_POINTS_DSL_VAR = "controlPointsDSL";

    private static final String ATTENDANCE_SCRIPT_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/engine/attendance/attendance.groovy";
    private static final String ATTENDANCE_DSL_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/scripts/attendance.dsl";

    private static final String PASSING_SCRIPT_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/engine/passing/passing.groovy";
    private static final String PASSING_DSL_PATH =
        "src/main/groovy/ru/nsu/fit/markelov/scripts/passing.dsl";

    private static final String COURSE_DSL_VAR = "course";

    public static void main(String[] args) {
        try {
            Group group = (Group)
                runScript(GROUP_SCRIPT_PATH, GROUP_DSL_PATH, GROUP_DSL_VAR);

            Tasks tasks = (Tasks)
                runScript(TASKS_SCRIPT_PATH, TASKS_DSL_PATH, TASKS_DSL_VAR);

            @SuppressWarnings("unchecked")
            Set<Lesson> lessons = (Set<Lesson>)
                runScript(LESSONS_SCRIPT_PATH, LESSONS_DSL_PATH, LESSONS_DSL_VAR);

            @SuppressWarnings("unchecked")
            Set<ControlPoint> controlPoints = (Set<ControlPoint>) runScript(
                CONTROL_POINTS_SCRIPT_PATH, CONTROL_POINTS_DSL_PATH, CONTROL_POINTS_DSL_VAR);

            Course course = new Course(
                new GitHardcoded(), new GradleHardcoded(), group, tasks, lessons, controlPoints);

            runScript(ATTENDANCE_SCRIPT_PATH, ATTENDANCE_DSL_PATH, COURSE_DSL_VAR, course);

            runScript(PASSING_SCRIPT_PATH, PASSING_DSL_PATH, COURSE_DSL_VAR, course);

            System.out.println(course.createReport());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object runScript(String scriptPath, String dslPath,
                                    String variableName) throws IOException {
        Binding binding = new Binding();
        new GroovyShell(binding).evaluate(readFile(scriptPath) + readFile(dslPath));

        return binding.getVariable(variableName);
    }

    private static void runScript(String scriptPath, String dslPath,
                                  String variableName, Object variable) throws IOException {
        Binding binding = new Binding();
        binding.setVariable(variableName, variable);
        new GroovyShell(binding).evaluate(readFile(scriptPath) + readFile(dslPath));
    }

    private static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
