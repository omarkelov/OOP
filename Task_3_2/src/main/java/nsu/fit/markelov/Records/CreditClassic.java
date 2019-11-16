package nsu.fit.markelov.Records;

import nsu.fit.markelov.RecordBook;

/**
 * The <code>CreditClassic</code> class represents a single
 * classic credit record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class CreditClassic extends RecordNonDif {
    /**
     * Creates a new <code>CreditDif</code>.
     *
     * @param subject                 the subject name.
     * @param semester                the semester number.
     * @param passed   whether the credit is passed.
     */
    public CreditClassic(String subject, int semester, boolean passed) {
        super(subject, semester, passed);
    }
}
