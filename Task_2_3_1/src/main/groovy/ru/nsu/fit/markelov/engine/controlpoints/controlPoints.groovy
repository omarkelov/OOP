package ru.nsu.fit.markelov.engine.controlpoints

import static groovy.lang.Closure.DELEGATE_ONLY

controlPointsDSL = new TreeSet<ControlPointDSL>()

void controlPoint(@DelegatesTo(strategy = DELEGATE_ONLY, value = ControlPointDSL) Closure closure) {
    ControlPointDSL controlPoint = new ControlPointDSL()

    closure.delegate = controlPoint
    closure.resolveStrategy = DELEGATE_ONLY
    closure.call()

    controlPointsDSL.add(controlPoint)
}
