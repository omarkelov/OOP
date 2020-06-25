package ru.nsu.fit.markelov.engine.tasks

import ru.nsu.fit.markelov.objects.Tasks

import static groovy.lang.Closure.DELEGATE_ONLY

class TasksDSL extends Tasks {
    void task(@DelegatesTo(strategy = DELEGATE_ONLY, value = TaskDSL) Closure closure) {
        TaskDSL taskDSL = new TaskDSL()

        closure.delegate = taskDSL
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()

        super.tasks[taskDSL.id] = taskDSL
    }
}
