package nsu.fit.markelov;

import java.util.InputMismatchException;

/**
 * The <code>Record</code> class represents a single record
 * in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public abstract class Record {

    private String mSubject;
    private int mSemester;

    /**
     * Creates a new <code>Record</code>.
     *
     * @param subject                 the subject name.
     * @param semester                the semester number.
     * @throws InputMismatchException if null or empty 'subject' parameter was passed.
     *                                Or if 'semester' parameter less than one was passed.
     */
    public Record(String subject, int semester) throws InputMismatchException {
        mSubject = subject;
        mSemester = semester;
        checkInput();
    }

    /**
     * Returns the subject of the record.
     *
     * @return the subject of the record.
     */
    public String getSubject() {
        return mSubject;
    }

    /**
     * Returns the semester of the record.
     *
     * @return the semester of the record.
     */
    public int getSemester() {
        return mSemester;
    }

    /**
     * Returns the evaluation of the record.
     *
     * @return the evaluation of the record.
     */
    public abstract String getEvaluation();

    private void checkInput() throws InputMismatchException {
        if (mSubject == null || mSubject.isEmpty()) {
            throw new InputMismatchException("null or empty 'subject' parameter was passed to the Record class constructor.");
        }

        if (mSemester < 1) {
            throw new InputMismatchException("Invalid 'semester' parameter was passed to the Record class constructor.");
        }
    }
}
