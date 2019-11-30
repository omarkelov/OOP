package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Visitors.Visitor;

/**
 * The <code>RecordNonDif</code> class represents a single
 * non-differential record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class RecordNonDif extends Record {

    private boolean passed;

    /**
     * Creates a new <code>RecordNonDif</code>.
     *
     * @param subject  the subject name.
     * @param semester the semester number.
     * @param passed   whether the credit is passed.
     */
    public RecordNonDif(String subject, int semester, boolean passed) {
        super(subject, semester);
        this.passed = passed;
    }

    /**
     * Returns whether the credit is passed.
     *
     * @return whether the credit is passed.
     */
    public boolean isPassed() {
        return passed;
    }

    /**
     * Returns whether the credit is passed.
     *
     * @return whether the credit is passed.
     */
    @Override
    public String getEvaluation() {
        return passed ? "Passed" : "Not passed";
    }

    // ---------------------------------------------------------------------------------------------

    public Double acceptDouble(Visitor<Double> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Boolean acceptBoolean(Visitor<Boolean> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Double getGradeDouble() {
        return Double.NaN;
    }

    @Override
    public Boolean isIncreasedScholarship() {
        return passed;
    }
}
