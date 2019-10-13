package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.repositories.PrefManager
import cz.levinzonr.spotistats.domain.managers.UserManager
import cz.levinzonr.spotistats.domain.managers.UserManagerImpl
import cz.levinzonr.spotistats.repositories.AuthTokenRepository
import cz.levinzonr.spotistats.repositories.SettingsRepository
import cz.levinzonr.spotistats.repositories.SettingsRepositoryImpl
import cz.levinzonr.spotistats.storage.AuthTokenRepositoryImpl
import cz.levinzonr.spotistats.storage.PrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



val storageModule = module {
    single<PrefManager> { PrefManagerImpl(androidContext()) }
    single<UserManager> { UserManagerImpl(get()) }
    single<AuthTokenRepository> { AuthTokenRepositoryImpl(get(), get())}
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}