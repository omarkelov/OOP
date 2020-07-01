package ru.nsu.fit.markelov.objects;

import ru.nsu.fit.markelov.util.validation.IllegalInputException;
import ru.nsu.fit.markelov.util.validation.Validatable;

import java.util.Set;
import java.util.TreeSet;

public class ControlPoints implements Validatable<ControlPoints> {
    private Set<ControlPoint> controlPoints = new TreeSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public ControlPoints validate() throws IllegalInputException {
        for (ControlPoint controlPoint : controlPoints) {
            controlPoint.validate();
        }

        return this;
    }

    /**
     * Returns controlPoints.
     *
     * @return controlPoints.
     */
    public Set<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    /**
     * Sets controlPoints.
     */
    public void setControlPoints(Set<ControlPoint> controlPoints) {
        this.controlPoints = controlPoints;
    }
}
