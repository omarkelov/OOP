package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.gradle.Test;
import ru.nsu.fit.markelov.gradle.TaskResult;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.markelov.Main.DATE_FORMAT;

public class TaskProgress {
    private final TaskResult taskResult;

    private String tests;
    private TaskPoints creditPoints;
    private List<TaskPoints> extraPointsList;

    public TaskProgress(TaskResult taskResult) {
        this.taskResult = taskResult;
        extraPointsList = new ArrayList<>();

        init();
    }

    private void init() {
        int passedTests = 0;
        int failedTests = 0;
        for (Test test : taskResult.getTests()) {
            if (test.isPassed()) {
                passedTests++;
            } else {
                failedTests++;
            }
        }
        tests = passedTests + "/" + (passedTests + failedTests);
    }

    public void pass(int points, String date, String message) throws ParseException {
        creditPoints = new TaskPoints();
        creditPoints.setPoints(points);
        creditPoints.setDate(DATE_FORMAT.parse(date));
        creditPoints.setMessage(message);
    }

    public void addExtraPoints(int points, String date, String message) throws ParseException {
        TaskPoints extraPoints = new TaskPoints();
        extraPoints.setPoints(points);
        extraPoints.setDate(DATE_FORMAT.parse(date));
        extraPoints.setMessage(message);
        extraPointsList.add(extraPoints);
    }

    public int countCreditPoints() {
        return creditPoints != null ? creditPoints.getPoints() : 0;
    }

    public int countExtraPoints() {
        int extraPoints = 0;
        for (TaskPoints points : extraPointsList) {
            extraPoints += points.getPoints();
        }
        return extraPoints;
    }

    public int countAllPoints() {
        return countCreditPoints() + countExtraPoints();
    }

    /**
     * Returns build.
     *
     * @return build.
     */
    public Boolean isBuilt() {
        return taskResult.isBuilt();
    }

    /**
     * Returns style.
     *
     * @return style.
     */
    public Boolean isStyleChecked() {
        return taskResult.isStyleChecked();
    }

    /**
     * Returns documentation.
     *
     * @return documentation.
     */
    public Boolean isDocumentationGenerated() {
        return taskResult.isDocumentationGenerated();
    }

    /**
     * Returns tests.
     *
     * @return tests.
     */
    public String getTests() {
        return tests;
    }

    /**
     * Returns creditPoints.
     *
     * @return creditPoints.
     */
    public TaskPoints getCreditPoints() {
        return creditPoints;
    }

    /**
     * Sets creditPoints.
     */
    public void setCreditPoints(TaskPoints creditPoints) {
        this.creditPoints = creditPoints;
    }

    /**
     * Returns extraPointsList.
     *
     * @return extraPointsList.
     */
    public List<TaskPoints> getExtraPointsList() {
        return extraPointsList;
    }

    /**
     * Sets extraPointsList.
     */
    public void setExtraPointsList(List<TaskPoints> extraPointsList) {
        this.extraPointsList = extraPointsList;
    }
}
