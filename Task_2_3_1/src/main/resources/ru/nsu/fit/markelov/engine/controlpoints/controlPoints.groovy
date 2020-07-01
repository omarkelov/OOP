package ru.nsu.fit.markelov.engine.controlpoints

import ru.nsu.fit.markelov.objects.ControlPoints

import static groovy.lang.Closure.DELEGATE_ONLY

controlPointsDSL = new ControlPoints()
exceptionDSL = null

void controlPoint(@DelegatesTo(strategy = DELEGATE_ONLY, value = ControlPointDSL) Closure closure) {
    ControlPointDSL controlPoint = new ControlPointDSL()

    closure.delegate = controlPoint
    closure.resolveStrategy = DELEGATE_ONLY
    closure.call()

    controlPointsDSL.controlPoints.add(controlPoint)
}
