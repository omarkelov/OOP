package ru.nsu.fit.markelov.git;

import ru.nsu.fit.markelov.objects.Student;

import java.util.Date;
import java.util.List;

public interface GitProvider {
    void setWorkingDirectory(String directory);
    void setUser(String login, String password);
    void clone(Student student);
    void pull(Student student);
    List<Date> getCommitDates(Student student);
}
