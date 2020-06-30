package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.git.GitProvider;
import ru.nsu.fit.markelov.gradle.GradleProvider;
import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.Group;
import ru.nsu.fit.markelov.objects.Lesson;
import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;
import ru.nsu.fit.markelov.objects.Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Course {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM");

    private final GitProvider gitProvider;
    private final GradleProvider gradleProvider;
    private final Group group;
    private final Tasks tasks;
    private final Set<Lesson> lessons;
    private final Set<ControlPoint> controlPoints;

    // student id -> attendance
    private final Map<String, Attendance> attendanceMap;
    // task id -> (student id -> task progress)
    private final Map<String, Map<String, TaskProgress>> taskProgressMap;
    // student id -> student progress
    private final Map<String, StudentProgress> studentProgressMap;

    public Course(GitProvider gitProvider, GradleProvider gradleProvider, Group group, Tasks tasks,
                  Set<Lesson> lessons, Set<ControlPoint> controlPoints) {
        this.gitProvider = gitProvider;
        this.gradleProvider = gradleProvider;
        this.group = group;
        this.tasks = tasks;
        this.lessons = lessons;
        this.controlPoints = controlPoints;

        attendanceMap = new TreeMap<>();
        taskProgressMap = new TreeMap<>();
        studentProgressMap = new TreeMap<>();

        initAttendance();
        initTaskProgress();
        initStudentProgress();
    }

    private void initAttendance() {
        for (Map.Entry<String, Student> studentEntry : group.getStudents().entrySet()) {
            String studentId = studentEntry.getKey();
            Student student = studentEntry.getValue();

            Attendance attendance = new Attendance(
                gitProvider.getCommitDates(student), lessons, controlPoints);

            attendanceMap.put(studentId, attendance);
        }
    }

    private void initTaskProgress() {
        for (Map.Entry<String, Task> taskEntry : tasks.getTasks().entrySet()) {
            String taskId = taskEntry.getKey();
            Task task = taskEntry.getValue();

            Map<String, TaskProgress> studentIdToTaskProgressMap = new TreeMap<>();

            for (Map.Entry<String, Student> studentEntry : group.getStudents().entrySet()) {
                String studentId = studentEntry.getKey();
                Student student = studentEntry.getValue();

                studentIdToTaskProgressMap.put(studentId,
                    new TaskProgress(gradleProvider.runTask(task, student)));
            }

            taskProgressMap.put(taskId, studentIdToTaskProgressMap);
        }
    }

    private void initStudentProgress() {
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

    public void passTask(String studentId, String taskId,
                         String date, String message) throws ParseException {
        int points = tasks.getTasks().get(taskId).getPoints();
        taskProgressMap.get(taskId).get(studentId).pass(points, date, message);
    }

    public void addExtraPoints(String studentId, String taskId, int points,
                               String date, String message) throws ParseException {
        taskProgressMap.get(taskId).get(studentId).addExtraPoints(points, date, message);
    }

    public void changeAttendance(String[] studentIds, String[] lessonDates,
                                 boolean attended) throws ParseException {
        for (String studentId : studentIds) {
            Attendance attendance = attendanceMap.get(studentId);
            attendance.change(lessonDates, attended);
        }
    }

    public String compile(String studentName, String taskName) {
        return "placeholder";
    }

    public String checkStyle(String studentName, String taskName) {
        return "placeholder";
    }

    public String test(String studentName, String taskName) {
        return "placeholder";
    }

    public int countPoints(String studentName, String taskName) {
        TaskProgress taskProgress = taskProgressMap.get(taskName).get(studentName);

        return taskProgress.countAllPoints();
    }

    public String createControlPointsMessage() {
        return "placeholder";
    }

    public String createReport() {
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
        for (ControlPoint controlPoint : controlPoints) {
            sb.append("<th>").append(controlPoint.getName()).append("</th>");
        }
        sb.append("\n            </tr>\n");
        for (Map.Entry<String, StudentProgress> studentProgressEntry : studentProgressMap.entrySet()) {
            String studentId = studentProgressEntry.getKey();
            StudentProgress studentProgress = studentProgressEntry.getValue();
            String studentName = group.getStudents().get(studentId).getFullName();
            sb.append("            <tr>\n");
            sb.append("                <td>").append(studentName).append("</td>");
            for (Integer points : studentProgress.calculatePoints()) {
                sb.append("<td>").append(points).append("</td>");
            }
            sb.append("\n            </tr>\n");

        }
        sb.append("        </table>\n");

        sb.append("        <h5>Attendance</h5>\n");
        sb.append("        <table>\n");
        sb.append("            <tr>\n");
        sb.append("                <th>Student</th>");
        for (Lesson lesson : lessons) {
            sb.append("<th>").append(DATE_FORMAT.format(lesson.getDate())).append("</th>");
        }
        for (ControlPoint controlPoint : controlPoints) {
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
                sb.append("<td>").append(taskProgress.isBuilt() ? "+" : "-").append("</td>");
                sb.append("<td>").append(taskProgress.isStyleChecked() ? "+" : "-").append("</td>");
                sb.append("<td>").append(taskProgress.isDocumentationGenerated() ? "+" : "-").append("</td>");
                sb.append("<td>").append(taskProgress.getTests()).append("</td>");
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
