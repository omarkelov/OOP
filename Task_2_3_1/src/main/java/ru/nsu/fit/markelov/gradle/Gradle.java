package ru.nsu.fit.markelov.gradle;

import ru.nsu.fit.markelov.objects.Student;
import ru.nsu.fit.markelov.objects.Task;

public interface Gradle {
    TestResult runTask(Task task, Student student);
}
