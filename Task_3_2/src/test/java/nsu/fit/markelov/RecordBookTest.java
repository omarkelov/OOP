package nsu.fit.markelov;

import nsu.fit.markelov.Records.RealRecords.ClassicCredit;
import nsu.fit.markelov.Records.RealRecords.GradedCredit;
import nsu.fit.markelov.Records.RealRecords.Exam;
import org.junit.Assert;
import org.junit.Test;

public class RecordBookTest {

    @Test
    public void testMyRecordBook() {
        RecordBook recordBook = new RecordBook(8, 2);

        recordBook.addRecord(new ClassicCredit("Digital Platforms", 1, true));
        recordBook.addRecord(new ClassicCredit("Foreign Language", 1, true));
        recordBook.addRecord(new ClassicCredit("Physical Culture", 1, true));
        recordBook.addRecord(new ClassicCredit("Physical Culture and Sports (elective subjects)", 1, true));
        recordBook.addRecord(new GradedCredit("Imperative Programming", 1, 5));
        recordBook.addRecord(new GradedCredit("Declarative Programming", 1, 5));
        recordBook.addRecord(new GradedCredit("History", 1, 5));
        recordBook.addRecord(new GradedCredit("Fundamentals of Speech", 1, 5));
        recordBook.addRecord(new Exam("Introduction to Algebra and Analysis", 1, 5));
        recordBook.addRecord(new Exam("Introduction to Discrete Mathematics and Mathematical Logic", 1, 4));

        recordBook.addRecord(new ClassicCredit("Physical Culture", 2, true));
        recordBook.addRecord(new ClassicCredit("Physical Culture and Sports (elective subjects)", 2, true));
        recordBook.addRecord(new GradedCredit("Digital Platforms", 2, 5));
        recordBook.addRecord(new GradedCredit("Declarative Programming", 2, 4));
        recordBook.addRecord(new GradedCredit("Foreign Language", 2, 4));
        recordBook.addRecord(new Exam("Introduction to Algebra and Analysis", 2, 4));
        recordBook.addRecord(new Exam("Introduction to Discrete Mathematics and Mathematical Logic", 2, 5));
        recordBook.addRecord(new Exam("Imperative Programming", 2, 5));

        double delta = 0.000001d;
        Assert.assertEquals(4.666666666666667d, recordBook.getRecordBookAverage(), delta);
        Assert.assertEquals(4.625d, recordBook.getDiplomaAverage(), delta);
        Assert.assertFalse(recordBook.isIncreasedScholarship());
    }
}
