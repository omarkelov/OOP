package nsu.fit.markelov;

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

    private int mSemestersAmount;
    private int mLastSemester;
    private ArrayList<NonDifRecord> mNonDifRecords;
    private ArrayList<DifRecord> mDifRecords;

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
        mSemestersAmount = semestersAmount;
        mLastSemester = lastSemester;
        checkInput();

        mNonDifRecords = new ArrayList<>();
        mDifRecords = new ArrayList<>();
    }

    /**
     * Adds non-differential record.
     *
     * @param nonDifRecord the record to be added.
     * @see   NonDifRecord
     */
    public void addRecord(NonDifRecord nonDifRecord) {
        mNonDifRecords.add(nonDifRecord);
    }

    /**
     * Adds differential record.
     *
     * @param difRecord the record to be added.
     * @see   DifRecord
     */
    public void addRecord(DifRecord difRecord) {
        mDifRecords.add(difRecord);
    }

    /**
     * Calculates and returns the average point of the <code>RecordBook</code>.
     *
     * @return the average point of the <code>RecordBook</code>.
     */
    public double getRecordBookAverage() {
        return getAverage(mDifRecords);
    }

    /**
     * Calculates and returns the average point of the diploma.
     *
     * @return the average point of the diploma.
     */
    public double getDiplomaAverage() {
        // (subject -> DifRecord) map for getting only the last record of each subject
        HashMap<String, DifRecord> map = new HashMap<>();
        mDifRecords.forEach(record -> {
            if (!map.containsKey(record.getSubject()) || record.getSemester() > map.get(record.getSubject()).getSemester()) {
                map.put(record.getSubject(), record);
            }
        });

        return getAverage(map.values());
    }

    /**
     * Calculates and returnes whether the student should get
     * an increased scholarship.
     *
     * @return whether the student should get an increased scholarship.
     */
    public boolean isIncreasedScholarship() {
        boolean creditsPassed = mNonDifRecords.stream()
                .filter(record -> record.getSemester() == mLastSemester)
                .allMatch(NonDifRecord::isPassed);

        boolean exellentMarks = mDifRecords.stream()
                .filter(record -> record.getSemester() == mLastSemester)
                .allMatch(record -> record.getPoint() == 5);

        return creditsPassed && exellentMarks;
    }

    private double getAverage(Collection<DifRecord> collection) {
        return collection.stream()
                .mapToDouble(DifRecord::getPoint)
                .average()
                .orElse(Double.NaN);
    }

    private void checkInput() throws InputMismatchException {
        if (mSemestersAmount < 1) {
            throw new InputMismatchException("Invalid 'semestersAmount' parameter was passed to the RecordBook class constructor.");
        }

        if (mLastSemester < 1) {
            throw new InputMismatchException("Invalid 'lastSemester' parameter was passed to the RecordBook class constructor.");
        }

        if (mLastSemester > mSemestersAmount) {
            throw new InputMismatchException("lastSemester must be <= semestersAmount.");
        }
    }
}
