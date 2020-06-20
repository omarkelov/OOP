package ru.nsu.fit.markelov.engine.group

import ru.nsu.fit.markelov.objects.GroupObject

import static groovy.lang.Closure.DELEGATE_ONLY

class GroupDSL extends GroupObject {
    void student(@DelegatesTo(strategy = DELEGATE_ONLY, value = StudentDSL) Closure closure) {
        StudentDSL studentDSL = new StudentDSL()

        closure.delegate = studentDSL
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()

        super.students[studentDSL.nickname] = studentDSL
    }
}
