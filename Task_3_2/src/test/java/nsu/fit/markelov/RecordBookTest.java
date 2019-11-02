package nsu.fit.markelov;

import org.junit.Assert;
import org.junit.Test;

public class RecordBookTest {

    @Test
    public void test() {
        RecordBook recordBook;
        try {
            recordBook = new RecordBook(8, 2);
        } catch (Exception e) {
            recordBook = null;
            Assert.fail();
        }

        recordBook.addRecord(new ClassicCredit("Digital Platforms", true, 1));
        recordBook.addRecord(new ClassicCredit("Foreign Language", true, 1));
        recordBook.addRecord(new ClassicCredit("Physical Culture", true, 1));
        recordBook.addRecord(new ClassicCredit("Physical Culture and Sports (elective subjects)", true, 1));
        recordBook.addRecord(new DifferentialCredit("Imperative Programming", 5, 1));
        recordBook.addRecord(new DifferentialCredit("Declarative Programming", 5, 1));
        recordBook.addRecord(new DifferentialCredit("History", 5, 1));
        recordBook.addRecord(new DifferentialCredit("Fundamentals of Speech", 5, 1));
        recordBook.addRecord(new Exam("Introduction to Algebra and Analysis", 5, 1));
        recordBook.addRecord(new Exam("Introduction to Discrete Mathematics and Mathematical Logic", 4, 1));

        recordBook.addRecord(new ClassicCredit("Physical Culture", true, 2));
        recordBook.addRecord(new ClassicCredit("Physical Culture and Sports (elective subjects)", true, 2));
        recordBook.addRecord(new DifferentialCredit("Digital Platforms", 5, 2));
        recordBook.addRecord(new DifferentialCredit("Declarative Programming", 4, 2));
        recordBook.addRecord(new DifferentialCredit("Foreign Language", 4, 2));
        recordBook.addRecord(new Exam("Introduction to Algebra and Analysis", 4, 2));
        recordBook.addRecord(new Exam("Introduction to Discrete Mathematics and Mathematical Logic", 5, 2));
        recordBook.addRecord(new Exam("Imperative Programming", 5, 2));

        Assert.assertEquals(4.666666666666667d, recordBook.getAverage(), 0.000001d);
        Assert.assertEquals(4.625d, recordBook.getDiplomaAverage(), 0.000001d);
        Assert.assertFalse(recordBook.isIncreasedScholarships());
    }
}
