package ru.nsu.fit.markelov.engine.group

import ru.nsu.fit.markelov.objects.Group
import ru.nsu.fit.markelov.util.validation.IllegalInputException

import static groovy.lang.Closure.DELEGATE_ONLY

/**
 * GroupDSL class is used for setting the group data from the user script.
 *
 * @author Oleg Markelov
 */
class GroupDSL extends Group {
    /**
     * Adds student to this group.
     */
    void student(@DelegatesTo(strategy = DELEGATE_ONLY, value = StudentDSL) Closure closure) {
        StudentDSL studentDSL = new StudentDSL()

        closure.delegate = studentDSL
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()

        studentDSL.validate()

        if (super.students[studentDSL.id]) {
            throw new IllegalInputException("student id '$studentDSL.id' is not unique")
        }

        super.students[studentDSL.id] = studentDSL
    }
}
