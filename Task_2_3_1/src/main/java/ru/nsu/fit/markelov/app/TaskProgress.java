package ru.nsu.fit.markelov.app;

import ru.nsu.fit.markelov.gradle.Test;
import ru.nsu.fit.markelov.gradle.TestResult;

import java.text.ParseException;
import java.util.Date;

import static ru.nsu.fit.markelov.Main.DATE_FORMAT;

public class TaskProgress {
    private final TestResult testResult;

    private String tests;
    private int creditPoints;
    private int extraPoints;
    private Date creditPointsDate;
    private Date extraPointsDate;

    public TaskProgress(TestResult testResult) {
        this.testResult = testResult;

        init();
    }

    private void init() {
        int passedTests = 0;
        int failedTests = 0;
        for (Test test : testResult.getTests()) {
            if (test.isPassed()) {
                passedTests++;
            } else {
                failedTests++;
            }
        }
        tests = passedTests + "/" + failedTests;
    }

    public void pass(int points, String date, String message, boolean extra) throws ParseException {
        if (!extra) {
            creditPoints = points;
            creditPointsDate = DATE_FORMAT.parse(date);
        } else {
            extraPoints = points;
            extraPointsDate = DATE_FORMAT.parse(date);
        }
    }

    /**
     * Returns build.
     *
     * @return build.
     */
    public boolean isBuilt() {
        return testResult.isBuilt();
    }

    /**
     * Returns style.
     *
     * @return style.
     */
    public boolean isStyleChecked() {
        return testResult.isStyleChecked();
    }

    /**
     * Returns documentation.
     *
     * @return documentation.
     */
    public boolean isDocumentationGenerated() {
        return testResult.isDocumentationGenerated();
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
    public int getCreditPoints() {
        return creditPoints;
    }

    /**
     * Returns extraPoints.
     *
     * @return extraPoints.
     */
    public int getExtraPoints() {
        return extraPoints;
    }

    /**
     * Returns creditPointsDate.
     *
     * @return creditPointsDate.
     */
    public Date getCreditPointsDate() {
        return creditPointsDate;
    }

    /**
     * Returns extraPointsDate.
     *
     * @return extraPointsDate.
     */
    public Date getExtraPointsDate() {
        return extraPointsDate;
    }
}
