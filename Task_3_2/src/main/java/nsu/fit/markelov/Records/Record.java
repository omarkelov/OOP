package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Visitors.Visitor;

import java.util.InputMismatchException;

/**
 * The <code>Record</code> class represents a single record
 * in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public abstract class Record {

    private String subject;
    private int semester;

    /**
     * Creates a new <code>Record</code>.
     *
     * @param subject                 the subject name.
     * @param semester                the semester number.
     * @throws InputMismatchException if null or empty 'subject' parameter was passed.
     *                                Or if 'semester' parameter less than one was passed.
     */
    public Record(String subject, int semester) throws InputMismatchException {
        this.subject = subject;
        this.semester = semester;
        checkInput();
    }

    /**
     * Returns the subject of the record.
     *
     * @return the subject of the record.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns the semester of the record.
     *
     * @return the semester of the record.
     */
    public int getSemester() {
        return semester;
    }

    /**
     * Returns the evaluation of the record.
     *
     * @return the evaluation of the record.
     */
    public abstract String getEvaluation();

    private void checkInput() throws InputMismatchException {
        if (subject == null || subject.isEmpty()) {
            throw new InputMismatchException("null or empty 'subject' parameter was passed to the" +
                    " Record class constructor.");
        }

        if (semester < 1) {
            throw new InputMismatchException("Invalid 'semester' parameter was passed to the" +
                    " Record class constructor.");
        }
    }

    // ---------------------------------------------------------------------------------------------

    public abstract Double acceptDouble(Visitor<Double> visitor);

    public abstract Boolean acceptBoolean(Visitor<Boolean> visitor);

    public abstract Double getGradeDouble();

    public abstract Boolean isIncreasedScholarship();
}
