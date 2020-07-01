package ru.nsu.fit.markelov.engine.tasks

import ru.nsu.fit.markelov.objects.Tasks
import ru.nsu.fit.markelov.util.validation.IllegalInputException

import static groovy.lang.Closure.DELEGATE_ONLY

/**
 * TasksDSL class is used for setting the tasks data from the user script.
 *
 * @author Oleg Markelov
 */
class TasksDSL extends Tasks {
    /**
     * Adds task to the collection of tasks.
     */
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
