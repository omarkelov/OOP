package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.ClassicRecord;
import nsu.fit.markelov.Records.GradedRecord;

/**
 * <code>IncreasedScholarshipVisitor</code> class is a simple visitor
 * used for defining whether the student should get an increased scholarship.
 * <p>
 * <code>IncreasedScholarshipVisitor</code> implements <code>Visitor</code>
 * interface.
 *
 * @author Oleg Markelov
 * @see    Visitor
 */

public class IncreasedScholarshipVisitor implements Visitor {

    private boolean increasedScholarship = true;

    /**
     * Cancels an increased scholarship if the grade is not 5.
     *
     * @param gradedRecord the record to be visited.
     */
    @Override
    public void visit(GradedRecord gradedRecord) {
        if (gradedRecord.getGrade() != 5) {
            increasedScholarship = false;
        }
    }

    /**
     * Cancels an increased scholarship if the record is not passed.
     *
     * @param classicRecord the record to be visited.
     */
    @Override
    public void visit(ClassicRecord classicRecord) {
        if (!classicRecord.isPassed()) {
            increasedScholarship = false;
        }
    }

    /**
     * Returns whether the student should get an increased scholarship.
     *
     * @return whether the student should get an increased scholarship.
     */
    public boolean isIncreasedScholarship() {
        return increasedScholarship;
    }
}
