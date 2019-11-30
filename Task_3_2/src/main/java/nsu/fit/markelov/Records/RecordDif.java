package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Visitors.Visitor;

import java.util.InputMismatchException;

/**
 * The <code>RecordDif</code> class represents a single differential
 * record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class RecordDif extends Record {

    private int grade;

    /**
     * Creates a new <code>RecordDif</code>.
     *
     * @param subject                 the subject name.
     * @param semester                the semester number.
     * @param grade                   the grade from 2 to 5.
     * @throws InputMismatchException if the grade is not between 2 and 5.
     */
    public RecordDif(String subject, int semester, int grade) throws InputMismatchException {
        super(subject, semester);
        this.grade = grade;
        checkInput();
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
     * Returns the grade of the record.
     *
     * @return the grade of the record.
     */
    @Override
    public String getEvaluation() {
        return grade + "";
    }

    private void checkInput() throws InputMismatchException {
        if (grade < 2 || grade > 5) {
            throw new InputMismatchException("Invalid 'grade' parameter was passed to the" +
                    " RecordDif class constructor.");
        }
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public Double acceptDouble(Visitor<Double> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Boolean acceptBoolean(Visitor<Boolean> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Double getGradeDouble() {
        return new Double(grade);
    }

    @Override
    public Boolean isIncreasedScholarship() {
        return grade == 5;
    }
}
