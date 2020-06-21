package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.git.Git;
import ru.nsu.fit.markelov.objects.ControlPointObject;
import ru.nsu.fit.markelov.objects.GroupObject;
import ru.nsu.fit.markelov.objects.LessonObject;
import ru.nsu.fit.markelov.objects.StudentObject;
import ru.nsu.fit.markelov.objects.TasksObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Course {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM");

    private Git git;
    private GroupObject groupObject;
    private TasksObject tasksObject;
    private Set<LessonObject> lessonObjects;
    private Set<ControlPointObject> controlPointObjects;

    private Map<String, Attendance> studentIdToAttendanceMap;

    public Course(Git git, GroupObject groupObject, TasksObject tasksObject,
                  Set<LessonObject> lessonObjects, Set<ControlPointObject> controlPointObjects) {
        this.git = git;
        this.groupObject = groupObject;
        this.tasksObject = tasksObject;
        this.lessonObjects = lessonObjects;
        this.controlPointObjects = controlPointObjects;

        studentIdToAttendanceMap = new TreeMap<>();

        for (Map.Entry<String, StudentObject> entry : groupObject.getStudentObjects().entrySet()) {
            String studentId = entry.getKey();
            StudentObject studentObject = entry.getValue();

            Attendance attendance = new Attendance(
                git.getCommitDates(studentObject), lessonObjects, controlPointObjects);

            studentIdToAttendanceMap.put(studentId, attendance);
        }
    }

    public void changeAttendance(String[] studentIds, String[] lessonDates,
                                 boolean attended) throws ParseException {
        for (String studentId : studentIds) {
            Attendance attendance = studentIdToAttendanceMap.get(studentId);
            attendance.change(lessonDates, attended);
        }
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
        sb.append("        <h4>Group ").append(groupObject.getName()).append("</h4>\n");
        sb.append("        <h5>Attendance</h5>\n");
        sb.append("        <table>\n");
        sb.append("            <tr>\n");
        sb.append("                <th>Student</th>");
        for (LessonObject lessonObject : lessonObjects) {
            sb.append("<th>").append(DATE_FORMAT.format(lessonObject.getDate())).append("</th>");
        }
        for (ControlPointObject controlPointObject : controlPointObjects) {
            sb.append("<th>").append(controlPointObject.getName()).append("</th>");
        }
        sb.append("\n            </tr>\n");
        for (Map.Entry<String, Attendance> entry : studentIdToAttendanceMap.entrySet()) {
            String studentName = groupObject.getStudentObjects().get(entry.getKey()).getFullName();
            sb.append("            <tr>\n");
            sb.append("                <td>").append(studentName).append("</td>");
            for (Boolean attended : entry.getValue().getAttendance()) {
                sb.append("<td>").append(attended ? "+" : "-").append("</td>");
            }
            for (String attendanceCount : entry.getValue().getAttendanceCounts()) {
                sb.append("<td>").append(attendanceCount).append("</td>");
            }
            sb.append("\n            </tr>\n");
        }
        sb.append("        </table>\n");
        sb.append("    </body>\n");
        sb.append("</html>\n");

        return sb.toString();
    }
}
