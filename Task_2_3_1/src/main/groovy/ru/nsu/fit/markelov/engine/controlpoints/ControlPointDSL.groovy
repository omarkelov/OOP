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

    void exc(int points) {
        super.pointsForExc = points
    }

    void good(int points) {
        super.pointsForGood = points
    }

    void sat(int points) {
        super.pointsForSat = points
    }
}
