package ru.nsu.fit.markelov.gradle;

import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;

import java.util.List;

public interface GradleProvider {
    void setWorkingDirectory(String directory);
    String compile(Student student, Task task);
    String checkStyle(Student student, Task task);
    List<Test> test(Student student, Task task);
    TaskResult getTaskResult(Student student, Task task);
}
