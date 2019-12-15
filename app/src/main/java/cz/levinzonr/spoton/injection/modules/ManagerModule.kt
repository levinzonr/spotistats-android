package cz.levinzonr.spoton.injection.modules

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import cz.levinzonr.spoton.AppConfigImpl
import cz.levinzonr.spoton.BuildConfig
import cz.levinzonr.spoton.domain.managers.AppConfig
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val managerModule = module {
    single<SpotifyRemoteManager> { SpotifyRemoteManagerImpl(androidContext(), get()) }

    single<FirebaseRemoteConfig> {
        val interval = if (BuildConfig.DEBUG) 30 else 3600
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(interval.toLong())
                .build()

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(mapOf("latest_app_version" to BuildConfig.VERSION_CODE))
        remoteConfig
    }

    single<AppConfig> { AppConfigImpl() }
}