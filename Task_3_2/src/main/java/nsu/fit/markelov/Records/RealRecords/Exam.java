package nsu.fit.markelov.Records.RealRecords;

import nsu.fit.markelov.RecordBook;
import nsu.fit.markelov.Records.GradedRecord;

/**
 * The <code>Exam</code> class represents a single
 * Exam record in <code>RecordBook</code>.
 *
 * @author Oleg Markelov
 * @see    RecordBook
 */
public class Exam extends GradedRecord {
    /**
     * Creates a new <code>Exam</code>.
     *
     * @param  subject                  the subject name.
     * @param  semester                 the semester number.
     * @param  grade                    the grade from 2 to 5.
     * @throws IllegalArgumentException if the grade is not between 2 and 5.
     */
    public Exam(String subject, int semester, int grade) {
        super(subject, semester, grade);
    }
}
