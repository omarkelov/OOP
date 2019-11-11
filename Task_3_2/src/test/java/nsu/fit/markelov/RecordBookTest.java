package nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class RecordBookTest {

    @Test
    public void test() {
        RecordBook recordBook = new RecordBook(8, 2);

        recordBook.addRecord(new NonDifRecord("Digital Platforms", 1, true));
        recordBook.addRecord(new NonDifRecord("Foreign Language", 1, true));
        recordBook.addRecord(new NonDifRecord("Physical Culture", 1, true));
        recordBook.addRecord(new NonDifRecord("Physical Culture and Sports (elective subjects)", 1, true));
        recordBook.addRecord(new DifRecord("Imperative Programming", 1, 5));
        recordBook.addRecord(new DifRecord("Declarative Programming", 1, 5));
        recordBook.addRecord(new DifRecord("History", 1, 5));
        recordBook.addRecord(new DifRecord("Fundamentals of Speech", 1, 5));
        recordBook.addRecord(new DifRecord("Introduction to Algebra and Analysis", 1, 5));
        recordBook.addRecord(new DifRecord("Introduction to Discrete Mathematics and Mathematical Logic", 1, 4));

        recordBook.addRecord(new NonDifRecord("Physical Culture", 2, true));
        recordBook.addRecord(new NonDifRecord("Physical Culture and Sports (elective subjects)", 2, true));
        recordBook.addRecord(new DifRecord("Digital Platforms", 2, 5));
        recordBook.addRecord(new DifRecord("Declarative Programming", 2, 4));
        recordBook.addRecord(new DifRecord("Foreign Language", 2, 4));
        recordBook.addRecord(new DifRecord("Introduction to Algebra and Analysis", 2, 4));
        recordBook.addRecord(new DifRecord("Introduction to Discrete Mathematics and Mathematical Logic", 2, 5));
        recordBook.addRecord(new DifRecord("Imperative Programming", 2, 5));

        double delta = 0.000001d;
        Assert.assertEquals(4.666666666666667d, recordBook.getRecordBookAverage(), delta);
        Assert.assertEquals(4.625d, recordBook.getDiplomaAverage(), delta);
        Assert.assertFalse(recordBook.isIncreasedScholarship());
    }
}
