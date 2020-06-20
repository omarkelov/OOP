package ru.nsu.fit.markelov.engine.controlpoints

import ru.nsu.fit.markelov.objects.ControlPointObject

import static ru.nsu.fit.markelov.Main.DATE_FORMAT

class ControlPointDSL extends ControlPointObject {
    void name(String name) {
        super.name = name
    }

    void date(String date) {
        super.date = DATE_FORMAT.parse(date)
    }
}
