package cz.levinzonr.spoton.injection.modules

import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val managerModule = module {
    single<SpotifyRemoteManager> { SpotifyRemoteManagerImpl(androidContext(), get()) }
}