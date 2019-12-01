package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.ClassicRecord;
import nsu.fit.markelov.Records.GradedRecord;

/**
 * <code>IncreasedScholarshipVisitor</code> class is a simple visitor used for defining whether the
 * student should get an increased scholarship.
 * <p>
 * <code>IncreasedScholarshipVisitor</code> implements <code>Visitor</code> interface.
 *
 * @author Oleg Markelov
 * @see    Visitor
 */

public class PositiveGradesVisitor implements Visitor {

    private boolean allGradesPositive = true;

    /**
     * Cancels the assumption that all the grades are positive if the grade is under 4.
     *
     * @param gradedRecord the record to visit.
     */
    @Override
    public void visit(GradedRecord gradedRecord) {
        if (gradedRecord.getGrade() < 4) {
            allGradesPositive = false;
        }
    }

    /**
     * Cancels the assumption that all the grades are positive if the record is not passed.
     *
     * @param classicRecord the record to visit.
     */
    @Override
    public void visit(ClassicRecord classicRecord) {
        if (!classicRecord.isPassed()) {
            allGradesPositive = false;
        }
    }

    /**
     * Returns whether all the grades are positive.
     *
     * @return whether all the grades are positive.
     */
    public boolean areAllGradesPositive() {
        return allGradesPositive;
    }
}
