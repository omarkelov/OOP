package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.GradedRecord;
import nsu.fit.markelov.Records.ClassicRecord;

public class AverageGradeVisitor implements Visitor {

    private int gradeSum = 0;
    private int nGrades = 0;

    @Override
    public void visit(GradedRecord gradedRecord) {
        gradeSum += gradedRecord.getGrade();
        nGrades++;
    }

    @Override
    public void visit(ClassicRecord classicRecord) {}

    public double getAverage() {
        return (double) gradeSum / nGrades;
    }
}
