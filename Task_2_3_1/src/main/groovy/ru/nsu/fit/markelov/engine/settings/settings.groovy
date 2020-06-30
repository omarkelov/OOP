package ru.nsu.fit.markelov.engine.settings

import static groovy.lang.Closure.DELEGATE_ONLY

settingsDSL = new SettingsDSL()

void workingDirectory(String workingDirectory) {
    settingsDSL.workingDirectory = workingDirectory
}

void git(@DelegatesTo(strategy = DELEGATE_ONLY, value = SettingsDSL) Closure closure) {
    closure.delegate = settingsDSL
    closure.resolveStrategy = DELEGATE_ONLY
    closure.call()
}
