package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.git.GitException;
import ru.nsu.fit.markelov.git.GitProvider;
import ru.nsu.fit.markelov.gradle.GradleException;
import ru.nsu.fit.markelov.gradle.GradleProvider;
import ru.nsu.fit.markelov.gradle.Test;
import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.ControlPoints;
import ru.nsu.fit.markelov.objects.Group;
import ru.nsu.fit.markelov.objects.Lesson;
import ru.nsu.fit.markelov.objects.Lessons;
import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;
import ru.nsu.fit.markelov.objects.Tasks;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class Course {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM");

    private final GitProvider gitProvider;
    private final GradleProvider gradleProvider;
    private final Group group;
    private final Tasks tasks;
    private final Lessons lessons;
    private final ControlPoints controlPoints;

    // student id -> attendance
    private final Map<String, Attendance> attendanceMap;
    // task id -> (student id -> task progress)
    private final Map<String, Map<String, TaskProgress>> taskProgressMap;
    // student id -> student progress
    private final Map<String, StudentProgress> studentProgressMap;

    public Course(GitProvider gitProvider, GradleProvider gradleProvider, Group group, Tasks tasks,
                  Lessons lessons, ControlPoints controlPoints)
                  throws GitException, GradleException, IllegalInputException {
        this.gitProvider = requireNonNull(gitProvider,
            "Course git provider " + NOT_NULL);
        this.gradleProvider = requireNonNull(gradleProvider,
            "Course gradle provider " + NOT_NULL);
        this.group = requireNonNull(group,
            "Course group " + NOT_NULL);
        this.tasks = requireNonNull(tasks,
            "Course tasks " + NOT_NULL);
        this.lessons = requireNonNull(lessons,
            "Course lessons " + NOT_NULL);
        this.controlPoints = requireNonNull(controlPoints,
            "Course control points " + NOT_NULL);

        attendanceMap = new TreeMap<>();
        taskProgressMap = new TreeMap<>();
        studentProgressMap = new TreeMap<>();

        initAttendance();
        initTaskProgress();
        initStudentProgress();
    }

    private void initAttendance() throws GitException, IllegalInputException {
        for (Map.Entry<String, Student> studentEntry : group.getStudents().entrySet()) {
            String studentId = studentEntry.getKey();
            Student student = studentEntry.getValue();

            Attendance attendance = new Attendance(
                gitProvider.getCommitDates(student), lessons, controlPoints);

            attendanceMap.put(studentId, attendance);
        }
    }

    private void initTaskProgress() throws GradleException, IllegalInputException {
        for (Map.Entry<String, Task> taskEntry : tasks.getTasks().entrySet()) {
            String taskId = taskEntry.getKey();
            Task task = taskEntry.getValue();

            Map<String, TaskProgress> studentIdToTaskProgressMap = new TreeMap<>();

            for (Map.Entry<String, Student> studentEntry : group.getStudents().entrySet()) {
                String studentId = studentEntry.getKey();
                Student student = studentEntry.getValue();

                studentIdToTaskProgressMap.put(studentId,
                    new TaskProgress(gradleProvider.getTaskResult(student, task)));
            }

            taskProgressMap.put(taskId, studentIdToTaskProgressMap);
        }
    }

    private void initStudentProgress() throws IllegalInputException {
        for (Map.Entry<String, Student> studentEntry : group.getStudents().entrySet()) {
            String studentId = studentEntry.getKey();

            List<TaskProgress> taskProgressList = new ArrayList<>();
            for (Map.Entry<String, Map<String, TaskProgress>> taskProgressEntry : taskProgressMap.entrySet()) {
                Map<String, TaskProgress> studentIdToTaskProgressMap = taskProgressEntry.getValue();

                taskProgressList.add(studentIdToTaskProgressMap.get(studentId));
            }

            studentProgressMap.put(studentId, new StudentProgress(controlPoints, taskProgressList));
        }
    }

    public void passTask(String studentId, String taskId, String date, String message)
                         throws ParseException, IllegalInputException {
        Task task = tasks.getTasks().get(taskId);
        if (task == null) {
            throw new IllegalInputException("No such task: " + taskId);
        }

        int points = task.getPoints();

        Map<String, TaskProgress> taskProgressMap = this.taskProgressMap.get(taskId);
        if (taskProgressMap == null) {
            throw new IllegalInputException("No such task: " + taskId);
        }

        TaskProgress taskProgress = taskProgressMap.get(studentId);
        if (taskProgress == null) {
            throw new IllegalInputException("No such student: " + studentId);
        }

        taskProgress.pass(points, date, message);
    }

    public void addExtraPoints(String studentId, String taskId, int points, String date,
                               String message) throws ParseException, IllegalInputException {
        Map<String, TaskProgress> taskProgressMap = this.taskProgressMap.get(taskId);
        if (taskProgressMap == null) {
            throw new IllegalInputException("No such task: " + taskId);
        }

        TaskProgress taskProgress = taskProgressMap.get(studentId);
        if (taskProgress == null) {
            throw new IllegalInputException("No such student: " + studentId);
        }

        taskProgress.addExtraPoints(points, date, message);
    }

    public void changeAttendance(String[] studentIds, String[] lessonDates, boolean attended)
                                 throws ParseException, IllegalInputException {
        for (String studentId : studentIds) {
            Attendance attendance = attendanceMap.get(studentId);

            if (attendance == null) {
                throw new IllegalInputException("No such student: " + studentId);
            }

            attendance.change(lessonDates, attended);
        }
    }

    public String compile(String studentId, String taskId)
                          throws GradleException, IllegalInputException {
        Student student = group.getStudents().get(studentId);
        if (student == null) {
            throw new IllegalInputException("No such student: " + studentId);
        }

        Task task = tasks.getTasks().get(taskId);
        if (task == null) {
            throw new IllegalInputException("No such task: " + taskId);
        }

        return gradleProvider.compile(student, task);
    }

    public String checkStyle(String studentId, String taskId)
                             throws GradleException, IllegalInputException {
        Student student = group.getStudents().get(studentId);
        if (student == null) {
            throw new IllegalInputException("No such student: " + studentId);
        }

        Task task = tasks.getTasks().get(taskId);
        if (task == null) {
            throw new IllegalInputException("No such task: " + taskId);
        }

        return gradleProvider.checkStyle(student, task);
    }

    public String test(String studentId, String taskId)
                       throws GradleException, IllegalInputException {
        Student student = group.getStudents().get(studentId);
        if (student == null) {
            throw new IllegalInputException("No such student: " + studentId);
        }

        Task task = tasks.getTasks().get(taskId);
        if (task == null) {
            throw new IllegalInputException("No such task: " + taskId);
        }

        List<Test> tests = gradleProvider.test(student, task);
        int passed = 0;
        int failed = 0;
        for (Test test : tests) {
            if (test.isPassed()) {
                passed++;
            } else {
                failed++;
            }
        }

        return passed + " out of " + (passed + failed) + " passed";
    }

    public int countPoints(String studentId, String taskId) throws IllegalInputException {
        Map<String, TaskProgress> taskProgressMap = this.taskProgressMap.get(taskId);
        if (taskProgressMap == null) {
            throw new IllegalInputException("No such task: " + taskId);
        }

        TaskProgress taskProgress = taskProgressMap.get(studentId);
        if (taskProgress == null) {
            throw new IllegalInputException("No such student: " + studentId);
        }

        return taskProgress.countAllPoints();
    }

    public String createControlPointsMessage() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, StudentProgress> studentProgressEntry : studentProgressMap.entrySet()) {
            String studentId = studentProgressEntry.getKey();
            StudentProgress studentProgress = studentProgressEntry.getValue();
            sb.append(studentId).append(": ");

            Map<ControlPoint, String> pointsMap = studentProgress.calculatePoints();
            String delim = ", ";
            for (Map.Entry<ControlPoint, String> pointsEntry : pointsMap.entrySet()) {
                ControlPoint controlPoint = pointsEntry.getKey();
                String points = pointsEntry.getValue();

                sb.append(controlPoint.getName()).append(" (").append(points).append(")").append(delim);
            }

            if (!pointsMap.isEmpty()) {
                sb.setLength(sb.length() - delim.length());
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String createHtmlReport() {
        StringBuilder sb = new StringBuilder();

        sb.append("<!DOCTYPE html>\n");
        sb.append("<html>\n");
        sb.append("    <head>\n");
        sb.append("        <meta charset=\"utf-8\">\n");
        sb.append("        <title>Report</title>\n");
        sb.append("    </head>\n");
        sb.append("    <body>\n");
        sb.append("        <h4>Group ").append(group.getName()).append("</h4>\n");

        sb.append("        <h5>").append("Progress").append("</h5>\n");
        sb.append("        <table>\n");
        sb.append("            <tr>\n");
        sb.append("                <th>Student</th>");
        for (ControlPoint controlPoint : controlPoints.getControlPoints()) {
            sb.append("<th>").append(controlPoint.getName()).append("</th>");
        }
        sb.append("\n            </tr>\n");
        for (Map.Entry<String, StudentProgress> studentProgressEntry : studentProgressMap.entrySet()) {
            String studentId = studentProgressEntry.getKey();
            StudentProgress studentProgress = studentProgressEntry.getValue();
            String studentName = group.getStudents().get(studentId).getFullName();
            sb.append("            <tr>\n");
            sb.append("                <td>").append(studentName).append("</td>");
            for (String points : studentProgress.calculatePoints().values()) {
                sb.append("<td>").append(points).append("</td>");
            }
            sb.append("\n            </tr>\n");
        }
        sb.append("        </table>\n");

        sb.append("        <h5>Attendance</h5>\n");
        sb.append("        <table>\n");
        sb.append("            <tr>\n");
        sb.append("                <th>Student</th>");
        for (Lesson lesson : lessons.getLessons()) {
            sb.append("<th>").append(DATE_FORMAT.format(lesson.getDate())).append("</th>");
        }
        for (ControlPoint controlPoint : controlPoints.getControlPoints()) {
            sb.append("<th>").append(controlPoint.getName()).append("</th>");
        }
        sb.append("\n            </tr>\n");
        for (Map.Entry<String, Attendance> attendanceEntry : attendanceMap.entrySet()) {
            String studentId = attendanceEntry.getKey();
            Attendance attendance = attendanceEntry.getValue();
            String studentName = group.getStudents().get(studentId).getFullName();
            sb.append("            <tr>\n");
            sb.append("                <td>").append(studentName).append("</td>");
            for (Boolean attended : attendance.getAttendance()) {
                sb.append("<td>").append(attended ? "+" : "-").append("</td>");
            }
            for (String attendanceCount : attendance.getAttendanceCounts()) {
                sb.append("<td>").append(attendanceCount).append("</td>");
            }
            sb.append("\n            </tr>\n");
        }
        sb.append("        </table>\n");

        for (Map.Entry<String, Map<String, TaskProgress>> taskProgressEntry : taskProgressMap.entrySet()) {
            String taskId = taskProgressEntry.getKey();
            Map<String, TaskProgress> studentIdToTaskProgressMap = taskProgressEntry.getValue();

            sb.append("        <h5>").append(taskId).append("</h5>\n");
            sb.append("        <table>\n");
            sb.append("            <tr>\n");
            sb.append("                <th>Student</th><th>Build</th><th>Style</th><th>Doc</th>");
            sb.append("<th>Tests</th><th>Credit</th><th>Add</th><th>Total</th>");
            sb.append("\n            </tr>\n");
            for (Map.Entry<String, TaskProgress> studentTaskProgressEntry : studentIdToTaskProgressMap.entrySet()) {
                String studentId = studentTaskProgressEntry.getKey();
                TaskProgress taskProgress = studentTaskProgressEntry.getValue();
                String studentName = group.getStudents().get(studentId).getFullName();
                sb.append("            <tr>\n");
                sb.append("                <td>").append(studentName).append("</td>");
                Boolean built = taskProgress.isBuilt();
                Boolean styleChecked = taskProgress.isStyleChecked();
                Boolean docsGenerated = taskProgress.isDocumentationGenerated();
                String tests = taskProgress.getTests();
                sb.append("<td>").append(built != null ? built ? "+" : "-" : "").append("</td>");
                sb.append("<td>").append(styleChecked != null ? styleChecked ? "+" : "-" : "").append("</td>");
                sb.append("<td>").append(docsGenerated != null ? docsGenerated ? "+" : "-" : "").append("</td>");
                sb.append("<td>").append(tests != null ? tests : "").append("</td>");
                sb.append("<td>").append(taskProgress.countCreditPoints()).append("</td>");
                sb.append("<td>").append(taskProgress.countExtraPoints()).append("</td>");
                sb.append("<td>").append(taskProgress.countAllPoints()).append("</td>");
                sb.append("\n            </tr>\n");
            }
            sb.append("        </table>\n");
        }

        sb.append("    </body>\n");
        sb.append("</html>\n");

        return sb.toString();
    }
}
