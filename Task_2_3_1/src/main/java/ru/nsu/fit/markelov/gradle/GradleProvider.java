package ru.nsu.fit.markelov.gradle;

import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;

import java.util.List;

public interface GradleProvider {
    void setWorkingDirectory(String directory) throws GradleException;
    String compile(Student student, Task task) throws GradleException;
    String checkStyle(Student student, Task task) throws GradleException;
    List<Test> test(Student student, Task task) throws GradleException;
    TaskResult getTaskResult(Student student, Task task) throws GradleException;
}
