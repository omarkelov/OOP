package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Visitors.Visitor;

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
     * @param  subject                  the subject name.
     * @param  semester                 the semester number.
     * @throws IllegalArgumentException if null or empty 'subject' parameter was passed.
     *                                  Or if 'semester' parameter less than one was passed.
     */
    public Record(String subject, int semester) {
        this.subject = subject;
        this.semester = semester;
        checkInput();
    }

    private void checkInput() {
        String message = null;

        if (subject == null || subject.isEmpty()) {
            message = "null or empty 'subject' parameter was passed to the Record class constructor.";
        } else if (semester < 1) {
            message = "Invalid 'semester' parameter was passed to the Record class constructor.";
        }

        if (message != null) {
            throw new IllegalArgumentException(message);
        }
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

    /**
     * Applies a visitor to this record.
     *
     * @param visitor a visitor to be applied.
     */
    public abstract void accept(Visitor visitor);
}
