package cz.levinzonr.spotistats

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import cz.levinzonr.spotistats.inititializers.AppInitializer
import cz.levinzonr.spotistats.injection.modules.appModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private val initializer: AppInitializer by inject()
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appModule)

        }
        initializer.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}