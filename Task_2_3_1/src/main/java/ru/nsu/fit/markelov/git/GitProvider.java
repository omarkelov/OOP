package ru.nsu.fit.markelov.git;

import ru.nsu.fit.markelov.objects.Student;

import java.util.Date;
import java.util.List;

public interface GitProvider {
    List<Date> getCommitDates(Student student);
}
