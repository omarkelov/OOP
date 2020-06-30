package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.objects.ControlPoint;

import java.util.Collection;
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

    public Collection<Integer> calculatePoints() {
        // control point -> points
        Map<ControlPoint, Integer> pointsMap = new TreeMap<>();

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

            pointsMap.put(controlPoint, points);
        }

        return pointsMap.values();
    }
}
