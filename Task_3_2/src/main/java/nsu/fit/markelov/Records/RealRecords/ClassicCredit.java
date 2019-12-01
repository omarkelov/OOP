package nsu.fit.markelov.Records.RealRecords;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Records.ClassicRecord;

/**
 * The <code>ClassicCredit</code> class represents a single classic credit record in
 * <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class ClassicCredit extends ClassicRecord {
    /**
     * Creates a new <code>GradedCredit</code>.
     *
     * @param subject  the subject name.
     * @param semester the semester number.
     * @param passed   whether the credit is passed.
     */
    public ClassicCredit(String subject, int semester, boolean passed) {
        super(subject, semester, passed);
    }
}
