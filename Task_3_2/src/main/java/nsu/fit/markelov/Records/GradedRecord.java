package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Visitors.Visitor;

/**
 * The <code>GradedRecord</code> class represents a single differential record in
 * <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class GradedRecord extends Record {

    private int grade;

    /**
     * Creates a new <code>GradedRecord</code>.
     *
     * @param  subject                  the subject name.
     * @param  semester                 the semester number.
     * @param  grade                    the grade from 2 to 5.
     * @throws IllegalArgumentException if the grade is not between 2 and 5.
     */
    public GradedRecord(String subject, int semester, int grade) {
        super(subject, semester);
        this.grade = grade;
        checkInput();
    }

    private void checkInput() {
        if (grade < 2 || grade > 5) {
            throw new IllegalArgumentException("Invalid 'grade' parameter was passed to the" +
                    " GradedRecord class constructor.");
        }
    }

    /**
     * Returns the grade of the record.
     *
     * @return the grade of the record.
     */
    public int getGrade() {
        return grade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
