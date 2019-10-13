package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spotistats.domain.managers.SpotifyRemoteManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val managerModule = module {
    single<SpotifyRemoteManager> { SpotifyRemoteManagerImpl(androidContext(), get()) }
}