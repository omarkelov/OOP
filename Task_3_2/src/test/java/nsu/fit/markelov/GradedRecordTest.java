package nsu.fit.markelov;

import nsu.fit.markelov.Records.GradedRecord;
import org.junit.Assert;
import org.junit.Test;

public class GradedRecordTest {

    @Test
    public void testRecord() {
        GradedRecord gradedRecord = new GradedRecord("subject", 1, 3);
        Assert.assertEquals("subject", gradedRecord.getSubject());
        Assert.assertEquals(1, gradedRecord.getSemester());
        Assert.assertEquals(3, gradedRecord.getGrade());
    }

    @Test
    public void testIllegalArgumentException() {
        try {
            GradedRecord gradedRecord = new GradedRecord("subject", 1, 1);
            Assert.fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass().getSimpleName() + " got caught: " + e.getMessage());
            if (e.getMessage() != "Invalid 'grade' parameter was passed to the GradedRecord class constructor.") {
                Assert.fail();
            }
        }
    }
}
