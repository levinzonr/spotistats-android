package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.DarkMode

class SettingsRepositoryImpl(private val prefManager: PrefManager) : SettingsRepository {


    override var darkModeState: DarkMode
        get() = DarkMode.values()[prefManager.getInt(PREF_DARK_MODE, 0)]
        set(value) = prefManager.setInt(PREF_DARK_MODE, value.ordinal)



    companion object {
        const val PREF_DARK_MODE = "darkmode"
    }
}