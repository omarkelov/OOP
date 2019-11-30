package nsu.fit.markelov;

import nsu.fit.markelov.Records.Record;
import nsu.fit.markelov.Visitors.GradeVisitor;
import nsu.fit.markelov.Visitors.IncreasedScholarshipVisitor;
import nsu.fit.markelov.Visitors.Visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;

/**
 * The <code>RecordBook</code> class is a collection of student's
 * records during the whole period of study.
 *
 * @author Oleg Markelov
 * @see    Record
 */
public class RecordBook {

    private int semestersAmount;
    private int lastSemester;
    private ArrayList<Record> records;

    private Visitor<Double> gradeVisitor;
    private Visitor<Boolean> increasedScholarshipVisitor;

    /**
     * Creates a new <code>RecordBook</code>.
     *
     * @param semestersAmount         the number of semesters during
     *                                the whole period of study.
     * @param lastSemester            the number of semester the student
     *                                finished.
     * @throws InputMismatchException if 'semestersAmount' or 'lastSemester'
     *                                parameter less than one was passed. Or
     *                                if 'lastSemester' > 'semestersAmount'.
     */
    public RecordBook(int semestersAmount, int lastSemester) throws InputMismatchException {
        this.semestersAmount = semestersAmount;
        this.lastSemester = lastSemester;
        checkInput();

        records = new ArrayList<>();

        gradeVisitor = new GradeVisitor();
        increasedScholarshipVisitor = new IncreasedScholarshipVisitor();
    }

    /**
     * Adds any record.
     *
     * @param record the record to be added.
     * @see   Record
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

    /**
     * Calculates and returns the average grade of the diploma.
     *
     * @return the average grade of the diploma.
     */
    public double getDiplomaAverage() {
        // (subject -> RecordDif) map for getting only the last record of each subject
        HashMap<String, Record> map = new HashMap<>();
        records.forEach(record -> {
            if (!map.containsKey(record.getSubject()) ||
                    record.getSemester() > map.get(record.getSubject()).getSemester()) {
                map.put(record.getSubject(), record);
            }
        });

        return getAverage(map.values());
    }

    /**
     * Calculates and returns whether the student should get
     * an increased scholarship.
     *
     * @return whether the student should get an increased scholarship.
     */
    public boolean isIncreasedScholarship() {
        return records.stream()
                .filter(record -> record.getSemester() == lastSemester)
//                .allMatch(record -> record.acceptBoolean(increasedScholarshipVisitor) &&
                .allMatch(record -> record.isIncreasedScholarship() &&
                        record.getSemester() != semestersAmount);
    }

    private double getAverage(Collection<Record> collection) {
        return collection.stream()
//                .mapToDouble(record -> record.acceptDouble(gradeVisitor))
                .mapToDouble(Record::getGradeDouble)
                .filter(grade -> !Double.isNaN(grade))
                .average()
                .orElse(Double.NaN);
    }

    private void checkInput() throws InputMismatchException {
        if (semestersAmount < 1) {
            throw new InputMismatchException("Invalid 'semestersAmount' parameter was passed to" +
                    " the RecordBook class constructor.");
        }

        if (lastSemester < 1) {
            throw new InputMismatchException("Invalid 'lastSemester' parameter was passed to the" +
                    " RecordBook class constructor.");
        }

        if (lastSemester > semestersAmount) {
            throw new InputMismatchException("lastSemester must be <= semestersAmount.");
        }
    }
}
