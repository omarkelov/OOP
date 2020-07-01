package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.ControlPoints;
import ru.nsu.fit.markelov.objects.Lesson;
import ru.nsu.fit.markelov.objects.Lessons;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.Main.DATE_FORMAT;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class Attendance {
    private final List<Date> commitDates;
    private final Lessons lessons;
    private final ControlPoints controlPoints;

    // lesson date -> student attendance
    private Map<Date, Boolean> lessonDateToAttendanceMap;
    // control point -> attendance count
    private Map<ControlPoint, String> controlPointToAttendanceCountMap;

    public Attendance(List<Date> commitDates, Lessons lessons, ControlPoints controlPoints)
                      throws IllegalInputException {
        this.commitDates = requireNonNull(commitDates,
            "Attendance commit dates " + NOT_NULL);
        this.lessons = requireNonNull(lessons,
            "Attendance lessons " + NOT_NULL);
        this.controlPoints = requireNonNull(controlPoints,
            "Attendance control points " + NOT_NULL);

        initAttendance();
        initAttendanceCount();
    }

    private void initAttendance() {
        lessonDateToAttendanceMap = new TreeMap<>();

        long prevLessonTime = 0;
        for (Lesson lesson : lessons.getLessons()) {
            Date lessonDate = lesson.getDate();
            lessonDateToAttendanceMap.put(lessonDate, false);

            long lessonTime = lessonDate.getTime();
            for (Date commitDate : commitDates) {
                long commitTime = commitDate.getTime();
                if (commitTime > prevLessonTime && commitTime <= lessonTime) {
                    lessonDateToAttendanceMap.put(lessonDate, true);
                }
            }

            prevLessonTime = lessonTime;
        }
    }

    private void initAttendanceCount() {
        controlPointToAttendanceCountMap = new TreeMap<>();

        for (ControlPoint controlPoint : controlPoints.getControlPoints()) {
            int lessonsCount = 0;
            int attendanceCount = 0;
            for (Map.Entry<Date, Boolean> entry : lessonDateToAttendanceMap.entrySet()) {
                long lessonTime = entry.getKey().getTime();
                long controlPointTime = controlPoint.getDate().getTime();
                boolean attended = entry.getValue();

                if (lessonTime <= controlPointTime) {
                    lessonsCount++;
                    if (attended) {
                        attendanceCount++;
                    }
                } else {
                    break;
                }
            }

            controlPointToAttendanceCountMap.put(controlPoint, attendanceCount + "/" + lessonsCount);
        }
    }

    public void change(String[] lessonDates, boolean attended) throws ParseException {
        for (String lessonDate : lessonDates) {
            Date date = DATE_FORMAT.parse(lessonDate);
            lessonDateToAttendanceMap.put(date, attended);
        }

        initAttendanceCount();
    }

    public Collection<Boolean> getAttendance() {
        return lessonDateToAttendanceMap.values();
    }

    public Collection<String> getAttendanceCounts() {
        return controlPointToAttendanceCountMap.values();
    }
}
