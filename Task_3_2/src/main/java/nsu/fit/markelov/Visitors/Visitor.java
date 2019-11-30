package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.ClassicRecord;
import nsu.fit.markelov.Records.GradedRecord;

public interface Visitor {
    void visit(GradedRecord gradedRecord);
    void visit(ClassicRecord classicRecord);
}
