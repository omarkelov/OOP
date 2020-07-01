package ru.nsu.fit.markelov.git;

import ru.nsu.fit.markelov.objects.Student;

import java.util.Date;
import java.util.List;

public interface GitProvider {
    void setWorkingDirectory(String directory) throws GitException;
    void setUser(String login, String password) throws GitException;
    boolean exists(Student student) throws GitException;
    void clone(Student student) throws GitException;
    void pull(Student student) throws GitException;
    List<Date> getCommitDates(Student student) throws GitException;
}
