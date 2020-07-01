package ru.nsu.fit.markelov.engine.tasks

import ru.nsu.fit.markelov.objects.Task

import static ru.nsu.fit.markelov.Main.DATE_FORMAT

/**
 * TaskDSL class is used for setting the task data from the user script.
 *
 * @author Oleg Markelov
 */
class TaskDSL extends Task {
    /**
     * Sets id.
     */
    void id(String id) {
        super.id = id
    }

    /**
     * Sets name.
     */
    void name(String name) {
        super.name = name
    }

    /**
     * Sets points.
     */
    void points(float points) {
        super.points = points
    }

    /**
     * Sets deadline.
     */
    void deadline(String deadline) {
        super.deadline = DATE_FORMAT.parse(deadline)
    }
}
