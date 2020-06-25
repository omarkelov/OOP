package ru.nsu.fit.markelov.engine.tasks

import ru.nsu.fit.markelov.objects.Task

import static ru.nsu.fit.markelov.Main.DATE_FORMAT

class TaskDSL extends Task {

    void id(String id) {
        super.id = id
    }

    void name(String name) {
        super.name = name
    }

    void points(float points) {
        super.points = points
    }

    void deadline(String deadline) {
        super.deadline = DATE_FORMAT.parse(deadline)
    }
}
