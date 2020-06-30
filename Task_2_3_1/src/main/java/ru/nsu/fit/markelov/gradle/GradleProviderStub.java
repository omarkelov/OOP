package ru.nsu.fit.markelov.gradle;

import ru.nsu.fit.markelov.objects.Settings;
import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class GradleProviderStub implements GradleProvider {

    public GradleProviderStub(Settings settings) {
        System.out.println(settings.getWorkingDirectory());
        System.out.println(settings.getGitLogin());
        System.out.println(settings.getGitPassword());
    }

    @Override
    public void setWorkingDirectory(String directory) {}

    @Override
    public TestResult runTask(Task task, Student student) {
        switch (task.getId()) {
            case "Task_2_1_1":
                if (student.getId().equals("Oleg")) {
                    return new TestResultHardcoded(true, true, true, generateTests(33, 0));
                }

                return new TestResultHardcoded(generateTests(0, 0));
            case "Task_2_2_1":
                if (student.getId().equals("Oleg")) {
                    return new TestResultHardcoded(true, true, true, generateTests(25, 0));
                }

                return new TestResultHardcoded(generateTests(0, 0));
            case "Task_2_3_1":
                if (student.getId().equals("Oleg")) {
                    return new TestResultHardcoded(true, false, false, generateTests(0, 0));
                }

                return new TestResultHardcoded(generateTests(0, 0));
            default:
                return new TestResultHardcoded(generateTests(0, 0));
        }
    }

    private List<Test> generateTests(int passed, int failed) {
        List<Test> tests = new ArrayList<>();

        for (int i = 0; i < passed; i++) {
            tests.add(new TestHardcoded("passed_" + i, true));
        }

        for (int i = 0; i < failed; i++) {
            tests.add(new TestHardcoded("failed_" + i, false));
        }

        return tests;
    }
}
