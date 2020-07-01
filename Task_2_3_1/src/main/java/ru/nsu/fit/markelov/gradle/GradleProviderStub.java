package ru.nsu.fit.markelov.gradle;

import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class GradleProviderStub implements GradleProvider {

    @Override
    public void setWorkingDirectory(String directory) {}

    @Override
    public String compile(Student student, Task task) {
        boolean isMe = student.getId().equals("Oleg");
        switch (task.getId()) {
            case "Task_2_1_1":
                // falls through
            case "Task_2_2_1":
                return isMe ? "built, documentation generated" : "build failed";
            case "Task_2_3_1":
                return isMe ? "built, documentation not generated" : "build failed";
            default:
                return "build failed";
        }
    }

    @Override
    public String checkStyle(Student student, Task task) {
        boolean isMe = student.getId().equals("Oleg");
        switch (task.getId()) {
            case "Task_2_1_1":
                // falls through
            case "Task_2_2_1":
                return isMe ? "ok" : "failed";
            case "Task_2_3_1":
                return isMe ? "failed" : "failed";
            default:
                return "build failed";
        }
    }

    @Override
    public List<Test> test(Student student, Task task) {
        boolean isMe = student.getId().equals("Oleg");
        switch (task.getId()) {
            case "Task_2_1_1":
                return isMe ? generateTests(33, 0) : generateTests(0, 0);
            case "Task_2_2_1":
                return isMe ? generateTests(25, 0) : generateTests(0, 0);
            case "Task_2_3_1":
                return isMe ? generateTests(0, 0) : generateTests(0, 0);
            default:
                return null;
        }
    }

    @Override
    public TaskResult getTaskResult(Student student, Task task) {
        switch (task.getId()) {
            case "Task_2_1_1":
                if (student.getId().equals("Oleg")) {
                    return new TaskResultStub(true, true, true, generateTests(34, 0));
                }

                return new TaskResultStub(true, false, false, generateTests(0, 0));
            case "Task_2_2_1":
                if (student.getId().equals("Oleg")) {
                    return new TaskResultStub(true, true, true, generateTests(25, 0));
                }

                return new TaskResultStub(generateTests(0, 0));
            case "Task_2_3_1":
                if (student.getId().equals("Oleg")) {
                    return new TaskResultStub(true, false, false, generateTests(0, 0));
                }

                return new TaskResultStub(generateTests(0, 0));
            default:
                return new TaskResultStub(generateTests(0, 0));
        }
    }

    private List<Test> generateTests(int passed, int failed) {
        List<Test> tests = new ArrayList<>();

        for (int i = 0; i < passed; i++) {
            tests.add(new TestStub("passed_" + i, true));
        }

        for (int i = 0; i < failed; i++) {
            tests.add(new TestStub("failed_" + i, false));
        }

        return tests;
    }
}
