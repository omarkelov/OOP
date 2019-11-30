package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Visitors.Visitable;
import nsu.fit.markelov.Visitors.Visitor;

/**
 * The <code>Record</code> class represents a single record
 * in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public abstract class Record implements Visitable {

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
        if (subject == null || subject.isEmpty()) {
            throw new IllegalArgumentException("null or empty 'subject' parameter was passed to the" +
                    " Record class constructor.");
        }

        if (semester < 1) {
            throw new IllegalArgumentException("Invalid 'semester' parameter was passed to the" +
                    " Record class constructor.");
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

    public abstract void accept(Visitor visitor);
}
