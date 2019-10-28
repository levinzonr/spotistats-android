package cz.levinzonr.spoton.inititializers

import android.app.Application
import cz.levinzonr.spoton.BuildConfig
import timber.log.Timber
import javax.inject.Inject

interface AppInitializer {
    fun init(app: Application)
}

class AppInitializerImpl @Inject constructor() : AppInitializer {
    override fun init(app: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}