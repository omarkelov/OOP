package ru.nsu.fit.markelov.engine.tasks

import static groovy.lang.Closure.DELEGATE_ONLY

tasksDSL = new TasksDSL()
exceptionDSL = null

void tasks(int semester, @DelegatesTo(strategy = DELEGATE_ONLY, value = TasksDSL) Closure closure) {
    tasksDSL.semester = semester
    closure.delegate = tasksDSL
    closure.resolveStrategy = DELEGATE_ONLY
    closure.call()
}
