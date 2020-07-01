package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.objects.ControlPoint;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StudentProgress {
    private final Set<ControlPoint> controlPoints;
    private final List<TaskProgress> taskProgressList;

    public StudentProgress(Set<ControlPoint> controlPoints, List<TaskProgress> taskProgressList) {
        this.controlPoints = controlPoints;
        this.taskProgressList = taskProgressList;
    }

    public Map<ControlPoint, String> calculatePoints() {
        // control point -> points
        Map<ControlPoint, String> pointsMap = new TreeMap<>();

        for (ControlPoint controlPoint : controlPoints) {
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
