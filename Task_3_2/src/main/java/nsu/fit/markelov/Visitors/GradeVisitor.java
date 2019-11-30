package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.RecordDif;
import nsu.fit.markelov.Records.RecordNonDif;

public class GradeVisitor implements Visitor<Double> {

    @Override
    public Double visit(RecordDif recordDif) {
        return new Double(recordDif.getGrade());
    }

    @Override
    public Double visit(RecordNonDif recordNonDif) {
        return Double.NaN;
    }
}
