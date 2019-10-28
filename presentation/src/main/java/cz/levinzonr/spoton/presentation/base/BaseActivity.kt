package cz.levinzonr.spoton.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import cz.levinzonr.spoton.presentation.extensions.setDarkMode
import cz.levinzonr.spoton.repositories.SettingsRepository
import org.koin.android.ext.android.inject
import timber.log.Timber

abstract class BaseActivity : AppCompatActivity() {

    private val settings: SettingsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Timber.d("Set Dartkm mdoe ${settings.darkModeState}")
    }
}