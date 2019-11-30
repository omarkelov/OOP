package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.ClassicRecord;
import nsu.fit.markelov.Records.GradedRecord;

public class IncreasedScholarshipVisitor implements Visitor {

    private boolean increasedScholarship = true;

    @Override
    public void visit(GradedRecord gradedRecord) {
        if (gradedRecord.getGrade() != 5) {
            increasedScholarship = false;
        }
    }

    @Override
    public void visit(ClassicRecord classicRecord) {
        if (!classicRecord.isPassed()) {
            increasedScholarship = false;
        }
    }

    public boolean isIncreasedScholarship() {
        return increasedScholarship;
    }
}
