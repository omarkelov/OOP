package ru.nsu.fit.markelov.engine.settings

import ru.nsu.fit.markelov.objects.Settings

/**
 * SettingsDSL class is used for setting the settings data from the user script.
 *
 * @author Oleg Markelov
 */
class SettingsDSL extends Settings {
    /**
     * Sets git login.
     */
    void login(String login) {
        super.gitLogin = login
    }

    /**
     * Sets git password.
     */
    void password(String password) {
        super.gitPassword = password
    }
}
