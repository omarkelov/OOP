package nsu.fit.markelov;

import nsu.fit.markelov.Records.Record;
import nsu.fit.markelov.Visitors.AverageGradeVisitor;
import nsu.fit.markelov.Visitors.IncreasedScholarshipVisitor;
import nsu.fit.markelov.Visitors.PositiveGradesVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The <code>RecordBook</code> class is a collection of student's records during the whole period
 * of study.
 *
 * @author Oleg Markelov
 * @see    Record
 */
public class RecordBook {

    public static final String INVALID_N_SEMESTERS_EXCEPTION_MESSAGE =
            "Invalid 'nSemesters' parameter was passed to the RecordBook class constructor.";
    public static final String INVALID_LAST_SEMESTER_EXCEPTION_MESSAGE =
            "Invalid 'lastSemester' parameter was passed to the RecordBook class constructor.";
    public static final String INVALID_PARAMS_EXCEPTION_MESSAGE =
            "lastSemester must be <= nSemesters.";
    public static final String INVALID_DIPLOMA_GRADE_EXCEPTION_MESSAGE =
            "Invalid 'diplomaGrade' parameter was passed to the setDiplomaGrade method.";

    private static final double MINIMAL_HONOURS_DIPLOMA_GRADE = 4.75d;

    private int nSemesters;
    private int lastSemester;
    private ArrayList<Record> records;
    private Integer diplomaGrade;

    /**
     * Creates a new <code>RecordBook</code>.
     *
     * @param  nSemesters               the number of semesters during the whole period of study.
     * @param  lastSemester             the number of semester the student finished.
     * @throws IllegalArgumentException if 'nSemesters' or 'lastSemester' parameter less than one
     *                                  was passed. Or if 'lastSemester' > 'nSemesters'.
     */
    public RecordBook(int nSemesters, int lastSemester) {
        this.nSemesters = nSemesters;
        this.lastSemester = lastSemester;
        checkInput();

        records = new ArrayList<>();
    }

    private void checkInput() {
        String message = null;

        if (nSemesters < 1) {
            message = INVALID_N_SEMESTERS_EXCEPTION_MESSAGE;
        } else if (lastSemester < 1) {
            message = INVALID_LAST_SEMESTER_EXCEPTION_MESSAGE;
        } else if (lastSemester > nSemesters) {
            message = INVALID_PARAMS_EXCEPTION_MESSAGE;
        }

        if (message != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Adds a record of any kind.
     *
     * @param record the record to be added.
     */
    public void addRecord(Record record) {
        records.add(record);
    }

    /**
     * Calculates and returns the average grade of the <code>RecordBook</code>.
     *
     * @return the average grade of the <code>RecordBook</code>.
     */
    public double getRecordBookAverage() {
        return getAverage(records);
    }

    private double getAverage(Collection<Record> records) {
        AverageGradeVisitor visitor = new AverageGradeVisitor();

        records.forEach(record -> record.accept(visitor));

        return visitor.getAverage();
    }

    /**
     * Returns whether the student should get the honours degree.
     *
     * @return whether the student should get the honours degree.
     */
    public boolean isHonoursDegree() {
        return diplomaGrade != null
                && diplomaGrade == 5
                && getDiplomaAverage() >= MINIMAL_HONOURS_DIPLOMA_GRADE
                && areAllGradesPositive();
    }

    /**
     * Calculates and returns the average grade of the diploma.
     *
     * @return the average grade of the diploma.
     */
    public double getDiplomaAverage() {
        // (subject -> GradedRecord) map for getting only the last record of each subject
        Map<String, Record> map = new HashMap<>();
        records.forEach(record -> {
            if (!map.containsKey(record.getSubject()) ||
                    record.getSemester() > map.get(record.getSubject()).getSemester()) {
                map.put(record.getSubject(), record);
            }
        });

        return getAverage(map.values());
    }

    private boolean areAllGradesPositive() {
        PositiveGradesVisitor visitor = new PositiveGradesVisitor();

        records.forEach(record -> record.accept(visitor));

        return visitor.areAllGradesPositive();
    }

    /**
     * Calculates and returns whether the student should get an increased scholarship.
     *
     * @return whether the student should get an increased scholarship.
     */
    public boolean isIncreasedScholarship() {
        if (lastSemester == nSemesters) {
            return false;
        }

        IncreasedScholarshipVisitor visitor = new IncreasedScholarshipVisitor();

        records.stream()
                .filter(record -> record.getSemester() == lastSemester)
                .forEach(record -> record.accept(visitor));

        return visitor.isIncreasedScholarship();
    }

    /**
     * Sets the diploma grade. After that being done, the last semester becomes the latest possible.
     *
     * @param diplomaGrade the diploma grade.
     */
    public void setDiplomaGrade(int diplomaGrade) {
        if (diplomaGrade < 2 || diplomaGrade > 5) {
            throw new IllegalArgumentException(INVALID_DIPLOMA_GRADE_EXCEPTION_MESSAGE);
        }

        this.diplomaGrade = diplomaGrade;
        lastSemester = nSemesters;
    }
}
