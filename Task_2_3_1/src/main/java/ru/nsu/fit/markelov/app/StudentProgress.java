package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.objects.ControlPoint;
import ru.nsu.fit.markelov.objects.ControlPoints;
import ru.nsu.fit.markelov.util.validation.IllegalInputException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static ru.nsu.fit.markelov.util.validation.IllegalInputException.NOT_NULL;
import static ru.nsu.fit.markelov.util.validation.IllegalInputException.requireNonNull;

public class StudentProgress {
    private final ControlPoints controlPoints;
    private final List<TaskProgress> taskProgressList;

    public StudentProgress(ControlPoints controlPoints, List<TaskProgress> taskProgressList)
                           throws IllegalInputException {
        this.controlPoints = requireNonNull(controlPoints,
            "StudentProgress control points " + NOT_NULL);
        this.taskProgressList = requireNonNull(taskProgressList,
            "StudentProgress task progress list " + NOT_NULL);
    }

    public Map<ControlPoint, String> calculatePoints() {
        // control point -> points
        Map<ControlPoint, String> pointsMap = new TreeMap<>();

        for (ControlPoint controlPoint : controlPoints.getControlPoints()) {
            long controlPointTime = controlPoint.getDate().getTime();

            int points = 0;
            for (TaskProgress taskProgress : taskProgressList) {
                TaskPoints creditPoints = taskProgress.getCreditPoints();
                if (creditPoints != null) {
                    long creditPointsTime = creditPoints.getDate().getTime();
                    if (creditPointsTime < controlPointTime) {
                        points += creditPoints.getPoints();
                    }
                }

                for (TaskPoints extraPoints : taskProgress.getExtraPointsList()) {
                    long extraPointsTime = extraPoints.getDate().getTime();
                    if (extraPointsTime < controlPointTime) {
                        points += extraPoints.getPoints();
                    }
                }
            }

            String grade = "Poor";
            if (points >= controlPoint.getPointsForSat()) grade = "Sat";
            if (points >= controlPoint.getPointsForGood()) grade = "Good";
            if (points >= controlPoint.getPointsForExc()) grade = "Exc";

            pointsMap.put(controlPoint, points + " - " + grade);
        }

        return pointsMap;
    }
}
