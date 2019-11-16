package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;

import java.util.InputMismatchException;

/**
 * The <code>CreditDif</code> class represents a single
 * differential credit record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class CreditDif extends RecordDif {
    /**
     * Creates a new <code>CreditDif</code>.
     *
     * @param subject                 the subject name.
     * @param semester                the semester number.
     * @param grade                   the grade from 2 to 5.
     * @throws InputMismatchException if the grade is not between 2 and 5.
     */
    public CreditDif(String subject, int semester, int grade) {
        super(subject, semester, grade);
    }
}
