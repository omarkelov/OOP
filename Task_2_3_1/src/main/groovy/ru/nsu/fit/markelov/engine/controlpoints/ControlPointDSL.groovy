package ru.nsu.fit.markelov.engine.controlpoints

import ru.nsu.fit.markelov.objects.ControlPoint

import static ru.nsu.fit.markelov.Main.DATE_FORMAT

/**
 * ControlPointDSL class is used for setting the control point data from the user script.
 *
 * @author Oleg Markelov
 */
class ControlPointDSL extends ControlPoint {
    /**
     * Sets name.
     */
    void name(String name) {
        super.name = name
    }

    /**
     * Sets date.
     */
    void date(String date) {
        super.date = DATE_FORMAT.parse(date)
    }

    /**
     * Sets amount of points for an excellent grade.
     */
    void exc(int points) {
        super.pointsForExc = points
    }

    /**
     * Sets amount of points for a good grade.
     */
    void good(int points) {
        super.pointsForGood = points
    }

    /**
     * Sets amount of points for a sat grade.
     */
    void sat(int points) {
        super.pointsForSat = points
    }
}
