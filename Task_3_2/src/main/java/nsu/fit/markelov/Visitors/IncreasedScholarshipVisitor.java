package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.RecordDif;
import nsu.fit.markelov.Records.RecordNonDif;

public class IncreasedScholarshipVisitor implements Visitor<Boolean> {

    @Override
    public Boolean visit(RecordDif recordDif) {
        return recordDif.getGrade() == 5;
    }

    @Override
    public Boolean visit(RecordNonDif recordNonDif) {
        return recordNonDif.isPassed();
    }
}
