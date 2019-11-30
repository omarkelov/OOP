package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Visitors.Visitor;

/**
 * The <code>ClassicRecord</code> class represents a single
 * non-differential record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class ClassicRecord extends Record {

    private boolean passed;

    /**
     * Creates a new <code>ClassicRecord</code>.
     *
     * @param subject  the subject name.
     * @param semester the semester number.
     * @param passed   whether the credit is passed.
     */
    public ClassicRecord(String subject, int semester, boolean passed) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
