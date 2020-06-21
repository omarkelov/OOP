package ru.nsu.fit.markelov.git;

import ru.nsu.fit.markelov.objects.StudentObject;

import java.util.Date;
import java.util.List;

public interface Git {
    List<Date> getCommitDates(StudentObject studentObject);
}
