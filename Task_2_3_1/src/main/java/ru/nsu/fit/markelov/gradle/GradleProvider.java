package ru.nsu.fit.markelov.gradle;

import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;

public interface GradleProvider {
    void setWorkingDirectory(String directory);
    TestResult runTask(Task task, Student student);
}
