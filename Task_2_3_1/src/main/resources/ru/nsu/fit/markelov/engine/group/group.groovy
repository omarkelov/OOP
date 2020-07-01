package ru.nsu.fit.markelov.engine.group

import static groovy.lang.Closure.DELEGATE_ONLY

groupDSL = new GroupDSL()
exceptionDSL = null

void group(String name, @DelegatesTo(strategy = DELEGATE_ONLY, value = GroupDSL) Closure closure) {
    groupDSL.name = name
    closure.delegate = groupDSL
    closure.resolveStrategy = DELEGATE_ONLY
    closure.call()
}
