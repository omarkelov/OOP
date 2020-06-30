package ru.nsu.fit.markelov.engine.settings

import ru.nsu.fit.markelov.objects.Settings

class SettingsDSL extends Settings {
    void login(String login) {
        super.gitLogin = login
    }

    void password(String password) {
        super.gitPassword = password
    }
}
