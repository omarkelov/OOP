package nsu.fit.markelov.Visitors;

import nsu.fit.markelov.Records.ClassicRecord;
import nsu.fit.markelov.Records.GradedRecord;
import nsu.fit.markelov.Records.Record;

/**
 * A visitor of records, in the style of the visitor design pattern. Classes implementing this
 * interface are used to operate on an <code>Record</code> when the kind of record is unknown at
 * compile time. When a visitor is passed to an element's accept method, the visitXYZ method most
 * applicable to that type is invoked.
 *
 * @author Oleg Markelov
 * @see    Record
 */

public interface Visitor {

    /**
     * Visits a record.
     *
     * @param gradedRecord the record to visit.
     */
    void visit(GradedRecord gradedRecord);

    /**
     * Visits a record.
     *
     * @param classicRecord the record to visit.
     */
    void visit(ClassicRecord classicRecord);
}
