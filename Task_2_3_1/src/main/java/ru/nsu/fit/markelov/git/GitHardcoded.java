package ru.nsu.fit.markelov.git;

import ru.nsu.fit.markelov.objects.StudentObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.nsu.fit.markelov.Main.DATE_FORMAT;

public class GitHardcoded implements Git {
    @Override
    public List<Date> getCommitDates(StudentObject studentObject) {
        List<Date> commitDates = new ArrayList<>();

        if (studentObject.getNickname().equals("Oleg")) {
            try {
                commitDates.add(DATE_FORMAT.parse("04.03.2020"));
                commitDates.add(DATE_FORMAT.parse("04.03.2020"));
                commitDates.add(DATE_FORMAT.parse("10.06.2020"));
                commitDates.add(DATE_FORMAT.parse("01.12.2020"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return commitDates;
    }
}
