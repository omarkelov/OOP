package nsu.fit.markelov;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RecordBook {

    private int semestersAmount;
    private int semestersFinished;
    private ArrayList<Record> records;

    public RecordBook(int semestersAmount, int semestersFinished) throws Exception {
        this.semestersAmount = semestersAmount;
        this.semestersFinished = semestersFinished;
        checkInput();

        records = new ArrayList<>();
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public double getAverage() {
        return getAverage(records);
    }

    private double getAverage(Collection<Record> collection) {
        return collection.stream()
                .filter(DifferentialRecord.class::isInstance)
                .map(DifferentialRecord.class::cast)
                .mapToDouble(DifferentialRecord::getMark)
                .average()
                .orElse(Double.NaN);
    }

    public double getDiplomaAverage() {
        HashMap<String, Record> map = new HashMap<>(); // subject to Record
        records.forEach(record -> {
            if (!map.containsKey(record.getSubject()) || record.getSemester() > map.get(record.getSubject()).getSemester()) {
                map.put(record.getSubject(), record);
            }
        });

        return getAverage(map.values());
    }

    public boolean isIncreasedScholarships() {
        return records.stream()
                .filter(record -> record.getSemester() == semestersFinished)
                .allMatch(record -> record instanceof DifferentialRecord ?
                        ((DifferentialRecord)record).getMark() == 5 :
                        ((ClassicCredit)record).isPassed()
                );
    }

/*
        public boolean isIncreasedScholarships() {
            return records.stream()
                    .filter(record -> record.getSemester() == semestersFinished)
                    .allMatch(record -> {
                        if (record instanceof DifferentialRecord) {
                            return ((DifferentialRecord)record).getMark() == 5;
                        } else if (record instanceof ClassicCredit) {
                            return ((ClassicCredit)record).isPassed();
                        } else {
                            throw new ClassCastException(); // likely wrong - then which Exception to use?
                        }
                    });
        }
    */
/*
    public boolean isIncreasedScholarships() {
        boolean areCreditsPassed = records.stream()
                .filter(record -> record.getSemester() == semestersFinished)
                .filter(DifferentialRecord.class::isInstance)
                .map(DifferentialRecord.class::cast)
                .allMatch(record -> record.getMark() == 5);

        boolean areExellentMarks = records.stream()
                .filter(record -> record.getSemester() == semestersFinished)
                .filter(DifferentialRecord.class::isInstance)
                .map(DifferentialRecord.class::cast)
                .allMatch(record -> record.getMark() == 5);

        return areCreditsPassed && areExellentMarks;
    }
*/
    private void checkInput() throws Exception {
        if (semestersAmount < 1 || semestersFinished < 1 || semestersFinished > semestersAmount) {
            throw new Exception("Invalid parameter was passed to the RecordBook class constructor.");
        }
    }

    // ----- debug -----

    public void print() {
        for (Record record : records) {
            System.out.println(record.getSubject() + ": " + record.getEvaluation());
        }
    }
}
