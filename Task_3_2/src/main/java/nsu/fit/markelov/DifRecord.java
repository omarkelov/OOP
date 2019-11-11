package nsu.fit.markelov;

import java.util.InputMismatchException;

/**
 * The <code>DifRecord</code> class represents a single differential
 * record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class DifRecord extends Record {

    private int mPoint;

    /**
     * Creates a new <code>DifRecord</code>.
     *
     * @param subject                 the subject name.
     * @param semester                the semester number.
     * @param point                   the point from 2 to 5.
     * @throws InputMismatchException if the point is not between 2 and 5.
     */
    public DifRecord(String subject, int semester, int point) throws InputMismatchException {
        super(subject, semester);
        mPoint = point;
        checkInput();
    }

    /**
     * Returns the point of the record.
     *
     * @return the point of the record.
     */
    public int getPoint() {
        return mPoint;
    }

    /**
     * Returns the point of the record.
     *
     * @return the point of the record.
     */
    public String getEvaluation() {
        return mPoint + "";
    }

    private void checkInput() throws InputMismatchException {
        if (mPoint < 2 || mPoint > 5) {
            throw new InputMismatchException("Invalid 'point' parameter was passed to the DifRecord class constructor.");
        }
    }
}
