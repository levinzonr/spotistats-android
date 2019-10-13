package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.DarkMode

interface SettingsRepository {

    var darkModeState: DarkMode
}