package nsu.fit.markelov;

/**
 * The <code>NonDifRecord</code> class represents a single
 * non-differential record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class NonDifRecord extends Record {

    private boolean mPassed;

    /**
     * Creates a new <code>NonDifRecord</code>.
     *
     * @param subject  the subject name.
     * @param semester the semester number.
     * @param passed   whether the credit is passed.
     */
    public NonDifRecord(String subject, int semester, boolean passed) {
        super(subject, semester);
        mPassed = passed;
    }

    /**
     * Returns whether the credit is passed.
     *
     * @return whether the credit is passed.
     */
    public boolean isPassed() {
        return mPassed;
    }

    /**
     * Returns whether the credit is passed.
     *
     * @return whether the credit is passed.
     */
    public String getEvaluation() {
        return mPassed ? "Passed" : "Not passed";
    }
}
