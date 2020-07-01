package ru.nsu.fit.markelov.engine.tasks

import ru.nsu.fit.markelov.objects.Tasks
import ru.nsu.fit.markelov.util.validation.IllegalInputException

import static groovy.lang.Closure.DELEGATE_ONLY

class TasksDSL extends Tasks {
    void task(@DelegatesTo(strategy = DELEGATE_ONLY, value = TaskDSL) Closure closure) {
        TaskDSL taskDSL = new TaskDSL()

        closure.delegate = taskDSL
        closure.resolveStrategy = DELEGATE_ONLY
        closure.call()

        taskDSL.validate()

        if (super.tasks[taskDSL.id]) {
            throw new IllegalInputException("task id '$taskDSL.id' is not unique")
        }

        super.tasks[taskDSL.id] = taskDSL
    }
}
