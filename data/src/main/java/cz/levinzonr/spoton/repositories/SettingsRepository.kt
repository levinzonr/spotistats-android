package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.DarkMode

interface SettingsRepository {

    var darkModeState: DarkMode
}