package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.RecordDif;
import nsu.fit.markelov.Records.RecordNonDif;

public interface Visitor<T> {
    T visit(RecordDif recordDif);
    T visit(RecordNonDif recordNonDif);
}
