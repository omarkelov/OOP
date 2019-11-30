package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.ClassicRecord;
import nsu.fit.markelov.Records.GradedRecord;

/**
 * <code>AverageGradeVisitor</code> class is a simple visitor
 * used for accumulating the sum of grades of all the visited
 * records to get the average grade in the end.
 * <p>
 * <code>AverageGradeVisitor</code> implements <code>Visitor</code>
 * interface.
 *
 * @author Oleg Markelov
 * @see    Visitor
 */

public class AverageGradeVisitor implements Visitor {

    private int gradeSum = 0;
    private int nGrades = 0;

    /**
     * Accumulates the sum of all the grades.
     *
     * @param gradedRecord the record to be visited.
     */
    @Override
    public void visit(GradedRecord gradedRecord) {
        gradeSum += gradedRecord.getGrade();
        nGrades++;
    }

    /**
     * Accumulates nothing.
     *
     * @param classicRecord the record to be visited.
     */
    @Override
    public void visit(ClassicRecord classicRecord) {}

    /**
     * Returns the average grade of all the visited records.
     *
     * @return the average grade of all the visited records.
     */
    public double getAverage() {
        return (double) gradeSum / nGrades;
    }
}
