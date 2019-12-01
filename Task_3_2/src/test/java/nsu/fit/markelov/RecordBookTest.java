package nsu.fit.markelov;

import nsu.fit.markelov.Records.RealRecords.ClassicCredit;
import nsu.fit.markelov.Records.RealRecords.GradedCredit;
import nsu.fit.markelov.Records.RealRecords.Exam;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class RecordBookTest {

    private static final double DELTA = 0.000001d;

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

        Assert.assertEquals(4.666666666666667d, recordBook.getRecordBookAverage(), DELTA);
        Assert.assertEquals(4.625d, recordBook.getDiplomaAverage(), DELTA);
        Assert.assertFalse(recordBook.isHonoursDegree());
        Assert.assertFalse(recordBook.isIncreasedScholarship());
    }

    @Test
    public void testHonoursDegree() {
        Random random = new Random(9);

        RecordBook recordBook = new RecordBook(10, 10);
        recordBook.setDiplomaGrade(5);

        for (int i = 0; i < 75; i++) {
            recordBook.addRecord(new Exam(i+"", random.nextInt(10) + 1, 5));
        }

        for (int i = 0; i < 25; i++) {
            recordBook.addRecord(new Exam((i+75)+"", random.nextInt(10) + 1, 4));
        }

        Assert.assertTrue(recordBook.isHonoursDegree());

        recordBook.addRecord(new Exam("extra", random.nextInt(10) + 1, 4));

        Assert.assertFalse(recordBook.isHonoursDegree());
    }

    @Test
    public void testIllegalArgumentExceptions() {
        try {
            RecordBook recordBook = new RecordBook(0, 0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(RecordBook.INVALID_N_SEMESTERS_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            RecordBook recordBook = new RecordBook(8, 0);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(RecordBook.INVALID_LAST_SEMESTER_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            RecordBook recordBook = new RecordBook(8, 10);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(RecordBook.INVALID_PARAMS_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            RecordBook recordBook = new RecordBook(8, 8);
            recordBook.setDiplomaGrade(9);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(RecordBook.INVALID_DIPLOMA_GRADE_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }
    }
}
