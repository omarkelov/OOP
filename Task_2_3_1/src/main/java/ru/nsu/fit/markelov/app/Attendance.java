package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.objects.ControlPointObject;
import ru.nsu.fit.markelov.objects.LessonObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.Main.DATE_FORMAT;

public class Attendance {
    private List<Date> commitDates;
    private Set<LessonObject> lessonObjects;
    private Set<ControlPointObject> controlPointObjects;

    // lesson date -> student attendance
    private Map<Date, Boolean> lessonDateToAttendanceMap;
    // control point -> attendance count
    private Map<ControlPointObject, String> controlPointToAttendanceCountMap;

    public Attendance(List<Date> commitDates, Set<LessonObject> lessonObjects, Set<ControlPointObject> controlPointObjects) {
        this.commitDates = commitDates;
        this.lessonObjects = lessonObjects;
        this.controlPointObjects = controlPointObjects;

        initAttendance();
        initAttendanceCount();
    }

    private void initAttendance() {
        lessonDateToAttendanceMap = new TreeMap<>();

        long prevLessonTime = 0;
        for (LessonObject lessonObject : lessonObjects) {
            Date lessonDate = lessonObject.getDate();
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

        for (ControlPointObject controlPointObject : controlPointObjects) {
            int lessonsCount = 0;
            int attendanceCount = 0;
            for (Map.Entry<Date, Boolean> entry : lessonDateToAttendanceMap.entrySet()) {
                long lessonTime = entry.getKey().getTime();
                long controlPointTime = controlPointObject.getDate().getTime();
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

            controlPointToAttendanceCountMap.put(controlPointObject, attendanceCount + "/" + lessonsCount);
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
