package cz.levinzonr.spoton

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import cz.levinzonr.roxie.Roxie
import cz.levinzonr.spoton.inititializers.AppInitializer
import cz.levinzonr.spoton.injection.modules.appModule
import cz.levinzonr.spoton.presentation.extensions.setDarkMode
import cz.levinzonr.spoton.repositories.SettingsRepository
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    private val initializer: AppInitializer by inject()
    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appModule)

        }
        Roxie.enableLogging(object : Roxie.Logger {
            override fun log(msg: String) {
                Timber.d(msg)
            }
        })
        initializer.init(this)

        setDarkMode(settingsRepository.darkModeState)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}