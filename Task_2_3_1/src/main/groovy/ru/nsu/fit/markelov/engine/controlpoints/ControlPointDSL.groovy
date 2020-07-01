package ru.nsu.fit.markelov.engine.controlpoints

import ru.nsu.fit.markelov.objects.ControlPoint

import static ru.nsu.fit.markelov.Main.DATE_FORMAT

class ControlPointDSL extends ControlPoint {
    void name(String name) {
        super.name = name
    }

    void date(String date) {
        super.date = DATE_FORMAT.parse(date)
    }

    void five(int points) {
        super.pointsForFive = points
    }

    void four(int points) {
        super.pointsForFour = points
    }

    void three(int points) {
        super.pointsForThree = points
    }
}
