package nsu.fit.markelov;

import nsu.fit.markelov.Records.ClassicRecord;
import org.junit.Assert;
import org.junit.Test;

public class ClassicRecordTest {

    @Test
    public void testRecord() {
        ClassicRecord classicRecord = new ClassicRecord("subject", 1, true);
        Assert.assertEquals("subject", classicRecord.getSubject());
        Assert.assertEquals(1, classicRecord.getSemester());
        Assert.assertTrue(classicRecord.isPassed());
    }

    @Test
    public void testIllegalArgumentExceptions() {
        try {
            ClassicRecord classicRecord = new ClassicRecord("", 1, true);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(ClassicRecord.INVALID_SUBJECT_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }

        try {
            ClassicRecord classicRecord = new ClassicRecord("subject", -2, true);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (!e.getMessage().startsWith(ClassicRecord.INVALID_SEMESTER_EXCEPTION_MESSAGE)) {
                Assert.fail();
            }
        }
    }
}
