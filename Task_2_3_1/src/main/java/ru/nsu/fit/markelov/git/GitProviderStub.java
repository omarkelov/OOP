package ru.nsu.fit.markelov.git;

import ru.nsu.fit.markelov.objects.Student;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ru.nsu.fit.markelov.Main.DATE_FORMAT;

public class GitProviderStub implements GitProvider {
    @Override
    public List<Date> getCommitDates(Student student) {
        List<Date> commitDates = new ArrayList<>();

        try {
            if (student.getId().equals("Oleg")) {
                commitDates.add(DATE_FORMAT.parse("04.03.2020"));
                commitDates.add(DATE_FORMAT.parse("04.03.2020"));
                commitDates.add(DATE_FORMAT.parse("10.06.2020"));
                commitDates.add(DATE_FORMAT.parse("01.12.2020"));
            } else {
                commitDates.add(DATE_FORMAT.parse("04.05.2020"));
                commitDates.add(DATE_FORMAT.parse("04.05.2020"));
                commitDates.add(DATE_FORMAT.parse("01.06.2020"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return commitDates;
    }
}
