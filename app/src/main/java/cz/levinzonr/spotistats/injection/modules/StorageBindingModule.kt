package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.domain.managers.PrefManager
import cz.levinzonr.spotistats.storage.PrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



val storageModule = module {
    single<PrefManager> { PrefManagerImpl(androidContext()) }
}